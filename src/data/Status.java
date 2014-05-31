package data;

public enum Status {
	ON(1),
	MALFUNCTION(2),
	STANDBY(3), 
	NON_OPERATIONAL(4);

	private final int value;
	
	private Status(final int newValue) {
	        value = newValue;
	}

	public int getValue() { 
		return value; 
	}

}
