/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MissionItems.MissionItemStrat;
import MissionItems.MissionItemsVisiblity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Max
 */
public class CommandScreenController implements Initializable {
    @FXML
    private Label labelMissionDate;
    @FXML
    private TextField textFieldDate;
    @FXML
    private Label LabelMissionClass;
    @FXML
    private Label summaryLabelDate;
    @FXML
    private Label titleLabel;
    @FXML
    private ComboBox<MissionItemStrat> ComboMissionClass;
    @FXML
    private Label MissionClass;
    @FXML
    private Label summaryMissionClass;
    @FXML
    private ImageView wordMapImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        ComboMissionClass.getItems().clear();
     ComboMissionClass.getItems().add(new MissionItemsVisiblity("Take photo", FXCollections.observableArrayList(wordMapImage)));
     textFieldDate.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
        @Override 
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789".contains(keyEvent.getCharacter())) {
                  keyEvent.consume();
                 }
            }
         });
    }    
     @FXML
    private void updateDateFinishLabel(KeyEvent event) {
        summaryLabelDate.setText(textFieldDate.getText());
       
    }

    @FXML
    private void comboClassSelectedAction(Event event) {
        Object selectedItem = ComboMissionClass.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
             summaryMissionClass.setText("not specified" );
            return;
        }
        summaryMissionClass.setText(selectedItem.toString());
        ((MissionItemStrat)selectedItem).execute();
    }

    @FXML
    private void updateLastCoardinate(MouseEvent event) {
        
    }

    @FXML
    private void resetCoardinates(MouseEvent event) {
    }

    @FXML
    private void updateCursor(MouseEvent event) {
       System.out.println("The x is = " + event.getX() + "The y is: " + event.getY());
      
    }

    @FXML
    private void updateFirstCoardinate(MouseEvent event) {
        
    }
    
    

}
