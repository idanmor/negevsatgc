package data;

public enum Command {

	MOVE_TO_SAFE(1),
	MOVE_TO_STANDBY(2),
	MOVE_TO_OP(3),
	FORMAT_ENERGY(4),
	FORMAT_TEMP(5),
	FORMAT_STATIC(6),
	FORMAT_MIXED(7),
	SBAND_ON(8),
	SBAND_STANDBY(9),
	PAYLOAD_ON(10),
	PAYLOAD_STANDBY(11),
	THERMAL_CRTL_ON(12),
	THERMAL_CRTL_STANDBY(13);
	
	private final int value;
	
	private Command(final int newValue) {
	        value = newValue;
	}

	public int getValue() { 
		return value; 
	}
	    
}
