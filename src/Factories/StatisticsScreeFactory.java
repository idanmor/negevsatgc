/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import StatisticsItems.StatisticsComboFiltersStrat;
import StatisticsItems.StatisticsTemperatureFilter;
import com.sai.javafx.calendar.FXCalendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Max
 */
public class StatisticsScreeFactory implements InternalScreenFactory{
    TableView<String> table;
    @Override
    public void createScreenPane(BorderPane gridPane) {
        initialize(gridPane);
    }
    
    private void initialize(BorderPane border){
        createTable();
        double leftGap = 60;
        double topGap = 10;
        int mult = 1;
        AnchorPane anchor = new AnchorPane();
        Label filterBy = new Label("Filter");
        ComboBox<StatisticsComboFiltersStrat> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll(new StatisticsTemperatureFilter());
        Label beforeDate = new Label("Before: ");
        FXCalendar before = new FXCalendar();   
        Label afterDate = new Label("After :");
        FXCalendar after = new FXCalendar();   
        Button filletButton = new Button("Filter");
        filletButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                filter(filterCombo.getSelectionModel().getSelectedItem().toString(), before.getTextField().getText(), after.getTextField().getText());
            }
        });
        
        AnchorPane.setTopAnchor(filterBy, topGap);
        AnchorPane.setLeftAnchor(filterBy, topGap);
        AnchorPane.setTopAnchor(filterCombo, topGap);
        AnchorPane.setLeftAnchor(filterCombo, leftGap*++mult);
        leftGap+=20;
        AnchorPane.setTopAnchor(beforeDate, topGap);       
        AnchorPane.setLeftAnchor(beforeDate, leftGap*++mult);
        AnchorPane.setTopAnchor(before, topGap);
        AnchorPane.setLeftAnchor(before, leftGap*++mult);
        leftGap+=20;
        AnchorPane.setTopAnchor(afterDate, topGap);
        AnchorPane.setLeftAnchor(afterDate, leftGap*++mult);
        AnchorPane.setTopAnchor(after, topGap);
        AnchorPane.setLeftAnchor(after, leftGap*++mult);
        AnchorPane.setTopAnchor(filletButton, topGap*4);
        AnchorPane.setLeftAnchor(filletButton,10.0);
        
        AnchorPane.setTopAnchor(table, topGap * 8);
        AnchorPane.setLeftAnchor(table, 10.0);
        
        anchor.getChildren().addAll(filterBy,filterCombo,beforeDate,before,afterDate,after,filletButton,table);
        table.setPrefWidth(anchor.getMaxWidth() - 20);
        
        border.setCenter(anchor);
    }
    
    
    private void filter(String type ,String before, String after){
        if(type == null || type.isEmpty()){
            return;
        }
         table.setItems(table.getItems().filtered(row -> row.equals(type)));
         GregorianCalendar beforeCal = createCalendar(before);
         GregorianCalendar afterCal = createCalendar(after);
         List items = table.getItems();
    }
    
    private GregorianCalendar createCalendar(String Date){
         String[] beforeSplitted = Date.split("/");
        return new GregorianCalendar(Integer.parseInt(beforeSplitted[2]), Integer.parseInt(beforeSplitted[1]), Integer.parseInt((beforeSplitted[0])));         
    }
    
    private void createTable(){
        table = new TableView<>();
        table.getColumns().addAll(new TableColumn<String,String>("Date"), new TableColumn<String,String>("Category"), new TableColumn<String, String>("Severity"));
        
    }
    
    
}
