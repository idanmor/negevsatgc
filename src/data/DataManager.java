package data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import persistency.dbConnection;

public class DataManager {
	private dbConnection db;
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
	
	public void setLatestSatData(Satellite sat){
		this.latestSatData=sat;
	}
	
	public List<Temprature> getTemprature(Timestamp startDate, Timestamp endDate){
		return db.getTemprature(startDate, endDate);
	}
	
	public List<Energy> getEnergy(Timestamp startDate, Timestamp endDate){
		return db.getEnergy(startDate, endDate);
	}
	
	public List<Satellite> getStatus(Timestamp startDate, Timestamp endDate){
		return db.getSatelliteData(startDate, endDate);
	}
	
	public List<Mission> getMission(Timestamp creationTimestamp){
		return db.getMission(creationTimestamp);
	}

	public Satellite getLatestSatData(){
		return(this.latestSatData);
	}
	public ArrayList<Pair<String,Status>> getListOfStatusPairs(Satellite satellite){
		ArrayList<Pair<String, Status>> statusPairs=new ArrayList<Pair<String,Status>>();
		statusPairs.add(new Pair("Temprature",satellite.getTempratureStatus()));
		statusPairs.add(new Pair("Energy",satellite.getEnergyStatus()));
		statusPairs.add(new Pair("Sband",satellite.getSbandStatus()));
		statusPairs.add(new Pair("Payload",satellite.getPayloadStatus()));
		statusPairs.add(new Pair("SolarPanels",satellite.getSolarPanelsStatus()));
		statusPairs.add(new Pair("Thermal",satellite.getThermalStatus()));
		return(statusPairs);
	}
	
	public ArrayList<Pair<String,Float>> getReadingsPerSensor(Component component){
		ArrayList<Pair<String, Float>> sensorsValues=component.getSensorsValues();	
		return sensorsValues;
	}

	
	 public void insertMission(Timestamp _missionExecutionTS, Command _command, int _priority){
		 db.insertMission(_missionExecutionTS, _command, _priority);
	 }
	 
	 public void insertSatellite(Status temp, Status energy, Status Sband, Status Payload,Status SolarPanels, Status Thermal, Timestamp ts){
		 db.insertSatellite(temp, energy, Sband, Payload, SolarPanels, Thermal, ts);
	 }
	 
	 public void insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp ts){
		 db.insertTemprature(sensor1, sensor2, sensor3, ts);
	 }
	 
	 public void insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp ts){
		 db.insertEnergy(batt1V, batt2V, batt3V, batt1C, batt2C, batt3C, ts);
	 }
	 
	 public void deleteComponent(String component,Timestamp timeStamp) {
		 db.deleteComponent(component, timeStamp);
	 }
	    
	 public void deleteCompletedMission(Timestamp creationTimestamp){
		 db.deleteCompletedMission(creationTimestamp);
	 }
}
