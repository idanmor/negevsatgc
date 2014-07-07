package orbit;

import java.util.Collection;

public interface OrbitPropagator {
	public boolean isPassPhase();
	public Pass getNextPass();
	public Collection<Pass> getPassesBetween(long start, long end);
}
