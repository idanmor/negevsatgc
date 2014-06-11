package communication;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class Message {
	private String messageText;
	
	public Message () {
		this.messageText = "";
	}
	
	public Message (String messageText) {
		this.messageText = messageText;
	}
	
	public void append (String addition) {
		messageText = this.messageText.concat(addition);
	}
	
	public Document toDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			String noTabsAndSpacesMsg = this.messageText.replaceAll(">\\s+<", "><").trim();
			InputSource is = new InputSource(new StringReader(noTabsAndSpacesMsg));
			return builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
