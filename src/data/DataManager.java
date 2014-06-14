package data;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import Utils.GuiManager;
import communication.CommunicationManager;
import javafx.util.Pair;
import persistency.dbConnection;

public class DataManager {
	private static final String comPort = "COM2";
	private dbConnection db;
	private CommunicationManager comm;
	
	private static DataManager instance = null;
	
	private Satellite latestSatData;
	
	private DataManager() {
		db = dbConnection.getdbCon();
		comm = CommunicationManager.getInstance();
		db.creatTables();
		latestSatData=db.getLatestSatelliteData();
			try {
				comm.connect(comPort);
			} catch (NoSuchPortException | PortInUseException
					| UnsupportedCommOperationException | IOException
					| TooManyListenersException e) {
				System.out.println("ERROR: Could not connect to " + comPort + ". Running in offline mode.");
				System.out.println("Error message: " + e.getMessage());
			}
	}
	
	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}
	
	public void setLatestSatData(Satellite sat){
		this.latestSatData=sat;
		GuiManager.getInstance().refreshSatelliteController(sat);
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
	
	public ArrayList<Pair<String,Pair<Status,Timestamp>>> getListOfStatusPairs(Satellite satellite){
		ArrayList<Pair<String,Pair<Status,Timestamp>>> statusPairs=new ArrayList<Pair<String,Pair<Status,Timestamp>>>();
		statusPairs.add(new Pair("Temprature",new Pair(satellite.getTempratureStatus(),satellite.getTempratureTS())));
		statusPairs.add(new Pair("Energy",new Pair(satellite.getEnergyStatus(),satellite.getEnergyTS())));
		statusPairs.add(new Pair("Sband",new Pair(satellite.getSbandStatus(),satellite.getSbandTS())));
		statusPairs.add(new Pair("Payload",new Pair(satellite.getPayloadStatus(),satellite.getPayloadTS())));
		statusPairs.add(new Pair("SolarPanels",new Pair(satellite.getSolarPanelsStatus(),satellite.getSolarPanelsTS())));
		statusPairs.add(new Pair("Thermal",new Pair(satellite.getThermalStatus(),satellite.getThermalTS())));
		
		return(statusPairs);
	}
	
	public Map<String,Float> getReadingsPerSensor(Component component){
		
		return component.getSensorsValues();
	}

	
	 public Mission insertMission(Timestamp _missionExecutionTS, Command _command, int _priority){
		 return db.insertMission(_missionExecutionTS, _command, _priority);
	 }
	 
	 public Satellite insertSatellite(Status temp, Timestamp tempTS, Status energy, Timestamp energyTS, 
			 							Status Sband, Timestamp SbandTS, Status Payload, Timestamp PayloadTS, 
			 							Status SolarPanels, Timestamp SolarPanelsTS, Status Thermal, Timestamp ThermalTS){
		 return db.insertSatellite(temp, tempTS, energy, energyTS, Sband, SbandTS, Payload, PayloadTS, SolarPanels, 
				 					SolarPanelsTS, Thermal, ThermalTS);
	 }
	 
	 public Satellite insertSatellite(Satellite.SatelliteState state, Status temp, Timestamp tempTS, Status energy, 
			 					Timestamp energyTS, Status Sband, Timestamp SbandTS, Status Payload, Timestamp PayloadTS, 
			 					Status SolarPanels, Timestamp SolarPanelsTS, Status Thermal, Timestamp ThermalTS){
		 return db.insertSatellite(state, temp, tempTS, energy, energyTS, Sband, SbandTS, Payload, PayloadTS, 
				 				SolarPanels, SolarPanelsTS, Thermal, ThermalTS);
	 }
	 
	 public Temprature insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp ts){
		 return db.insertTemprature(sensor1, sensor2, sensor3, ts);
	 }
	 
	 public Energy insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp ts){
		 return db.insertEnergy(batt1V, batt2V, batt3V, batt1C, batt2C, batt3C, ts);
	 }
	 
	 public void deleteComponent(String component,Timestamp timeStamp) {
		 db.deleteComponent(component, timeStamp);
	 }
	    
	 public void deleteCompletedMission(Timestamp creationTimestamp){
		 db.deleteCompletedMission(creationTimestamp);
	 }
}
