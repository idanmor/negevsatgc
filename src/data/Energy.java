package data;

import java.sql.Timestamp;

public class Energy implements Component {
	private int state;
	private Timestamp stateTimestamp;
	private Timestamp dataTimestamp;
	
	private float batt1Voltage;
	private float batt2Voltage;
	private float batt3Voltage;
	private float batt1Current;
	private float batt2Current;
	private float batt3Current;
	
	public Energy(int state, Timestamp stateTimestamp) {
		super();
		this.state = state;
		this.stateTimestamp = stateTimestamp;
		
		this.dataTimestamp = null;
		this.batt1Current = 0;
		this.batt1Voltage = 0;
		this.batt2Current = 0;
		this.batt2Voltage = 0;
		this.batt3Current = 0;
		this.batt3Voltage = 0;
	}
	
	
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
