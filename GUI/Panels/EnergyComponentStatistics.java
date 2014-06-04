package Panels;

import java.sql.Timestamp;
import java.text.DateFormat;

import misc.StatisticDataItemInterface;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class EnergyComponentStatistics extends AbstractComponentStatistics{

	public EnergyComponentStatistics(BorderPane mainParentPane) {
		super(mainParentPane);

	}

	@Override
	protected String getCsvFileLocationAndName() {
		 return "D:\\Voltage.csv";
	}

	@Override
	protected void populateTableNodes(Timestamp oldestTS,
			ObservableList<StatisticDataItemInterface> nodes,
			DateFormat formatter, Timestamp toDate) {
		//TODO
		
		
	}

}
