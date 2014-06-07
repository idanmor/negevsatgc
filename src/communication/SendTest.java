package communication;

public class SendTest {

	public static void main(String[] args) {
		try {
			CommunicationManager.getInstance().connect("COM2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg1 = "Yesterday all my troubles seemed so far away,\nNow it looks as though they're here to stay.\n";
		String msg2 = "Oh I believe in yesterday.\nSuddenly I'm not half the man I've used to be\n";
		String msg3 = "There's a shadow hanging over me.\nOh yesterday came suddenly\n";
		CommunicationManager.getInstance().sendMessage(new Message(msg1));
		CommunicationManager.getInstance().sendMessage(new Message(msg2));
		CommunicationManager.getInstance().sendMessage(new Message(msg3));
		while(true) {
			
		}
	}

}
