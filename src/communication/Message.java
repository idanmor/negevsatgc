package communication;

import java.nio.charset.Charset;

public class Message {
	private String messageText;
	
	public Message () {
		this.messageText = "";
	}
	
	public Message (String messageText) {
		this.messageText = messageText;
	}
	
	public void append (String addition) {
		this.messageText.concat(addition);
	}
	
	public boolean validate() {
		return true;
	}
	
	public String toString() {
		return messageText;
	}
	
	public byte[] getBytes() {
		return messageText.getBytes(Charset.forName("UTF-8"));
	}
}
