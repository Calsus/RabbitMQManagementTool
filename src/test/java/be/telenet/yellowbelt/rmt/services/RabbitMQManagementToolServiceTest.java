package be.telenet.yellowbelt.rmt.services;

import be.telenet.yellowbelt.rmt.models.Header;
import org.apache.camel.CamelContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests the sending of a message.
 * Created by Jerry-Lee on 25/01/2017.
 */
public class RabbitMQManagementToolServiceTest {

	@Autowired
	private CamelContext context;

	private RabbitMQManagementToolService service;

	@Before
	public void setup() {
		service = new RabbitMQManagementToolService();
	}

	@Test
	public void testThatExtractingOfExchangeNameIsCorrect() {
		String exchangeName = service.extractExchangeNameFromQueue("cim-importer-queue");
		assertThat(exchangeName, is("cim-importer"));
	}

	@Test
	public void testThatMapWithValidHeadersReturnCorrectMap() {
		List<Header> headers = new ArrayList<>();
		headers.add(new Header("ContentProviderId", "magnetmedia"));
		headers.add(new Header());
		headers.add(new Header());
		headers.add(new Header());
		Map<String, Object> map = service.getMapWithValidHeaders(headers);
		assertThat(headers.size(), is(4));
		assertThat(map.size(), is(1));
	}

	@Test
	public void testThatHeaderIsInvalid() {
		Header header = new Header();
		assertFalse(service.isValidHeader(header));
	}

	@Test
	public void testThatHeaderIsValid() {
		Header header = new Header("ContentProviderId", "magnetmedia");
		assertTrue(service.isValidHeader(header));
	}

}
