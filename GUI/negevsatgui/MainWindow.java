/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negevsatgui;

import MenuItems.MainMenu;
import Panels.MissionPanel;
import Panels.SatteliteStatusPanel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import Utils.Constants;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import misc.SattaliteUtils;

/**
 *
 * @author Max
 */
public class MainWindow{
	private BorderPane mainPane;
	private static MainWindow instance;
	private ComboBox<SatteliteMods> comboSattelites;
	public MainWindow(){
		super();
		synchronized(MainWindow.class){
			if(instance != null) throw new UnsupportedOperationException(
					getClass()+" is singleton but constructor called more than once");
			instance = this;

		}
	}
	public static MainWindow  getMainWindow(){
		if(instance == null){
			return new MainWindow();
		}
		return instance;
	}


	public void start(Stage primaryStage) {
		int height = 600;
		int width = 810;
		mainPane = new BorderPane();  
		BorderPane root = new BorderPane();
		root.setTop(new MainMenu());
		root.setCenter(mainPane);
		mainPane.setId("mainPane");
		
		Label smartSentance = new Label("Anyone who has never made a mistake has never tried anything new.\n - Albert Einstein");
		smartSentance.setStyle("-fx-font-size:20;");
		Label satteliteStatus = new Label(getSatteliteStatus());
		satteliteStatus.setStyle("-fx-font-size:20;");
		mainPane.setCenter(smartSentance);
		mainPane.setBottom(satteliteStatus);
		Scene scene = new Scene(root, width, height);         
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		(NegevSatGui.class.getResource(Constants.CSS_MAIN).toExternalForm());
		//mainPane.getStylesheets().add("mainClass.css");
		mainPane.getStyleClass().add("main");
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException ex) {
//			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//		}
	
		mainPane.prefHeightProperty().bind(scene.heightProperty());
		mainPane.prefWidthProperty().bind(scene.widthProperty());
		showMainScreen(mainPane);
		
		
	}

	public BorderPane getMainPane(){
		return mainPane;
	}

	TextArea demoListView;
	public void showMainScreen(BorderPane mainPane){
		VBox mainScreen = new VBox();
		HBox firtsLine = new HBox();
		HBox secondLine = new HBox();
		VBox logBox = new VBox();
		HBox buttonBox = SattaliteUtils.getHBox(10);
		  try {
			Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
			//firtsLine.getChildren().addAll(new MissionPanel(getMainPane()),new SatteliteStatusPanel(getMainPane()));
			//firtsLine.getChildren().addAll(new MissionPanel(getMainPane()),root);
			firtsLine.getChildren().addAll(new MissionPanel(getMainPane()),new SatteliteStatusPanel(getMainPane(),root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		demoListView = new TextArea();
		demoListView.setEditable(false);
		TextField toLogger = new TextField();
		toLogger.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					demoListView.appendText(addDateToString(toLogger.getText()) + "\n");
					toLogger.setText("");
				}
			}
		});
		Label logTitle = new Label("Log Screen");
		demoListView.setText("(1/1/2014)Sattelite status: = Good \n(1/1/2014)Mulfunction in Power supply \n(1/1/2014)Battery status: 60% \n");
		demoListView.setPrefWidth(400);
		logBox.getChildren().addAll(logTitle,demoListView,toLogger);
		buttonBox.getChildren().addAll(generateButtonHolders());
		secondLine.getChildren().addAll(logBox,buttonBox);
		mainScreen.getChildren().addAll(firtsLine,secondLine);
		//sattelite.png
		mainPane.setCenter(mainScreen);
		firtsLine.autosize();
	}
	private List<VBox> generateButtonHolders(){
		final String[] logOutPut = {"Moving to power save mode... ", "Moving to maintenece mode...",
				"Restart command initiated..", "Sending telemetry file...", "Sending scheduled commands", "Running system analasys"};
		String[] buttonNames = {"Power Save", "Maitenance Mode",
				"Restart systems", "Send Telemetry", "Send shceduled command", "Analyze systems"};
		VBox one = SattaliteUtils.getVbox(15);
		VBox two =  SattaliteUtils.getVbox(15);
		int numOfButton = logOutPut.length;

		List<VBox> buttonList = new ArrayList<VBox>();
		for(int i = 0; i < numOfButton; i++){
			Button b = new Button(buttonNames[i]);
			final String a = logOutPut[i];
			b.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {

					demoListView.appendText(addDateToString(a) + "\n");
				}
			});
			if(i < numOfButton/2){
				one.getChildren().add(b);
			}else{
				two.getChildren().add(b);
			}
		}
		one.getChildren().add(getImmidiateModeChangeBox());
		buttonList.add(one);
		buttonList.add(two);

		return buttonList;
	}

	private String addDateToString(String text){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return "(" + dateFormat.format(date) + ")" + text ;

	}

	

	private HBox getImmidiateModeChangeBox(){
		comboSattelites = new ComboBox<>();
		Button send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// TODO send command

			}
		});
		comboSattelites.getItems().addAll(SatteliteMods.values());
		HBox container = new HBox();
		container.getChildren().addAll(comboSattelites,send);
		return container; 	
	}

	private enum SatteliteMods{
		SAFE("Safe mode"),
		OPERATION("Operation mode"),
		MAINTENANCE("Maintenace"),
		STANDBYE("Standby mode");

		private String command;
		private SatteliteMods(String command){
			this.command = command;
		}

		public String getCommand(){
			return command;
		}
		@Override
		public String toString(){
			return command;
		}
	}

    private String getSatteliteStatus() {
        String[] generatedStatus = {"Pass phase: Time remaining 3:20","Pass phase: In 2:30:45", "Connection Loss"};
        return generatedStatus[(int)(Math.random()*generatedStatus.length)];
    }
    
    
    
}
