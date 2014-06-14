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

import data.Command;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import Utils.Constants;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import misc.SattaliteUtils;
import Utils.*;

/**
 *
 * @author Max
 */
public class MainWindow{
	private BorderPane mainPane;
	private static MainWindow instance;
	private SattelitePictureController pictureController;
	private ComboBox<SatteliteMods> comboSattelites;
	private ComboBox<DataAcquisitionMode> dataAquisitionModeBox;
	private ComboBox<Component> componentStatusBox;
	private ComboBox<State> buttonBox;
	private Label satteliteStatus;
	private FXMLLoader fxmlLoader;
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
		int width = 910;
		mainPane = new BorderPane();  
		BorderPane root = new BorderPane();
		root.setTop(new MainMenu());
		root.setCenter(mainPane);
		mainPane.setId("mainPane");

		Label smartSentance = new Label("Anyone who has never made a mistake has never tried anything new.\n - Albert Einstein");
		smartSentance.setStyle("-fx-font-size:20;");
		satteliteStatus = new Label(getSatteliteStatus());
		satteliteStatus.setStyle("-fx-font-size:20;");
		mainPane.setCenter(smartSentance);
		mainPane.setBottom(satteliteStatus);
		Scene scene = new Scene(root, width, height);         
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		(NegevSatGui.class.getResource(Constants.CSS_MAIN).toExternalForm());
		//mainPane.getStylesheets().add("mainClass.css");
		mainPane.getStyleClass().add("main");
		
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
		firtsLine.setSpacing(10);
		HBox secondLine = new HBox();
		VBox logBox = new VBox();
		HBox buttonBox = SattaliteUtils.getHBox(10);
		firtsLine.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("FXMLDocument.fxml"));
		try {
			fxmlLoader.load(getClass().getResource("FXMLDocument.fxml").openStream());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		pictureController = fxmlLoader.<SattelitePictureController>getController();
		Pane left = new Pane();
		Pane right = new Pane();
		MissionPanel mp = new MissionPanel(getMainPane(),left);
		left.getChildren().add(mp);
		right.getChildren().add(new SatteliteStatusPanel(getMainPane(),fxmlLoader.getRoot()));
	//	left.setPrefSize(300, 400);
		//right.setPrefSize(300, arg1);
		//firtsLine.getChildren().addAll(new MissionPanel(getMainPane(),left),new SatteliteStatusPanel(getMainPane(),fxmlLoader.getRoot()));
		firtsLine.getChildren().addAll(left,right);
		
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
		//demoListView.setText("(1/1/2014)Sattelite status: = Good \n(1/1/2014)Mulfunction in Power supply \n(1/1/2014)Battery status: 60% \n");
		demoListView.setPrefWidth(400);
		logBox.getChildren().addAll(logTitle,demoListView,toLogger);
		buttonBox.getChildren().addAll(generateButtonHolders());
		secondLine.getChildren().addAll(logBox,buttonBox);
		mainScreen.getChildren().addAll(firtsLine,secondLine);
		//sattelite.png
		mainPane.setCenter(mainScreen);
		firtsLine.autosize();
	}
	private List<HBox> generateButtonHolders(){
		HBox one = new HBox();
		one.setSpacing(10);
		List<HBox> buttonList = new ArrayList<HBox>();
		List<Node[]> listOfImmediateActions = new ArrayList<>();
		listOfImmediateActions.add(getImmidiateModeChangeBox());
		listOfImmediateActions.add(getDataAcquisitionModeBox());
		listOfImmediateActions.add(getComponentsStatusModeBox());
		VBox first = new VBox();
		first.setSpacing(20);
		VBox second = new VBox();
		second.setSpacing(10);
		VBox third = new VBox();
		third.setSpacing(13);
		for(int i = 0 ; i < listOfImmediateActions.size() ; i++){
			first.getChildren().add(listOfImmediateActions.get(i)[0]);
			second.getChildren().add(listOfImmediateActions.get(i)[1]);
			third.getChildren().add(listOfImmediateActions.get(i)[2]);
		}
		
		one.getChildren().addAll(first,second,third);
		buttonList.add(one);
	

		return buttonList;
	}

	private String addDateToString(String text){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return "(" + dateFormat.format(date) + ")" + text ;

	}



	private Node[] getImmidiateModeChangeBox(){
		comboSattelites = new ComboBox<>();
		Node[] nodes = new Node[3];
		Button send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SatteliteMods item = getMainWindow().comboSattelites.getSelectionModel().getSelectedItem();
				if(item == null){
					return;
				}
				GuiManager.getInstance().sendImmidiateSatelliteModeCommand(getMainWindow().comboSattelites.getSelectionModel().getSelectedItem());
			}
		});
		comboSattelites.getItems().addAll(SatteliteMods.values());
		Label immidiateLabel = new Label("Send Immidiate status chagne:");
		nodes[0] = immidiateLabel;
		nodes[1] = comboSattelites;
		nodes[2] = send;
		return nodes; 	
	}

	private Node[] getDataAcquisitionModeBox(){
		dataAquisitionModeBox = new ComboBox<>();
		Node[] nodes = new Node[3];
		Button send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				DataAcquisitionMode item = getMainWindow().dataAquisitionModeBox.getSelectionModel().getSelectedItem();
				if(item == null){
					return;
				}
				GuiManager.getInstance().sendImmidiateDataAquisitionCommand(getMainWindow().dataAquisitionModeBox.getSelectionModel().getSelectedItem());
			}
		});
		Label changeTypeOfDataRecieved = new Label("Change type of data recieved:");
		dataAquisitionModeBox.getItems().addAll(DataAcquisitionMode.values());
		nodes[0] = changeTypeOfDataRecieved;
		nodes[1] = dataAquisitionModeBox;
		nodes[2] = send;
		return nodes; 	
	}

	private  Node[] getComponentsStatusModeBox(){
		componentStatusBox = new ComboBox<>();
		Node[] nodes = new Node[3];
		buttonBox = new ComboBox<>();
		Button send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Component item = getMainWindow().componentStatusBox.getSelectionModel().getSelectedItem();
				if(item == null){
					return;
				}
				GuiManager.getInstance().sendImmidiateComponentStatusChange(getMainWindow().componentStatusBox.getSelectionModel().getSelectedItem().getCommand(buttonBox.getValue()));
			}
		});
		Label changeComponentStatus = new Label("Change component status:");
		buttonBox.getItems().addAll(State.values());
		componentStatusBox.getItems().addAll(Component.values());
		HBox box = new HBox();
		box.setSpacing(5);
		box.getChildren().addAll(componentStatusBox,buttonBox);
		nodes[0] = changeComponentStatus;
		nodes[1] = box;
		nodes[2] = send;
		return nodes; 	
	}

	public enum SatteliteMods{
		SAFE("Safe mode", Command.MOVE_TO_SAFE),
		OPERATION("Operation mode", Command.MOVE_TO_OP),
		STANDBYE("Standby mode", Command.MOVE_TO_STANDBY);

		private String presentationCommand;
		private Command command ;
		private SatteliteMods(String presentationCommand, Command command){
			this.presentationCommand = presentationCommand;
			this.command = command;
		}

		public Command getCommand(){
			return command;
		}
		@Override
		public String toString(){
			return presentationCommand;
		}
	}

	public enum DataAcquisitionMode{
		FORMAT_ENERGY("Send only Energy", Command.FORMAT_ENERGY),
		FORMAT_TEMP("Send only Temperature", Command.FORMAT_TEMP),
		FORMAT_STATIC("Send only Satellite Status", Command.FORMAT_STATIC),
		FORMAT_MIXED("Send mixed data", Command.FORMAT_MIXED);

		private String presentationCommand;
		private Command command ;
		private DataAcquisitionMode(String presentationCommand, Command command){
			this.presentationCommand = presentationCommand;
			this.command = command;
		}

		public Command getCommand(){
			return command;
		}
		@Override
		public String toString(){
			return presentationCommand;
		}


	}
	public enum State{
		ON("On"),
		OFF("Standby");

		private String presentationCommand;
		private State(String presentationCommand){
			this.presentationCommand = presentationCommand;

		}

		@Override
		public String toString(){
			return presentationCommand;
		}
	}
	public enum Component{
		SBAND("Sband"),
		PAYLOAD("Payload (Camera)"),
		THERMAL("Thermal");

		private String presentationCommand;
		private Component(String presentationCommand){
			this.presentationCommand = presentationCommand;

		}

		public Command getCommand(State state){
			switch (this) {
			case SBAND:
				return state == State.ON ? Command.SBAND_ON : Command.SBAND_STANDBY;
			case PAYLOAD:
				return state == State.ON ? Command.PAYLOAD_ON : Command.PAYLOAD_STANDBY;
			case THERMAL:
				return state == State.ON ? Command.THERMAL_CRTL_ON : Command.THERMAL_CRTL_STANDBY;
			default:
				break;
			}
			return null;//unrecognized command
		}
		@Override
		public String toString(){
			return presentationCommand;
		}
	}
	private String getSatteliteStatus() {
		String[] generatedStatus = {"Pass phase: Time remaining 3:20","Pass phase: In 2:30:45", "Connection Loss"};
		return generatedStatus[(int)(Math.random()*generatedStatus.length)];
	}
	public void setSatteliteStatusText(String status) {
		satteliteStatus.setText(status);
	}

	public void addToLog(String... data){
		if(data == null){
			return;
		}
		for(int i = 0 ; i < data.length ; i++){
			String toAdd = data[i];
			if(!toAdd.endsWith("\n")){
				toAdd = toAdd + "\n";
			}
			demoListView.appendText(toAdd);
		}

	}
	public SattelitePictureController getSatellitePictureController() {	

		return this.pictureController;
	}



}
