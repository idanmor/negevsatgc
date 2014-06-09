package Panels;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;
import data.Component;
import data.DataManager;
import data.Temprature;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

public class TemperatureComponentStatistics extends AbstractComponentStatistics {

	public TemperatureComponentStatistics(BorderPane mainParentPane) {
		super(mainParentPane);
	}
	
	@Override
	protected void populateTableNodes(Timestamp oldestTS,
			ObservableList<StatisticDataItemInterface> nodes,
			DateFormat formatter, Timestamp toDate) {
		boolean hasData = false;
		List<Component> components = getComponent(oldestTS, toDate);
				//DataManager.getInstance().getTemprature(oldestTS, toDate);
		if(components.isEmpty()){
			return;
		}
		int numOfReadings = components.get(0).getSensorsValues().size();
		for(int i = 0 ; i < components.size() ; i++){
			String[][] data = new String[numOfReadings][3];
			
			hasData = true;
			Component t = components.get(i);
			data[i][0] = formatter.format(new Date(t.getSampleTimestamp().getTime())).toString();
			for(Pair<String, Float> sensor : t.getSensorsValues()){
				//data[i][0] = formatter.format(new Date(t.getSampleTimestamp().getTime())).toString();
				data[i][1] = sensor.getValue().toString();
				data[i][2] = sensor.getKey();
				nodes.add(new StatisticDataItem(formatter.format(new Date(oldestTS.getTime())).toString(),getObjectName(),sensor.getValue().toString() , data)); //TODO		
			}
		}    
//		if(hasData){
//			nodes.add(new StatisticDataItem(formatter.format(new Date(oldestTS.getTime())).toString(), "Temperature", "Sensor 1", data)); //TODO
//		}
	}

	@Override
	protected String getCsvFileLocationAndName() {
		 return "D:\\Temperature.csv";
	}

	@Override
	public List<Component> getComponent(Timestamp oldestTS, Timestamp TS) {
		return new ArrayList<>(DataManager.getInstance().getTemprature(oldestTS, TS));
	}

	@Override
	public String getObjectName() {
		return "Temperature";
	}
}
