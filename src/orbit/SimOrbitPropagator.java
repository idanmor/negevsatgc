package orbit;

import java.util.Collection;
import java.util.Date;

public class SimOrbitPropagator implements OrbitPropagator {
	public static final int ALWAYS_PASS = 0;
	public static final int MANUAL_PASS = 1;
	
	private int mode;
	
	public SimOrbitPropagator () {
		this.mode = SimOrbitPropagator.ALWAYS_PASS;
	}
	
	public SimOrbitPropagator (int mode) {
		this.mode = mode;
	}
	
	@Override
	public Pass getNextPass() {
		if (this.mode == SimOrbitPropagator.ALWAYS_PASS) {
			Date now = new Date();
			return new Pass (now, new Date(now.getTime() + 240000));
		}
		else if (this.mode == SimOrbitPropagator.MANUAL_PASS){
			return null;
		}
		else
			return null;
	}

	@Override
	public Collection<Pass> getPassesBetween(long start, long end) {
		return null;
	}

}
