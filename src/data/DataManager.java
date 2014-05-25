package data;

import java.sql.Timestamp;
import java.util.List;

import persistency.dbConnection;

public class DataManager {
	private static dbConnection db;
	public static final String tle = 
					"ISS (ZARYA)\n"
					+ "1 25544U 98067A   08264.51782528 -.00002182  00000-0 -11606-4 0  2927\n"
					+ "2 25544  51.6416 247.4627 0006703 130.5360 325.0288 15.72125391563537";
	
	private static DataManager instance = null;
	
	private Satellite latestSatData;
	
	private DataManager() {
		db= dbConnection.getdbCon();
	}
	
	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}
	public static List<? extends Component> getComponentData(String component, Timestamp date1, Timestamp date2){
		return db.getComponentData(component, date1, date2);
	}
	
	public static List<Temprature> getTemprature(Timestamp date1, Timestamp date2){
		return dbConnection.getTemprature(date1, date2);
	}
	
	public static List<Energy> getEnergy(Timestamp date1, Timestamp date2){
		return dbConnection.getEnergy(date1, date2);
	}
}
