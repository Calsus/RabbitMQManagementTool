package be.telenet.yellowbelt.rmt.services;

import be.telenet.yellowbelt.rmt.models.RabbitMQHeader;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This service is responsible for handling the sending of a message on to a RabbitMQ queue.
 * <p>
 * Created by Jerry-Lee on 24/01/2017.
 */
@Service
public class RabbitMQManagementToolService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQManagementToolService.class);

	@Autowired
	private CamelContext camelContext;

	@Value("${rabbitmq.hostname}")
	private String hostname;

	@Value("${rabbitmq.port}")
	private String port;

	private String rabbitMQEndpoint = "rabbitmq://";

	/**
	 * <p>Send a message to a RabbitMQ queue.</p>
	 * Steps:
	 * <ul>
	 * <li>Extract all valid headers from the given headers param</li>
	 * <li>Create a producer template</li>
	 * <li>Create the RabbitMQ endpoint</li>
	 * <li>Send the content and the valid headers to the RabbitMQ endpoint</li>
	 * </ul>
	 *
	 * @param headers List of {@link RabbitMQHeader} containing all the headers that have to be added.
	 * @param content Content that have to be send with the message
	 * @param queue   Name of the queue
	 */
	public void sendMessage(List<RabbitMQHeader> headers, String content, String queue) {
		logDebugHeaders(headers);

		Map<String, Object> headerMap = getMapWithValidHeaders(headers);
		ProducerTemplate template = camelContext.createProducerTemplate();

		String endpoint = rabbitMQEndpoint + hostname + ":" + port + "/" + extractExchangeNameFromQueue(queue) + "?exchangeType=fanout&bridgeEndpoint=true&queue=" + queue;

		LOGGER.debug("Endpoint: " + endpoint);

		template.sendBodyAndHeaders(endpoint, content, headerMap);

	}

	String extractExchangeNameFromQueue(String queue) {
		return queue.split("-queue")[0];
	}

	/**
	 * This method will filter out the invalid headers and then convert it a Map of String, Object.
	 *
	 * @param headers List of {@link RabbitMQHeader}
	 * @return returns a Map of String, Object because the method {@link ProducerTemplate#sendBodyAndHeaders(String, Object, Map)} requires it.
	 **/
	Map<String, Object> getMapWithValidHeaders(List<RabbitMQHeader> headers) {
		return headers.stream().filter(this::isValidHeader).collect(Collectors.toMap(RabbitMQHeader::getKey, RabbitMQHeader::getValue));
	}

	boolean isValidHeader(RabbitMQHeader header) {
		return !StringUtils.isEmpty(header.getKey()) && !StringUtils.isEmpty(header.getValue());
	}

	private void logDebugHeaders(List<RabbitMQHeader> headers) {
		headers.stream()
			.filter(this::isValidHeader)
			.map(RabbitMQHeader::toString)
			.collect(Collectors.toList())
			.forEach(LOGGER::debug);
	}

}
