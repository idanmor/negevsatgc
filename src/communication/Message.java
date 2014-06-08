package communication;

import java.io.IOException;
import java.nio.charset.Charset;

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
	
	public Document toDocument () {
		DOMParser parser = new DOMParser();
		try {
		    parser.parse(new InputSource(new java.io.StringReader(messageText)));
		    Document doc = parser.getDocument();
		    String message = doc.getDocumentElement().getTextContent();
		    System.out.println(message);
		    System.out.println(doc.toString());
		    return doc;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	public Document toDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(this.messageText));
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
	*/
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
