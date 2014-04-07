/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionFrames;

import MenuItems.MissionComboBoxWrapper;
import MenuItems.MissionDatePickerWrapper;
import MenuItems.MissionItemWrapper;
import MenuItems.MissionTableWrapper;
import MenuItems.MissionTextWrapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import webmap.Coardinate;
import webmap.WebMap;

/**
 *
 * @author Max
 */
public class MissionSplitFrameIMPL implements MissionSplitFrameInterface{
    private BorderPane mainPane;
    private SplitPane mainSplitPane;
    private TreeView<MissionTreeItem> leftTree;
    private VBox rightPane;
    private Button confirmButton;
    private Button cancelButton;
    
    private List<String> mission_components_list;
    
    private final int MISSION_DESC = 0;
    private final int MISSION_DATE = 1;
    private final int MISSION_DATE_END = 2;
    private final int MISSION_DURATION = 3;

    private final int COMPONENT_ON_OFF_LOCATION = 4;
        private final int MISSION_LOCATION_TABLE = 5;
    private final int MISSION_MAX_NUM_ITEMS = 6;
    public MissionSplitFrameIMPL(BorderPane pane){
        mainSplitPane = new SplitPane();
        mainPane = new BorderPane();
     //   mainPane = new VBox();
        init();
        pane.setCenter(mainPane);
        //pane.setBottom(getConfirmCancelButtons());
    }
    
    
    
    private void init(){
        populateLeftList();
        mainSplitPane.setOrientation(Orientation.HORIZONTAL);
        rightPane = new VBox();
        mainSplitPane.getItems().addAll(leftTree,rightPane);
      //  mainPane.getChildren().addAll(mainSplitPane/*,getConfirmCancelButtons()*/);
        mainPane.setCenter(mainSplitPane);
        mainPane.setBottom(getConfirmCancelButtons());
        mission_components_list = new ArrayList<>(MISSION_MAX_NUM_ITEMS);
        for(int i = 0 ; i < MISSION_MAX_NUM_ITEMS ; i++){
            mission_components_list.add(null);
            rightPane.getChildren().add(new HBox());
        }
    }
            
    private HBox getConfirmCancelButtons(){
        HBox box = new HBox();
        box.setPadding(new Insets(0, 0, 0, 200));
        box.setSpacing(200);
        box.getChildren().addAll(getConfirmButton(),getClearButton());
        box.setMaxHeight(0);
        return box;
    }
    
    protected Button getConfirmButton(){
        if(confirmButton == null){
            confirmButton = new Button("Confirm Mission");
            confirmButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    //TODO
                    
                }
            });
        } 
        return confirmButton;
        
    }
    
    private int getMaxLabelWidth(){
        return "Mission description:".length();      
    }
    
    public Button getClearButton(){
        if(cancelButton == null){
            cancelButton = new Button("Cancel");
            cancelButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                  rightPane.getChildren().clear();
                }
            });
        }
        return cancelButton;
    }
    @Override
    public void populateLeftList() {
        if(leftTree == null){
            TreeItem root = new TreeItem(new MissionTreeItem("Build mission"));
            root.setExpanded(true);
            leftTree = new TreeView<>(root);
             leftTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  
             leftTree.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent mouseEvent)
                    {            
                        if(mouseEvent.getClickCount() == 2)
                        {
                            MissionTreeItem item =  (MissionTreeItem) leftTree.getSelectionModel().getSelectedItem().getValue();
                            if(item == null || item.getMissionItem() == null){ //nothing is selected or there is no actual input item
                                mouseEvent.consume();
                                return;
                            }
                            BorderPane box = new BorderPane();
                            Button deletionButton = new Button("-");
                            deletionButton.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent t) {
                                    rightPane.getChildren().remove(box);
                                    item.setAlreadySelected(false);
                                }
                            });
                            box.setPadding(new Insets(10, 10, 10, 10));
                            Label expLabel = item.getExplainLabel();
                            
                            if(expLabel != null){
                                    box.setLeft(item.getExplainLabel());
                                    box.setRight(new HBox(item.getMissionItem().getWrappedItem(),deletionButton));
                                   
                                     System.out.println(item.getExplainLabel().getText().length());
                            }else{
                                 box.getChildren().addAll(item.getMissionItem().getWrappedItem(),deletionButton); 
                            }
                            
                            item.setAlreadySelected(true);
                            rightPane.getChildren().add(item.getMissionArrayLoc(),box);
                            mission_components_list.add(item.getMissionArrayLoc(), item.getMissionStringValue());
                        }
                    }
                });
             TreeItem dateAndLocation = new TreeItem(new MissionTreeItem("Date and Location Items"));
             dateAndLocation.getChildren().addAll(getDateAndLocationItems());
             TreeItem missionGeneral =  new TreeItem(new MissionTreeItem("General mission items"));
             missionGeneral.getChildren().addAll(getGeneralMissionDescription());
             TreeItem missionComponentsAction = new TreeItem(new MissionTreeItem("Components Actions"));
             missionComponentsAction.getChildren().addAll(getSatteliteComponentCommands());
             root.getChildren().addAll(dateAndLocation, missionGeneral, missionComponentsAction);
             
        }
    }
    /**
     * The function returns a list of mission that is related to dates, locations, time ext...
     * @return list of mission items 
     */
    protected ObservableList<TreeItem> getDateAndLocationItems(){
         ObservableList<TreeItem> list = FXCollections.observableArrayList();
         MissionDatePickerWrapper picker = new MissionDatePickerWrapper(LocalDate.now());
         picker.setEditable(false);
         list.add(new TreeItem (new MissionTreeItem(picker, "Mission Date", new Label("Date:"), MISSION_DATE)));
         list.add(new TreeItem(new MissionTreeItem(new MissionDatePickerWrapper(LocalDate.now()), "Mission end time", new Label("End mission:"), MISSION_DATE_END)));
         list.add(new TreeItem(new MissionTreeItem(new MissionTextWrapper("Type duration here"), "Duration", new Label("Mission Duration(hours):"), MISSION_DURATION)));
         list.add(new TreeItem (getWebMapMissionItem()));
         return list;
   }
     /**
     * The function returns a list of missions that describe the mission for example: Take photo, measure temperature....
     * @return list of mission items 
     */
    protected ObservableList<TreeItem> getGeneralMissionDescription(){
         ObservableList<TreeItem> list = FXCollections.observableArrayList();
         MissionTextWrapper nonEditableField = new MissionTextWrapper("Take photo");
         nonEditableField.setEditable(false);
         list.add(new TreeItem (new MissionTreeItem(nonEditableField, "Take photo mission", new Label("Mission description:"),MISSION_DESC)));
         list.add(new TreeItem (new MissionTreeItem(new MissionComboBoxWrapper(getListOfSatteliteComponents()), "Manage component mission", new Label("Choosen Component:"),MISSION_DESC)));
         list.add(new TreeItem (new MissionTreeItem(new MissionTextWrapper("Type Text Here"), "Custom mission", new Label("Mission description:"), MISSION_DESC)));
         return list;
    }
    
      protected ObservableList<TreeItem> getSatteliteComponentCommands(){
          ObservableList<TreeItem> list = FXCollections.observableArrayList();
         ObservableList<Object> mode = FXCollections.observableArrayList("Online","Offline");
          list.add(new TreeItem (new MissionTreeItem(new MissionComboBoxWrapper(mode), "Change Component Status", new Label("Mode:"),COMPONENT_ON_OFF_LOCATION)));      
          return list;
      } 
      private ObservableList<Object> getListOfSatteliteComponents(){
          return FXCollections.observableArrayList("Computer","Temperature sensor", "Battery","Camera");
          
      }
    private MissionTreeItem getWebMapMissionItem(){
        MissionTreeItem item = new MissionTreeItem(generateLocationTable(), "SelectLocation", null, MISSION_LOCATION_TABLE){
           public void doAdditionalActionsOnSelection(){
               WebMap map = new WebMap();
               map.start();
           }
        };
        return item;                    
    }

    @Override
    public String buildMissionSummary() {
        return "unsupported";
    }
    
     private MissionTableWrapper generateLocationTable(){
         MissionTableWrapper table = new MissionTableWrapper();
        
        table.setEditable(false);
        TableColumn lat = new TableColumn("Latitude");
        TableColumn lng = new TableColumn("Longtitude");
       // TableColumn Radius = new TableColumn("Radius");
        table.getColumns().addAll(lat, lng);
       
//        Task<List<Coardinate>> task = new Task<List<Coardinate>>() {
//
//            @Override
//            protected List<Coardinate> call() throws Exception {
//                WebMap w = new WebMap();
//                w.start();
//                return w.getMarkers();
//             }
//        };
//       //  w.start();
//         task.run();
//         
//        try {
//            addToTable(task.get(),table);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(MissionItemsTakePhoto.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(MissionItemsTakePhoto.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return table;
    }
    
    private void addToTable(List <Coardinate> markers, TableView table){
       for(int i = 0; i < markers.size() ; i++){
           Coardinate single = markers.get(i);
            table.getItems().addAll(single.getLat(),single.getLng(), new DeleteButton(i, table));
       }
    }
    
    private class MissionTreeItem /*extends TreeItem<MissionTreeItem>*/{
        private MissionItemWrapper item = null;
        private String name = null;
        private Label explainLabel = null;
        private boolean isInSummary;
        private int missionArrayLocation = -1;
        
        public MissionTreeItem(MissionItemWrapper item, String name, Label explainLabel, int missionArrayLocation){
            this.item = item;
            this.name = name;
            this.explainLabel = explainLabel;
            if(explainLabel != null){
                this.explainLabel.setText(explainLabel.getText().trim() + getFillerString());
              
            }
              
            isInSummary = false;
            this.missionArrayLocation = missionArrayLocation;
        }
        
        private String getFillerString(){
           StringBuilder b = new StringBuilder();
            for(int i = 0 ; i < Math.abs(getMaxLabelWidth() - explainLabel.getText().trim().length()); i++){
                b.append(" ");
            }
          
            return b.toString();
        }
        
        public MissionTreeItem(String name){
            this.name = name;
        }
        
        @Override
        public String toString(){
            return name;
        }
        
        public MissionItemWrapper getMissionItem(){
            return item;
        }
        public int getMissionArrayLoc(){
            return missionArrayLocation;
        }
        public Label getExplainLabel(){
            return explainLabel;
        }
        
        public void doAdditionalActionsOnSelection(){
            //if any additional actions needed override this function
        }
        public void setAlreadySelected(boolean isInSummary){
            this.isInSummary = isInSummary;
        }
        
        public boolean isAlreadyInSummary(){
            return isInSummary;
        }
        
        public String getMissionStringValue(){
           return item.getValueStringWithPreIfNeeded(explainLabel);
        }
    }
    
    public class TemporaryMission{
        
    };
     public class DeleteButton extends Button{
        private int tableIndex;
        
        
        public DeleteButton(int index, TableView table){
         index = tableIndex;
         this.setOnAction(new EventHandler<ActionEvent>(){
              @Override
              public void handle(ActionEvent t) {
                    table.getItems().remove(tableIndex);
                }
            });
         }
        
        
        @Override
        public String toString(){   
            return "Delete row";
        }
    }
}
