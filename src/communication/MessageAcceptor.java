package communication;

import org.w3c.dom.*;

public class MessageAcceptor implements Runnable {
	
	public void run ()
    {
		while (true) {
			try {
				System.out.println("DEBUG: MessageAcceptor waiting for new message");
				Message m = CommunicationManager.getInstance().getMessageAcceptorQueue().take();
				System.out.println("DEBUG: Message Accepted");
				//System.out.println(m.toString());
				Document msg = m.toDocument();
//				try {
//					parseMessage(msg);
//				} catch (InvalidMessageException e) {
//					System.out.println("Error: " + e.getMessage());
//				}
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}       
    }
	
	public void parseMessage(Document msg) throws InvalidMessageException {
    	NodeList nList = msg.getElementsByTagName("downstreamPacket");
    	if(nList.getLength() == 0) {
    		throw new InvalidMessageException("No downstreamPacket Element!");
    	}
    	System.out.println(msg.toString());
    	Node packet = nList.item(0);
    	Node typeNode = packet.getFirstChild();
    	if(!typeNode.getLocalName().equals("type")) {
    		throw new InvalidMessageException("No type Element!");
    	}
    	String type = typeNode.getTextContent();
    	switch(type) {
    	case "Static":
    		parseStaticPacket(packet);
    		break;
    	case "Temperature":
    		parseTemperaturePacket(packet);
    		break;
    	case "Energy":
    		parseEnergyPacket(packet);
    		break;
    	default:
    		throw new InvalidMessageException("Wrong packet type!");
    	}
    }
	
	public void parseStaticPacket (Node packet) {
		System.out.println("DEBUG: Static packet parsing");
	}
	
	public void parseTemperaturePacket (Node packet) {
		System.out.println("DEBUG: Temperature packet parsing");
	}
	
	public void parseEnergyPacket (Node packet) {
		System.out.println("DEBUG: Energy packet parsing");
	}
}
