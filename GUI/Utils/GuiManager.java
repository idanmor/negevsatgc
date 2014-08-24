package Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import orbit.OrbitManager;
import javafx.application.Platform;
import communication.CommunicationManager;
import data.Command;
import data.DataManager;
import data.Mission;
import data.Satellite;
import data.Satellite.SatelliteState;
import negevsatgui.MainWindow;
import negevsatgui.MainWindow.DataAcquisitionMode;
import negevsatgui.MainWindow.SatelliteMods;
import negevsatgui.SattelitePictureController;

public class GuiManager {
	private static GuiManager instance = null;
	private MainWindow mainWindow;
	private GuiManager(){
		this.mainWindow = MainWindow.getMainWindow();
	}

	public static GuiManager getInstance(){
		if(instance == null){
			instance = new GuiManager();
		}
		return instance;
	}
	/**
	 * Connected/Disconnected
	 * @param status
	 */
	public void setNewSatteliteStatusInGui(String status){
		mainWindow.setSatelliteStatusText(status);
	}
	/**
	 * Sends immediate satellite command to the satellite, only viable in pass mode
	 * The execution is immediate
	 * For example change the mode of the satellite to safe mode
	 * @param command
	 */
	public void sendImmidiateSatelliteModeCommand(SatelliteMods selectedItem) {
		Mission newMission = DataManager.getInstance().insertMission(null, selectedItem.getCommand(), 1);
		addToLog("Sending mission to Satellite -" + selectedItem.toString());
		CommunicationManager.getInstance().sendMission(newMission);
	}
	/**
	 * Sends immediate Data acquisition command to the satellite, only viable in pass mode.
	 * For example the gui asks the satellite for temperature statistics.
	 * The execution is immediate
	 * @param command 
	 */
	public void sendImmidiateDataAquisitionCommand(DataAcquisitionMode dataAcquisitionMode) {
		Mission newMission =  DataManager.getInstance().insertMission(null, dataAcquisitionMode.getCommand(), 1);
		addToLog("Sending mission to Sattelite -" + dataAcquisitionMode.toString());
		CommunicationManager.getInstance().sendMission(newMission);
	}
	
	/**
	 * Sends immediate component command to the satellite, only viable in pass mode
	 * The execution is immediate
	 * @param command
	 */
	public void sendImmidiateComponentStatusChange(Command command) {
		Mission newMission =  DataManager.getInstance().insertMission(null,command, 1);
		addToLog("Sending change component status mission to Satellite");
		CommunicationManager.getInstance().sendMission(newMission);
	}

	/**
	 * Function used when there is a new data from the satellite(for example entered pass mode)
	 * @param st
	 */
	public void refreshSatelliteController(Satellite st){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				addToLog("Satellite status data recieved");
				SattelitePictureController cont = MainWindow.getMainWindow().getSatellitePictureController();
				cont.nonSupdateSateliteStatus(st);
				MainWindow.getMainWindow().setSatelliteState(st);
				
			}
		});
	
	}
	/**
	 * Add data to the log, the date that will be written is the exact date that this function was called
	 * @param data
	 */
	public void addToLog(String data){
		if(data == null){
			return;
		}
		
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		
		String date = writeFormat.format(Calendar.getInstance().getTime());
		addToLog(date, data);
	}
	
	/**
	 * Adds data to log
	 * @param date date for receiving the data
	 * @param data the data to be written to the log
	 */
	public void addToLog(String date,String data){
		if(data == null){
			return;
		}	
			mainWindow.addToLog("("+ date + ")" + data);
	}

	/**
	 * Sends Mode command to the satellite
	 * @param dateString
	 * @param c
	 */
	public void sendSatelliteModeCommand(String dateString, Command c) {
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		dateString = dateString.replace("/", "-");
		Date date = null;
		try {
			date = writeFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		Timestamp ts = new Timestamp(date.getTime());
		Mission mission = new Mission(ts, c, 1);
		DataManager.getInstance().insertMission(ts, c, 1);
		CommunicationManager.getInstance().sendMission(mission);
		
	}
	
	/**
	 * Gets the last satellite state recorded in the database
	 * @return
	 */
	public SatelliteState getLastSatelliteState(){
		DataManager dm = DataManager.getInstance();
		return dm.getLastSateliteState();
	}
	
	/**
	 * Checks and returns the current satellite pass status
	 * 
	 * @return true if in pass false otherwise
	 */
	public boolean getInPassPhase(){
		return OrbitManager.getInstance().isPassPhase();
	}

	public String getSatteliteMapView() {
		return getInPassPhase() ? Constants.INPASS : Constants.NOT_PASS;
		
	}

}
