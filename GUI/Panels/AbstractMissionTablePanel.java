package Panels;

import java.sql.Timestamp;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import data.DataManager;
import data.Mission;

public abstract class AbstractMissionTablePanel {
	protected BorderPane tablePane;
	protected TableView<TableNode> table;
	protected Button send;
	
	public AbstractMissionTablePanel(BorderPane mainPane) {
		initialize();
		mainPane.setCenter(tablePane);
	}
	private void initialize() {
		List<Mission> missions = getMissionsForLastMonth();
		tablePane = new BorderPane();

		HBox box = new HBox();
		box.setSpacing(400);
		Button deleteButton = new Button("Delete Mission");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				List<TableNode> items = table.getSelectionModel().getSelectedItems();
				for(TableNode item : items){
					DataManager.getInstance().deleteCompletedMission(item.getMission().getCreationTimestamp());
				}
				table.getItems().removeAll(items);
				table.getSelectionModel().clearSelection();
				

			}
		});
		send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				for(TableNode node : table.getSelectionModel().getSelectedItems()){
					//TODO
				}

			}
		});


		createTable();
		populateTable(missions);
		box.getChildren().addAll(send,deleteButton);
		tablePane.setCenter(table);
		tablePane.setBottom(box);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setAlignment(Pos.CENTER);
	}


	private void populateTable(List<Mission> missions) {
		if(missions == null){
			return;
		}
		ObservableList<TableNode> nodes = FXCollections.observableArrayList();
		for(Mission mission : missions){
			nodes.add(new TableNode(mission));
		}
		table.getItems().addAll(nodes);
	}

	private void createTable(){
		if(table == null){
			table = new TableView<>();
			table.setPrefWidth(600);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			table.getItems().addListener(new ListChangeListener<TableNode>(){

				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends TableNode> arg0) {
					//TODO - disable send button if the satellite is not in phase 
				}
			});
			createColumns();
			
		}		
	}
	
	// Abstract methods
	/**
	 * Creates the columns for the table
	 */
	public abstract void createColumns();
	/**
	 * Gets the missions that would be put to the table
	 * @return
	 */
	public abstract List<Mission> getMissionsForLastMonth();
	
	
	
	
	
	/**
	 * A class that is used by a TableView in order to get the data on a mission
	 * @author Max
	 *
	 */
	public class TableNode{
		private Mission mission;
		private String creationTime;
		private String executionTS;
		private String description;
		private String sentTime;
		
		public TableNode(Mission mission){
			this.creationTime = mission.getCreationTimestamp().toString();
			this.executionTS = mission.getExecutionTime().toString();
			this.description = mission.getDescription();
			this.sentTime = mission.getSentTime() == null ? "Not Sent" : mission.getSentTime().toString();
			this.mission = mission;
		}
		/**
		 * Gets the time that this mission was created
		 */
		public String getCreationTime() {
			return creationTime;
		}
		/**
		 * Gets the time that this mission will be executed
		 * @return
		 */
		public String getExecutionTS() {
			return executionTS;
		}
		/**
		 * Gets the description of the mission
		 * @return
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * Gets the mission in the node
		 * @return
		 */
		public Mission getMission(){
			return mission;
		}
		
		/**
		 * Checks if the mission was sent already
		 * @return true if the mission was sent
		 */
		public boolean getSent(){
			return getSentTime().isEmpty();
		}
		/**
		 * Gets the time this mission was sent
		 * @return TimeStamp of sent time
		 */
		public String getSentTime(){
			return sentTime;
		}
		
		
	}
}
