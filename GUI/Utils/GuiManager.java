package Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart.Data;
import communication.CommunicationManager;
import data.Command;
import data.DataManager;
import data.Mission;
import data.Satellite;
import data.Status;
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

	public void sendImmidiateSatelliteModeCommand(SatelliteMods selectedItem) {
		Mission newMission = DataManager.getInstance().insertMission(null, selectedItem.getCommand(), 1);
		addToLog("Sending mission to Satellite -" + selectedItem.toString());
		CommunicationManager.getInstance().sendMission(newMission);
	}

	public void sendImmidiateDataAquisitionCommand(DataAcquisitionMode dataAcquisitionMode) {
		Mission newMission =  DataManager.getInstance().insertMission(null, dataAcquisitionMode.getCommand(), 1);
		addToLog("Sending mission to Sattelite -" + dataAcquisitionMode.toString());
		CommunicationManager.getInstance().sendMission(newMission);
	}

	public void sendImmidiateComponentStatusChange(Command command) {
		Mission newMission =  DataManager.getInstance().insertMission(null,command, 1);
		addToLog("Sending change component status mission to Satellite");
		CommunicationManager.getInstance().sendMission(newMission);
	}

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
	
	public void addToLog(String data){
		if(data == null){
			return;
		}
		
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		
		String date = writeFormat.format(Calendar.getInstance().getTime());
		addToLog(date, data);
	}
	
	public void addToLog(String date,String data){
		if(data == null){
			return;
		}	
			mainWindow.addToLog("("+ date + ")" + data);
	}

	public void sendSatelliteModeCommand(String dateString, Command c) {
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		dateString = dateString.replace("/", "-");
		Date date = null;
		try {
			date = writeFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//Date d = new Date(arg0)
		Timestamp ts = new Timestamp(date.getTime());
		Mission mission = new Mission(ts, c, 1);
		DataManager.getInstance().insertMission(ts, c, 1);
		CommunicationManager.getInstance().sendMission(mission);
		
	}
	
	public SatelliteState getLastSatelliteState(){
		DataManager dm = DataManager.getInstance();
		return dm.getLastSateliteState();
	}

}
