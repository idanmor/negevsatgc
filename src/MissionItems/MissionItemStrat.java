/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import com.sai.javafx.calendar.FXCalendar;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Max
 */
public abstract class MissionItemStrat {
    String name;
    List<Node> list;
    GridPane leftPane;
    GridPane rightPane;
    
    public MissionItemStrat(String name, List<Node> list){
        this.name = name;
        this.list = list;
        
    }
    public MissionItemStrat(String name, GridPane leftPane, GridPane rightPane){
        this.name = name; 
        this.leftPane = leftPane;
        this.rightPane = rightPane;
    }
    @Override
    public String toString(){
        return name;
    }
    public abstract void execute();
  
    protected void addScheduleCalendar(){
        Label sumSchedule = new Label("Scheduled: No");
       Label scheduledDate = new Label("Undefined");
       
       rightPane.add(sumSchedule, 2, 30);
       rightPane.add(scheduledDate,3,30);
        //left       
        FXCalendar calendar = new FXCalendar();
        CheckBox schedule = new CheckBox("Add to Schedule");
        calendar.setVisible(false);
        schedule.selectedProperty().addListener(new ChangeListener<Boolean>() {

           @Override
           public void changed(ObservableValue<? extends Boolean> ov, Boolean old, Boolean newVal) {
             //   boolean isPressed = schedule.isPressed();
                calendar.setVisible(newVal);
                if(!newVal){
                    scheduledDate.setText("Undefined");
                    calendar.getTextField().clear();
                }
                 sumSchedule.setText("Scheduled: " + newVal);
                   
           }
            
        });
      
        calendar.getTextField().textProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                scheduledDate.setText(calendar.getTextField().getText());
           }
       }
        );
        
        leftPane.add(schedule, 2, 15);
        leftPane.add(calendar, 2, 30);
    }
    
    protected Button getFinishButton(){
        Button done = new Button("Execute");
        done.setId("finish_button");
        done.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        return done;
    }
}
