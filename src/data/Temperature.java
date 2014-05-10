package data;

import java.sql.Timestamp;

public class Temperature implements Component {
	private int state;
	private Timestamp stateTimestamp;
	private Timestamp dataTimestamp;
	
	private float Sensor1;
	private float Sensor2;
	private float Sensor3;
	
	@Override
	public Timestamp getStateTimestamp() {
		return stateTimestamp;
	}

	@Override
	public Timestamp getDataTimestamp() {
		return dataTimestamp;
	}

	@Override
	public int getState() {
		return state;
	}
}
