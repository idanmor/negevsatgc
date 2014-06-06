package data;

import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.util.Pair;

public abstract class Component {

	
	abstract Timestamp getSampleTimestamp();
	abstract Timestamp getReceivedTimestamp();
	abstract ArrayList<Pair<String,Float>> getSensorsValues();
	
	
}
