package Panels;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;
import data.DataManager;
import data.Temprature;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class TemperatureComponentStatistics extends AbstractComponentStatistics {

	public TemperatureComponentStatistics(BorderPane mainParentPane) {
		super(mainParentPane);
	}
	
	@Override
	protected void populateTableNodes(Timestamp oldestTS,
			ObservableList<StatisticDataItemInterface> nodes,
			DateFormat formatter, Timestamp toDate) {
		boolean hasData = false;
		List<Temprature> temp = DataManager.getInstance().getTemprature(oldestTS, toDate);
		String[][] data = new String[temp.size()][3];
		for(int i = 0 ; i < temp.size() ; i++){
			hasData = true;
			Temprature t = temp.get(i);
			data[i][0] = formatter.format(new Date(t.getSampleTimestamp().getTime())).toString();
			data[i][1] = t.getSensor1() + ""; //for now only 1
			data[i][2] = "Sensor 1";
		}    
		if(hasData){
			nodes.add(new StatisticDataItem(formatter.format(new Date(oldestTS.getTime())).toString(), "Temperature", "Sensor 1", data)); //TODO
		}
	}

	@Override
	protected String getCsvFileLocationAndName() {
		 return "D:\\Temperature.csv";
	}
}
