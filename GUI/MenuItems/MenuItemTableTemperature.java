package MenuItems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;




import Utils.Constants;
import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuItemTableTemperature extends MenuItemStrategy {
	 TableView<StatisticDataItemInterface> table;
	public MenuItemTableTemperature() {
		super("Components Temperature");
		createTable();
		
	}

	@Override
	public void applyActionOnGrid(BorderPane mainPane) {
		mainPane.setCenter(table);
		
	}
	  public void writeExcel() throws Exception {
	        Writer writer = null;
	        try {
	            File file = new File("C:\\Person.csv.");
	            writer = new BufferedWriter(new FileWriter(file));
	            for (StatisticDataItemInterface data : table.getItems()) {
	                String text = data.getDate() + "," + data.getComponent()  + "," + data.getSeverity() + "\n";
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
	            File file = new File("Person.csv");
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
	    
	    
	    @SuppressWarnings("unchecked")
		private void createTable(){
	        table = new TableView<>();
	        table.setPrefWidth(600);
	        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	        TableColumn date = new TableColumn("Date Taken");
	        TableColumn component = new TableColumn("Component");
	        TableColumn severity = new TableColumn("Severity");
	       
	        //table.getColumns().addAll(new TableColumn<String,String>("Date"), new TableColumn<String,String>("Category"), new TableColumn<String, String>("Severity"));
	       
	        date.setCellValueFactory(
	         new PropertyValueFactory<StatisticDataItemInterface,String>("date")
	        );
	        component.setCellValueFactory(
	            new PropertyValueFactory<StatisticDataItemInterface,String>("component")
	        );
	        severity.setCellValueFactory(
	            new PropertyValueFactory<StatisticDataItemInterface,String>("severity")
	        );
	        populateTableDemo();
	        //backUpListItems = table.getItems();
	         table.getColumns().addAll(date, component, severity);
	        
	    }
	    private void populateTableDemo(){
	        DemoPopulateTable d = new DemoPopulateTable();
	        table.setItems(d.getNodes());
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
	    private class DemoPopulateTable{
	    	private String[] components = new String[]{"Sensor1","Sensor2","Sensor3"};
	        private ObservableList<StatisticDataItemInterface> demoNodes;
	        private final int DATA_SIZE = 20;
	        
	        public DemoPopulateTable(){
	            demoNodes = FXCollections.observableArrayList();
	            StringBuilder date = new StringBuilder("1/5/2014");
	            for(int i = 1 ; i < 10; i++){
	                date.replace(0, 1, String.valueOf(i));
	                demoNodes.add((new StatisticDataItem(date.toString(),components[i % components.length], "Temperature",  generateRandomStatisticsData())));
	            }
	           // demoNodes.add((new StatisticDataItem(date.toString(), "Voltage", "Normal", generateRandomStatisticsData())));
	            
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
