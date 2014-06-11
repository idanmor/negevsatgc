package Utils;

import negevsatgui.MainWindow;

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
	
	public void setNewSatteliteStatusInGui(String status){
		mainWindow.setSatteliteStatusText(status);
	}

}
