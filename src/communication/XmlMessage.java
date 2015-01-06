package communication;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class XmlMessage implements MessageInterface {
	private String messageText;
	private Vector<Byte> ToSend;
	
	public XmlMessage () {
		this.messageText = "";
		this.ToSend = new  Vector<Byte>();
	}
	
	public XmlMessage (String messageText) {
		this.messageText = messageText;
		this.ToSend = new  Vector<Byte>();
	}
	
	public void append (String addition) {
		messageText = this.messageText.concat(addition);
	}
	
	public void setTosend(Vector<Byte> v){
	this.ToSend = new Vector<Byte>(v);
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
	
	public void SetMessageText(String txt){
		this.messageText = txt;
	}
	
	public byte[] getBytes() {
		return messageText.getBytes(Charset.forName("UTF-8"));
	}
}
