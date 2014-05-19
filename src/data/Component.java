package data;

import java.sql.Timestamp;

public interface Component {
	public static final int COMPONENT_STATE_OK = 0;
	public static final int COMPONENT_STATE_CRIT = 1;
	public static final int COMPONENT_STATE_OFF = 2;
	public static final int COMPONENT_STATE_STANDBY = 3;
	
	Timestamp getStateTimestamp();
	Timestamp getDataTimestamp();
	int getState();
	
}
