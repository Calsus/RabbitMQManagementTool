package be.telenet.yellowbelt.rmt.models;

import lombok.Data;

/**
 * Model containing the key and the value of a header.
 * <p>
 * Created by Jerry-Lee on 23/01/2017.
 */
@Data
public class RabbitMQHeader {
	private String key;
	private String value;

	public RabbitMQHeader() {
		this("", "");
	}

	public RabbitMQHeader(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
