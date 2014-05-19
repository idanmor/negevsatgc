package data;

import java.sql.Timestamp;

public class Payload implements Component {
	private int state;
	private Timestamp stateTimestamp;
	private Timestamp dataTimestamp;
	
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
