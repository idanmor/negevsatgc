package orbit;

public class OrbitManager {
	public static final String tle = 
			"ISS (ZARYA)\n"
			+ "1 25544U 98067A   08264.51782528 -.00002182  00000-0 -11606-4 0  2927\n"
			+ "2 25544  51.6416 247.4627 0006703 130.5360 325.0288 15.72125391563537";

	private static OrbitManager instance = null;
	
	private Pass nextPass;
	private OrbitPropagator propagator;
	
	private OrbitManager() {
		this.propagator = new SimOrbitPropagator();
		this.nextPass = this.propagator.getNextPass();
	}
	
	public static OrbitManager getInstance() {
		if (instance == null)
			instance = new OrbitManager();
		return instance;
	}
	
	private Pass getNextPass() {
		if (nextPass.isOutOfDate()) {
			nextPass = propagator.getNextPass();
		}
		return nextPass;
	}
	
	public boolean isPassPhase() {
		return this.getNextPass().isInPassPhase();
	}
	
	/**
	 * Get the time remaining to the Pass
	 * @return milliseconds to this pass from now, 0 if the Pass already started
	 */
	public long timeToPassStart() {
		return this.getNextPass().getTimeToPassStart();
	}
	
	/**
	 * Get the time remaining to the Pass end
	 * @return milliseconds to this pass from now, 0 if not in this pass phase
	 */
	public long timeToPassEnd() {
		return this.getNextPass().getTimeToPassEnd();
	}
}
