package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter  {

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}
	
	public int getAnnualMaintananceCost() {
		int cost = 4000;
		for (Weapon w : weapons) {
			cost+=w.getAnnualMaintenanceCost();
		}
		if(crewMembers != null)
			cost+=500*this.crewMembers.size();
		cost += 500*maximalSpeed;
		
		return cost;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
