package communication;

public class CommunicationManager {

	private static CommunicationManager instance = null;
	
	private CommunicationManager() {
		
	}
	
	public static CommunicationManager getInstance() {
		if (instance == null) 
			instance = new CommunicationManager();
		return instance;
	}
}
