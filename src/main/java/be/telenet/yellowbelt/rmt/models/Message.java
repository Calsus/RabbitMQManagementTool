package be.telenet.yellowbelt.rmt.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model containing the content and the headers of a message.
 * <p>
 * Created by Jerry-Lee on 27/01/2017.
 */
@Data
public class Message {

	private String content;
	private List<Header> headers;

	public Message() {
		headers = new ArrayList<>();
	}

	public Message(String content, List<Header> headers) {
		this.content = content;
		this.headers = headers;
	}
}
