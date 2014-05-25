package communication;

public class Test {

	public static void main(String[] args) {
		try {
			CommunicationManager.getInstance().connect("COM2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(true) {
			
		}
	}

}
