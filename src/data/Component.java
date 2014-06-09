package data;

import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.util.Pair;

public abstract class Component {

	
	public abstract Timestamp getSampleTimestamp();
	public abstract Timestamp getReceivedTimestamp();
	public abstract ArrayList<Pair<String,Float>> getSensorsValues();
	
	
}
