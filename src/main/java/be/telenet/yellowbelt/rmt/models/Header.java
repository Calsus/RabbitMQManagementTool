package be.telenet.yellowbelt.rmt.models;

import lombok.Data;

/**
 * Model containing the key and the value of a header.
 * <p>
 * Created by Jerry-Lee on 23/01/2017.
 */
@Data
public class Header {
	private String key;
	private String value;

	public Header() {
		this("", "");
	}

	public Header(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
