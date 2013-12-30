/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MissionItems.MissionItemStrat;
import MissionItems.MissionItemTakeTemperature;
import MissionItems.MissionItemsTakePhoto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Max
 */
public class CommandScreen implements InternalScreenFactory{
 
    private Label labelMissionDate;
    
    private TextField textFieldDate;
  
    private Label LabelMissionClass;
    
    private Label summaryLabelDate;
   
    private Label titleLabel;
   
    private ComboBox<MissionItemStrat> ComboMissionClass;
   
    private Label MissionClass;
    
    private Label summaryMissionClass;
   private  SplitPane mainPane;
   private GridPane rightPane; 
   private  GridPane leftPane;
    @Override
    public void createScreenPane(BorderPane gridPane) {
        initialize(gridPane);
     }
    
    
    private void initialize(BorderPane gridPane){
        Scene s = gridPane.getScene();
        s.getStylesheets().add("Factories/GridPaneCss.css");
        mainPane  = new SplitPane();
        leftPane = createGridPane();
       
        titleLabel = new Label("Command Screen");
        summaryMissionClass = new Label("Not Specified");    
        rightPane = new GridPane();
        rightPane.getRowConstraints().add(new RowConstraints(20));
        ComboMissionClass = new ComboBox<>();
        ComboMissionClass.getItems().addAll(new MissionItemsTakePhoto(leftPane,rightPane), new MissionItemTakeTemperature(leftPane, rightPane));
        ComboMissionClass.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                 Object selectedItem = ComboMissionClass.getSelectionModel().getSelectedItem();
                if(selectedItem == null){
                    summaryMissionClass.setText("not specified" );
                     return;
                 }
                summaryMissionClass.setText(selectedItem.toString());
                 ((MissionItemStrat)selectedItem).execute();
                    }
                });
        
        Label view = new Label("Mission View");
        view.setAlignment(Pos.CENTER);
        leftPane.add(view, 3, 0);
        leftPane.add(ComboMissionClass, 2, 5);
         Label summaryView = new Label("Mission Summary View");
         summaryView.setAlignment(Pos.CENTER);
         final Pane  spring = new Pane();
        spring.minWidthProperty().bind(summaryView.widthProperty());
        rightPane.add(spring, 2, 0);
        rightPane.add(summaryView, 3, 0);
        
        mainPane.getItems().addAll(leftPane,rightPane);
        gridPane.setCenter(mainPane);
      
    }
    
      private GridPane createGridPane() {
       GridPane grid = new GridPane();             
        grid.setHgap(10);
        grid.setVgap(3);
        grid.getStyleClass().addAll("root","label");
         return grid;
    }
    
}
