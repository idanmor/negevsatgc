package Utils;

import java.sql.Timestamp;

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
		//TODO 
	}

	public void sendImmidiateDataAquisitionCommand(DataAcquisitionMode dataAcquisitionMode) {
		Mission newMission =  new Mission(null, dataAcquisitionMode.getCommand(), 1);
		//TODO 
	}

	public void sendImmidiateComponentStatusChange(Command command) {
		Mission newMission =  new Mission(null,command, 1);
		refreshSatelliteController(new Satellite(Status.ON, new Timestamp(0), Status.ON,new Timestamp(0), Status.ON, new Timestamp(0), Status.ON, new Timestamp(0), Status.ON, new Timestamp(0), Status.ON, new Timestamp(0)));
		//TODO 
	}

	public void refreshSatelliteController(Satellite st){
		SattelitePictureController cont = MainWindow.getMainWindow().getSatellitePictureController();
		cont.updateSateliteStatus(st);
	}

}
