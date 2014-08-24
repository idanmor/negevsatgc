package Panels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import data.Component;
import Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import misc.SatalliteUtils;
import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;


public abstract class AbstractComponentStatistics implements CommunicationRefreshInterface {
	TableView<StatisticDataItemInterface> table;
	private BorderPane rightPane;
	private BorderPane mainPane;
	private SplitPane split;
	LineChart<String,Number> lineChart;

	public AbstractComponentStatistics(BorderPane mainParentPane){			
		createTable();
		split = new SplitPane();
		split.setOrientation(Orientation.HORIZONTAL);
		rightPane = new BorderPane();
		mainPane = new BorderPane();
		HBox chartHbox = SatalliteUtils.getHBox(10);

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Day Time");

		lineChart = new LineChart<String,Number>(xAxis,yAxis);
		Button clearChart = new Button("Clear Chart");
		clearChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				lineChart.getData().clear();

			}
		});
		rightPane.setCenter(lineChart);
		ImageView excellImage = new ImageView(Utils.getImageViewFromLocation(this.getClass(),"excel.jpg"));
		Button exportToExcel = new Button("Export to excel",excellImage);
		exportToExcel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				try {
					//	writeExcelOneGraph(lineChart);
					writeExcelApache(lineChart);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		chartHbox.getChildren().addAll(clearChart, exportToExcel);
		rightPane.setTop(chartHbox);
		split.getItems().addAll(table,rightPane);
		mainPane.setCenter(split);
		mainPane.setTop(createTopHBox());
		mainParentPane.setCenter(this.mainPane);

	}
	protected HBox createTopHBox(){
		HBox box = SatalliteUtils.getHBox(10);
		Label beforeDate = new Label("End date: ");
		DatePicker before = new DatePicker(LocalDate.now());   
		Label afterDate = new Label("Start date :");
		DatePicker after = new DatePicker(LocalDate.now());  
		after.setUserData(Calendar.getInstance().getTimeInMillis());
		Button filterButton = new Button("Filter");
		filterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				filter(before.getEditor().getText(), after.getEditor().getText());
			}
		});
		box.getChildren().addAll(afterDate,after,beforeDate,before, filterButton);
		return box;
	}

	protected void populateTableNodes(Timestamp oldestTS,
			ObservableList<StatisticDataItemInterface> nodes,
			DateFormat formatter, Timestamp toDate) {
		boolean hasData = false;
		List<Component> components = getComponent(oldestTS, toDate);
		if(components.isEmpty()){
			return;
		}
		Set<String> sensors = components.get(0).getSetOfSensorsNames();
		for(String sensor : sensors){
			String[][] data = new String[components.size()][3];
			for(int i = 0 ; i < components.size() ; i++){
				hasData = true;
				Component comp = components.get(i);
				data[i][0] = formatter.format(new Date(comp.getSampleTimestamp().getTime())).toString();
				data[i][1] = comp.getSensorValue(sensor).toString();
				data[i][2] = sensor;
			}    
			if(hasData){
				nodes.add(new StatisticDataItem(formatter.format(new Date(oldestTS.getTime())).toString(), sensor, getObjectName(), data)); //TODO
			}
		}
	}
	private void filter(String before, String after){
		Timestamp beforeCal = null;
		Timestamp afterCal = null;
		before = before.replace("/", "-");
		after = after.replace("/", "-");
		long dayinMS = 86400000;
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy");
		if(before != null && !before.isEmpty()){

			try {
				Date beforeDate = writeFormat.parse(before);
				beforeCal = new Timestamp(beforeDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{

			beforeCal = new Timestamp(Calendar.getInstance().getTimeInMillis() + dayinMS);
		}
		if(after != null && !after.isEmpty()){
			Date afterDate;
			try {
				afterDate = writeFormat.parse(after);
				afterCal = new Timestamp(afterDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}else{
			long monthInMS = 26280000;//need to mult by 100
			afterCal = new Timestamp(Calendar.getInstance().getTimeInMillis() - monthInMS * 100);
		}

		ObservableList<StatisticDataItemInterface> filteredItems = FXCollections.observableArrayList();
		populateTableNodes(afterCal, filteredItems, new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss"), beforeCal);
		table.setItems(filteredItems);
	}
	public void writeExcel() throws Exception {
		Writer writer = null;
		try {
			File file = new File("C:\\Person.csv");
			writer = new BufferedWriter(new FileWriter(file));
			for (StatisticDataItemInterface data : table.getItems()) {
				String text = data.getDate() + "," + data.getComponent()  + "," + data.getType() + "\n";
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

	private void writeExcelApache(LineChart<String, Number> chart) throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Statistics");
		File file = new File(getCsvFileLocationAndName());
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		List<List<String>> data = getTableDataAsList();
		int numOfRows = 0;
		int columnNum = 0;
		for(List<String> newLine : data){
		
			for(String singleData : newLine){
				Row row = sheet.getRow(numOfRows);
				row = row == null ? sheet.createRow(numOfRows) : row;
				numOfRows++;

				Cell cell = row.createCell(columnNum);
				cell.setCellValue(singleData);
				sheet.autoSizeColumn(columnNum);
				
			}
			numOfRows = 0;
			columnNum++;
		}
		try {
			FileOutputStream out =
					new FileOutputStream(file);
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void writeExcelOneGraph(LineChart<String, Number> chart) throws Exception {
		Writer writer = null;
		try {
			File file = new File(getCsvFileLocationAndName());
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();


			writer = new BufferedWriter(new FileWriter(file));
			StringBuilder text = getTableDataStringBuilder();
			//     for (StatisticDataItemInterface data : table.getItems()) {
			//String text = data.getDate() + "," + data.getCategory()  + "," + data.getSeverity() + "\n";
			//    writer.write(text.toString());
			//   }
			writer.write(text.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {

			writer.flush();
			writer.close();
		} 
	}
	private StringBuilder getTableDataStringBuilder() {
		StringBuilder text = new StringBuilder();
		TableView.TableViewSelectionModel<StatisticDataItemInterface> selectionModel = table.getSelectionModel();
		for(int i = 0 ; i < selectionModel.getSelectedItems().size() ; i++){
			String[][] data = selectionModel.getSelectedItems().get(i).getData();
			for(int j = 0 ; j < data.length; j++){
				if(i == 0 && j == 0){
					for(int k = 0 ; k < data[j].length ; k++){

						text.append(table.getColumns().get(k).getText()).append(",");
					}
					text.append("\n");
				}
				//	                    	}
				for(int k = 0 ; k < data[j].length ; k++){

					text.append(data[j][k]).append(",");
				}
				text.append("\n");
			}

		}
		return text;
	}
	private List<List<String>> getTableDataAsList() {
		List<List<String>> text = new ArrayList<List<String>>();
		//StringBuilder text = new StringBuilder();
		TableView.TableViewSelectionModel<StatisticDataItemInterface> selectionModel = table.getSelectionModel();
		for(int i = 0 ; i < selectionModel.getSelectedItems().size() ; i++){
			String[][] data = selectionModel.getSelectedItems().get(i).getData();
			for(int j = 0 ; j < data.length; j++){
				if(i == 0 && j == 0){
					for(int k = 0 ; k < data[j].length ; k++){
						List<String> newColumn = new ArrayList<>();
						newColumn.add(table.getColumns().get(k).getText());
						text.add(newColumn);
						//text.append(table.getColumns().get(k).getText()).append(",");
					}
					//text.append("\n");
				}
				//	                    	}
				for(int k = 0 ; k < data[j].length ; k++){
					text.get(k).add(data[j][k]);
					//	text.append(data[j][k]).append(",");
				}
				//		text.append("\n");
			}

		}
		return text;
	}


	protected abstract String getCsvFileLocationAndName();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createTable(){
		if(table == null){
			table = new TableView<>();
			table.setPrefWidth(600);
			table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			TableColumn date = new TableColumn("Date Taken");
			TableColumn component = new TableColumn("Component");
			TableColumn type = new TableColumn("Type");
			date.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
			component.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
			type.prefWidthProperty().bind(table.widthProperty().multiply(0.25));

			date.setCellValueFactory(
					new PropertyValueFactory<StatisticDataItemInterface,String>("date")
					);
			component.setCellValueFactory(
					new PropertyValueFactory<StatisticDataItemInterface,String>("component")
					);
			type.setCellValueFactory(
					new PropertyValueFactory<StatisticDataItemInterface,String>("type")
					);
			//populateTableDemo();
			table.getColumns().addAll(date, component, type);
			table.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent t) {
					if(t.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null){
						createStatisicsWindow(lineChart);
					}
					t.consume();
				}
			});
		}
		populateTable();
		//backUpListItems = table.getItems();


		table.getItems();
	}
	private void populateTable(){
		final long monthInMS = 26280000;//need to mult by 100
		final long dayinMS = 86400000;
		DateFormat simpleFormatter = new SimpleDateFormat( "dd-MM-yyyy");

		Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
		Timestamp TS=new Timestamp(System.currentTimeMillis());
		ObservableList<StatisticDataItemInterface> nodes = FXCollections.observableArrayList();
		oldestTS = Utils.stripTimePortion(oldestTS);
		DateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		while(oldestTS.before(TS)){
			Timestamp toDate = new Timestamp(oldestTS.getTime() + dayinMS);
			populateTableNodes(oldestTS, nodes, formatter, toDate);
			oldestTS.setTime(oldestTS.getTime() + dayinMS);
		}
		table.getItems().addAll(nodes);
	}


	private void populateTableDemo(){
		DemoPopulateTable d = new DemoPopulateTable();
		table.setItems(d.getNodes());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createStatisicsWindow(LineChart<String,Number> lineChart){
		lineChart.getData().clear();
		lineChart.setTitle("Diagnostics");
		ObservableList<StatisticDataItemInterface> selected = table.getSelectionModel().getSelectedItems();
		for(int i = 0 ; i < selected.size(); i++){
			StatisticDataItemInterface selectedItem = selected.get(i);
			String[][] selectedItemData = selected.get(i).getData();
			XYChart.Series series = new XYChart.Series();
			series.setName(selectedItem.getDate());
			for(int j = 0 ; j < selectedItemData.length; j++){
				series.getData().add(new XYChart.Data(selectedItemData[j][0].substring(selectedItemData[j][0].indexOf(" ") + 1), Double.valueOf(selectedItemData[j][1])));          
			}
			lineChart.getData().add(series);
		}


	}

	//when no data avaliable
	private class DemoPopulateTable{
		private String[] components = new String[]{"Sensor1","Sensor2","Sensor3"};
		private ObservableList<StatisticDataItemInterface> demoNodes;
		private final int DATA_SIZE = 20;

		public DemoPopulateTable(){
			demoNodes = FXCollections.observableArrayList();
			StringBuilder date = new StringBuilder("1/5/2014");
			for(int i = 1 ; i < 10; i++){
				date.replace(0, 1, String.valueOf(i));
				demoNodes.add((new StatisticDataItem(date.toString(),components[i % components.length], "Temperature",  generateRandomStatisticsData(date.toString(),components[i % components.length]))));
			}
			// demoNodes.add((new StatisticDataItem(date.toString(), "Voltage", "Normal", generateRandomStatisticsData())));

		}

		public ObservableList<StatisticDataItemInterface> getNodes(){
			return demoNodes;
		}

		private Comparator<String> dateComperator =  new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				String firstString = o1.substring(o1.indexOf(" ") + 1);
				String secondString = o2.substring(o1.indexOf(" ") + 1);
				String[] split1 = firstString.split(":");
				String[] split2 = secondString.split(":");
				if (Integer.valueOf(split1[0]) > Integer.valueOf(split2[0])){
					return 1;
				}else if (Integer.valueOf(split1[0]) < Integer.valueOf(split2[0])){
					return -1;
				}else{
					return Integer.valueOf(split1[1]) > Integer.valueOf(split2[1]) ? 1 : -1; 
				}
			}
		};
		private String[][] generateRandomStatisticsData(String date, String component){
			String[][] data = new String[DATA_SIZE][3]; // x is the hour, y is the temp
			String[] hourForSort = new String[DATA_SIZE];
			String hour = null;
			String fTemp = null;
			StringBuilder hourBuilder = new StringBuilder();
			StringBuilder dateBuilder = new StringBuilder();
			int startTemp = 50;
			for(int i = 0; i < data.length ; i++){
				String minutes = ((int)(Math.random()*60)) + "";
				if(minutes.length() == 1){
					minutes = "0" + minutes;
				}
				hour = hourBuilder.append((int)(Math.random()*24)).append(":").append((int)(Math.random()*60)).toString();
				hourForSort[i] = date + " " + hour;
				hourBuilder.setLength(0);
			}
			Arrays.sort(hourForSort,dateComperator);
			for(int i = 0; i < hourForSort.length ; i++){
				data[i][0] = String.valueOf(hourForSort[i]);
				for(int j = 1 ; j < data[i].length -1 ; j++){
					int randomModifier = (int) (Math.random() * 10);
					int temp = startTemp + i * (randomModifier > 5 ? 1 : -1);
					fTemp = dateBuilder.append(temp).toString();
					dateBuilder.setLength(0);
					data[i][j] = fTemp;
				}
				data[i][data[i].length -1] = component;

			}


			return data;
		}
	}
	@Override
	public void refreshPanelData(){
		this.createTable();
	}
	public abstract List<Component> getComponent(Timestamp oldestTS, Timestamp TS);
	public abstract String getObjectName();
}
