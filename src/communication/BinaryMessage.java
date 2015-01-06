package communication;

import java.util.Vector;

import org.w3c.dom.Document;

public class BinaryMessage implements MessageInterface {

	private Vector<Byte> Content;
	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Vector<Byte> getContent(){
	return this.Content;
	}
	
	public void setContent(Vector<Byte> con){
		this.Content = new Vector<Byte>(con);
	}

	@Override
	public Document toDocument() {
		// TODO Auto-generated method stub
		return null;
	}

}
