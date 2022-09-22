package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends Fighter {

	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}

	public int getAnnualMaintananceCost() {
		int cost = 3500;
		for (Weapon w : weapons) {
			cost+=w.getAnnualMaintenanceCost();
		}
		if(crewMembers != null)
			cost+=500*this.crewMembers.size();
		cost += 1200*maximalSpeed;
		return cost;
	}
	@Override
	public String toString() {
		return super.toString();
	}

	
	

}
