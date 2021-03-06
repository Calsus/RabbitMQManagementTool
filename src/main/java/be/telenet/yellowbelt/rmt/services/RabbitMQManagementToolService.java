package be.telenet.yellowbelt.rmt.services;

import be.telenet.yellowbelt.rmt.models.Header;
import org.apache.camel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
	 * @param headers List of {@link Header} containing all the headers that have to be added.
	 * @param content Content that have to be send with the message
	 * @param queue   Name of the queue
	 */
	public void sendMessage(List<Header> headers, String content, String queue) {
		logDebugHeaders(headers);

		Map<String, Object> headerMap = getMapWithValidHeaders(headers);
		ProducerTemplate template = camelContext.createProducerTemplate();

		String endpoint = rabbitMQEndpoint + hostname + ":" + port + "/" + extractExchangeNameFromQueue(queue) + "?exchangeType=fanout&bridgeEndpoint=true&queue=" + queue;

		LOGGER.debug("Send Endpoint: " + endpoint);

		template.sendBodyAndHeaders(endpoint, content, headerMap);

	}

	/**
	 * <p>Receive messages from a RabbitMQ queue.</p>
	 *
	 * @param queueName Name of the queue
	 * @return a list of {@link Message}
	 */
	public List<Message> receiveMessages(String queueName) throws Exception {
		String endpoint = rabbitMQEndpoint + hostname + ":" + port + "/" + extractExchangeNameFromQueue(queueName) + "?exchangeType=fanout&bridgeEndpoint=true&queue=" + queueName;

		LOGGER.debug("Send Endpoint: " + endpoint);

		List<Exchange> exchanges = new ArrayList<>();
		ConsumerTemplate consumer = camelContext.createConsumerTemplate();
		consumer.start();
		ProducerTemplate producer = camelContext.createProducerTemplate();
		while (true) {
			Exchange exchange = consumer.receive(endpoint, 1000);
			if (exchange != null) {
				exchanges.add(exchange);
			} else {
				break;
			}
		}
		consumer.stop();
		exchanges.forEach(exchange -> producer.send(endpoint, exchange));

		return exchanges.stream().map(Exchange::getIn).collect(Collectors.toList());
	}

	String extractExchangeNameFromQueue(String queue) {
		return queue.split("-queue")[0];
	}

	/**
	 * This method will filter out the invalid headers and then convert it a Map of String, Object.
	 *
	 * @param headers List of {@link Header}
	 * @return returns a Map of String, Object because the method {@link ProducerTemplate#sendBodyAndHeaders(String, Object, Map)} requires it.
	 **/
	Map<String, Object> getMapWithValidHeaders(List<Header> headers) {
		return headers.stream().filter(this::isValidHeader).collect(Collectors.toMap(Header::getKey, Header::getValue));
	}

	boolean isValidHeader(Header header) {
		return !StringUtils.isEmpty(header.getKey()) && !StringUtils.isEmpty(header.getValue());
	}

	private void logDebugHeaders(List<Header> headers) {
		headers.stream()
			.filter(this::isValidHeader)
			.map(Header::toString)
			.collect(Collectors.toList())
			.forEach(LOGGER::debug);
	}

}
