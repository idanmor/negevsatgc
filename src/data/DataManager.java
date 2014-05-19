package data;

public class DataManager {
	public static final String tle = 
					"ISS (ZARYA)\n"
					+ "1 25544U 98067A   08264.51782528 -.00002182  00000-0 -11606-4 0  2927\n"
					+ "2 25544  51.6416 247.4627 0006703 130.5360 325.0288 15.72125391563537";
	
	private static DataManager instance = null;
	
	private Satellite latestSatData;
	
	private DataManager() {
		// TODO Initialize latestSatData with the latest data from the Satellite
	}
	
	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}
}
