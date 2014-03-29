/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import webmap.Coardinate;
import webmap.WebMap;

/**
 *
 * @author Max
 */
public class MissionItemsTakePhoto extends MissionItemStrat
{

    private TableView table = new TableView();
    
    public MissionItemsTakePhoto(GridPane leftPane, GridPane rightPane){
        super("Take Photo",leftPane, rightPane);
        //debug
    }
    
    @Override
    public void execute() {
       
        //Table - webmap
        //schedule?
        //time?
        
       //right
       final Label label = new Label("Coardinates View");
       label.setFont(new Font("Arial", 20));
       createTable();
       rightPane.add(label, 2, 10);
       rightPane.add(table, 2, 15);
       
       addScheduleCalendar();
       leftPane.add(this.getFinishButton(), 2, 35);
    }
    
    
    private void createTable(){
        table = new TableView();
        
        table.setEditable(false);
        TableColumn lat = new TableColumn("Latitude");
        TableColumn lng = new TableColumn("Longtitude");
       // TableColumn Radius = new TableColumn("Radius");
        table.getColumns().addAll(lat, lng);
       
        Task<List<Coardinate>> task = new Task<List<Coardinate>>() {

            @Override
            protected List<Coardinate> call() throws Exception {
                WebMap w = new WebMap();
                w.start();
                return w.getMarkers();
             }
        };
       //  w.start();
         task.run();
         
        try {
            addToTable(task.get());
        } catch (InterruptedException ex) {
            Logger.getLogger(MissionItemsTakePhoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MissionItemsTakePhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addToTable(List <Coardinate> markers){
       for(int i = 0; i < markers.size() ; i++){
           Coardinate single = markers.get(i);
            table.getItems().addAll(single.getLat(),single.getLng(), new DeleteButton(i));
       }
    }
    
    
    public class DeleteButton extends Button{
        private int tableIndex;
        
        
        public DeleteButton(int index){
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
    
    //Define the button cell
    private class ButtonCell extends TableCell<Object, Boolean> {
        final Button cellButton = new Button("Action");
         
        ButtonCell(){
             
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
 
                @Override
                public void handle(ActionEvent t) {
                   
                }
            });
        }
 
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }
    
}
