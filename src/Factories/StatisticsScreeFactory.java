/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import StatisticsItems.StatisticsComboFiltersStrat;
import StatisticsItems.StatisticsTemperatureFilter;
import Utils.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import misc.SattaliteUtils;
import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;

/**
 *
 * @author Max
 */
public class StatisticsScreeFactory implements InternalScreenFactory{
    TableView<StatisticDataItemInterface> table;
    ObservableList<StatisticDataItemInterface> backUpListItems = null;
    @Override
    public void createScreenPane(BorderPane gridPane) {
        initialize(gridPane);
    }
    
    private void initialize(BorderPane border){
        createTable();
        VBox anchor = SattaliteUtils.getVbox();
        HBox filterBox = SattaliteUtils.getHBox();
        Label filterBy = new Label("Filter");
        ComboBox<StatisticsComboFiltersStrat> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll(new StatisticsTemperatureFilter());
        Label beforeDate = new Label("Before: ");
        DatePicker before = new DatePicker();   
        Label afterDate = new Label("After :");
        DatePicker after = new DatePicker();   
        Button filterButton = new Button("Filter");
        filterButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                StatisticsComboFiltersStrat item = filterCombo.getSelectionModel().getSelectedItem();
                filter(item == null ? null : item.toString(), before.getEditor().getText(), after.getEditor().getText());
            }
        });
        Button compare = new Button("Compare");
        compare.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                createCompareStatisticsWindows();
            }
        });
        Button smartCompare = new Button("Smart Compare");
     
        smartCompare.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                createCompareStatisticsWindowsYY();
            }
        });
        smartCompare.setDisable(true);
        filterBox.getChildren().addAll(filterBy,filterCombo,beforeDate,before,afterDate,after,filterButton);
      
        anchor.getChildren().addAll(filterBox,table,compare);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(table.getSelectionModel().getSelectedItems().size() != 2){
                    smartCompare.setDisable(true);
                }else{
                     smartCompare.setDisable(false);
                }
            }
        });
        border.setCenter(anchor);
    }
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            File file = new File("C:\\Person.csv.");
            writer = new BufferedWriter(new FileWriter(file));
            for (StatisticDataItemInterface data : table.getItems()) {
                String text = data.getDate() + "," + data.getCategory()  + "," + data.getSeverity() + "\n";
             writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        } 
    }
    
    public void writeExcelOneGraph(BarChart chart) throws Exception {
        Writer writer = null;
        try {
            File file = new File("Person.csv.");
            StringBuilder text = new StringBuilder();
             writer = new BufferedWriter(new FileWriter(file));
            TableView.TableViewSelectionModel<StatisticDataItemInterface> selectionModel = table.getSelectionModel();
            for(int i = 0 ; i < selectionModel.getSelectedItems().size() ; i++){
              String[][] data = selectionModel.getSelectedItems().get(i).getData();
                for(int j = 0 ; j < data.length; j++){
                    //writer.write(data[j][0]);
                    for(int k = 0 ; k < data[j].length ; k++){
                        text.append(data[j][k]).append(",");
                    }
                    text.append("\n");
                }
              
            }
            for (StatisticDataItemInterface data : table.getItems()) {
                //String text = data.getDate() + "," + data.getCategory()  + "," + data.getSeverity() + "\n";
         //    writer.write(text.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        } 
    }
    
    private void createCompareStatisticsWindows(){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Day Time");
        final BarChart<String,Number> lineChart = 
                new BarChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Diagnostics comperator");
        ObservableList<StatisticDataItemInterface> selected = table.getSelectionModel().getSelectedItems();
        for(int i = 0 ; i < selected.size(); i++){
            StatisticDataItemInterface selectedItem = selected.get(i);
            String[][] selectedItemData = selected.get(i).getData();
            XYChart.Series series = new XYChart.Series();
            series.setName(selectedItem.getDate());
            for(int j = 0 ; j < selectedItemData.length; j++){
                 series.getData().add(new XYChart.Data(selectedItemData[j][0], Double.valueOf(selectedItemData[j][1])));          
            }
              lineChart.getData().add(series);
        }
        Stage s = new Stage();
        Scene scene = new Scene(lineChart, 1000, 600);
        scene.getStylesheets().add(Constants.CSS_CHART);
        s.setScene(scene);
        s.show();
        
        
    }
    
     private void createCompareStatisticsWindowsYY(){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Day Time");
        final BarChart<String,Number> lineChart = 
                new BarChart<>(xAxis,yAxis);
        lineChart.setTitle("Diagnostics comperator");
        ObservableList<StatisticDataItemInterface> selected = table.getSelectionModel().getSelectedItems();
     //   for(int i = 0 ; i < 2; i++){
            StatisticDataItemInterface firstSelectedItem = selected.get(0);
             StatisticDataItemInterface secondSelectedItem = selected.get(1);
//            XYChart.Series series = new XYChart.Series();
//            series.setName(firstSelectedItem.getDate());
//            for(int j = 0 ; j < firstSelectedItemData.length; j++){
//                 series.getData().add(new XYChart.Data(firstSelectedItemData[j][0], Double.valueOf(firstSelectedItemData[j][1])));          
//            }
            
              lineChart.getData().add(getSeriesFromData(firstSelectedItem,secondSelectedItem));
        try {
            //lineChart.getData().add(getSeriesFromData(secondSelectedItem));
            //   }
            writeExcelOneGraph(lineChart);
        } catch (Exception ex) {
            Logger.getLogger(StatisticsScreeFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Image excellImage = new Image(getClass().getResourceAsStream("excel.jpg"));
     
        Button exportToExcel = new Button("Export to excel",new ImageView(excellImage));
        exportToExcel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                
            }
        });
        Stage s = new Stage();
        Scene scene = new Scene(lineChart, 800, 800);
        scene.getStylesheets().add(Constants.CSS_CHART);
        s.setScene(scene);
        s.show();
        
        
    }
     private XYChart.Series getSeriesFromData(StatisticDataItemInterface firstSelectedItem, StatisticDataItemInterface secondSelectedItem){
         String[][] firstSelectedItemData = firstSelectedItem.getData();
            String[][] secondSelectedItemData = secondSelectedItem.getData();
            XYChart.Series series = new XYChart.Series();
            series.setName(firstSelectedItem.getDate());
            for(int j = 0 ; j < Math.min(firstSelectedItemData.length, secondSelectedItemData.length); j++){
                 series.getData().add(new XYChart.Data(firstSelectedItemData[j][1], Double.valueOf(secondSelectedItemData[j][1])));          
            }
            return series;
     }
    
    
    private void filter(String type ,String before, String after){
        GregorianCalendar beforeCal = null;
         GregorianCalendar afterCal = null;
        // table.setItems(table.getItems().filtered(row -> row.equals(type)));
         if(before != null && !before.isEmpty()){
            beforeCal = createCalendar(before);
          
         }
         if(after != null && !after.isEmpty()){
            afterCal = createCalendar(after);
          
         }
           
         ObservableList<StatisticDataItemInterface> filteredItems = FXCollections.observableArrayList();
         List<StatisticDataItemInterface> items = backUpListItems;
         for(StatisticDataItemInterface item: items){
            boolean valid = true;
            if(type != null && !type.isEmpty() && !type.equals(item.getCategory())){
                valid = false;
            }else
            if(beforeCal != null && beforeCal.compareTo(createCalendar(item.getDate())) < 0){
                 valid = false;
             }else if (afterCal != null && afterCal.compareTo(createCalendar(item.getDate())) > 0){
                 valid = false;
             }
             if(valid){
                 filteredItems.add(item);
             }
             
             
         }
         table.setItems(filteredItems);
    }
    
    private GregorianCalendar createCalendar(String Date){
        String[] beforeSplitted = Date.split("/");
        return new GregorianCalendar(Integer.parseInt(beforeSplitted[2]), Integer.parseInt(beforeSplitted[1]), Integer.parseInt((beforeSplitted[0])));         
    }
    
    private void createTable(){
        table = new TableView<>();
        table.setPrefWidth(600);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn date = new TableColumn("Date");
        TableColumn category = new TableColumn("Category");
        TableColumn severity = new TableColumn("Severity");
       
        //table.getColumns().addAll(new TableColumn<String,String>("Date"), new TableColumn<String,String>("Category"), new TableColumn<String, String>("Severity"));
       
        date.setCellValueFactory(
         new PropertyValueFactory<StatisticDataItemInterface,String>("date")
        );
        category.setCellValueFactory(
            new PropertyValueFactory<StatisticDataItemInterface,String>("category")
        );
        severity.setCellValueFactory(
            new PropertyValueFactory<StatisticDataItemInterface,String>("severity")
        );
        populateTableDemo();
        backUpListItems = table.getItems();
         table.getColumns().addAll(date, category, severity);
        
    }
    
    private void populateTableDemo(){
        DemoPopulateTable d = new DemoPopulateTable();
        table.setItems(d.getNodes());
    }
    
    
    private class DemoPopulateTable{
        private ObservableList<StatisticDataItemInterface> demoNodes;
        private final int DATA_SIZE = 20;
        
        public DemoPopulateTable(){
            demoNodes = FXCollections.observableArrayList();
            StringBuilder date = new StringBuilder("1/1/2014");
            for(int i = 1 ; i < 6; i++){
                date.replace(0, 1, String.valueOf(i));
                demoNodes.add((new StatisticDataItem(date.toString(), "Temperature", "Normal", generateRandomStatisticsData())));
            }
            demoNodes.add((new StatisticDataItem(date.toString(), "Voltage", "Normal", generateRandomStatisticsData())));
            
        }
        
        public ObservableList<StatisticDataItemInterface> getNodes(){
            return demoNodes;
        }
        
        private Comparator<String> dateComperator =  new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split(":");
                    String[] split2 = o2.split(":");
                    if (Integer.valueOf(split1[0]) > Integer.valueOf(split2[0])){
                        return 1;
                    }else if (Integer.valueOf(split1[0]) < Integer.valueOf(split2[0])){
                        return -1;
                    }else{
                        return Integer.valueOf(split1[1]) > Integer.valueOf(split2[1]) ? 1 : -1; 
                    }
                 }
            };
        private String[][] generateRandomStatisticsData(){
            String[][] data = new String[DATA_SIZE][2]; // x is the hour, y is the temp
            Integer[] hourForSort = new Integer[DATA_SIZE];
            String hour = null;
            String fTemp = null;
            StringBuilder hourBuilder = new StringBuilder();
            StringBuilder dateBuilder = new StringBuilder();
            for(int i = 0; i < data.length ; i++){
               // hour = hourBuilder.append((int)(Math.random()*24)).append(":").append((int)(Math.random()*60)).toString();
                hour = hourBuilder.append(i + DATA_SIZE).toString();
                hourForSort[i] = Integer.valueOf(hour);
                hourBuilder.setLength(0);
            }
            Arrays.sort(hourForSort);
            for(int i = 0; i < hourForSort.length ; i++){
                data[i][0] = String.valueOf(hourForSort[i]);
                for(int j = 1 ; j < data[i].length ; j++){
                    fTemp = dateBuilder.append(Math.random() * 300).toString();
                    dateBuilder.setLength(0);
                    data[i][j] = fTemp;
                }
               
            }
            
            
            return data;
        }
    }
    
}
