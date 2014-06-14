package Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import communication.CommunicationManager;
import data.Command;
import data.DataManager;
import data.Mission;
import data.Satellite;
import data.Status;
import negevsatgui.MainWindow;
import negevsatgui.MainWindow.DataAcquisitionMode;
import negevsatgui.MainWindow.SatteliteMods;
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
		mainWindow.setSatteliteStatusText(status);
	}

	public void sendImmidiateSatelliteModeCommand(SatteliteMods selectedItem) {
		Mission newMission = new Mission(null, selectedItem.getCommand(), 1);
		addToLog("Sending mission to Sattelite -" + selectedItem.toString());
		//TODO 
	}

	public void sendImmidiateDataAquisitionCommand(DataAcquisitionMode dataAcquisitionMode) {
		Mission newMission =  new Mission(null, dataAcquisitionMode.getCommand(), 1);
		addToLog("Sending mission to Sattelite -" + dataAcquisitionMode.toString());
	}

	public void sendImmidiateComponentStatusChange(Command command) {
		Mission newMission =  new Mission(null,command, 1);
		addToLog("Sending change component status mission to Sattelite");
		refreshSatelliteController(new Satellite(Status.ON, new Timestamp(1), Status.ON,new Timestamp(1), Status.ON, new Timestamp(1), Status.ON, new Timestamp(1), Status.ON, new Timestamp(1), Status.ON, new Timestamp(1)));
		//TODO 
	}

	public void refreshSatelliteController(Satellite st){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				addToLog("Sattelite status data recieved");
				SattelitePictureController cont = MainWindow.getMainWindow().getSatellitePictureController();
				cont.nonSupdateSateliteStatus(st);
				//SattelitePictureController.updateSateliteStatus(st);
				
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
		
		String date = writeFormat.format(dateString);
		
		Mission mission = new Mission(new Timestamp(writeFormat.getCalendar().getTimeInMillis()), c, 1);
		
		
	}

}
