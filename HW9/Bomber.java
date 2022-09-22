package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myBattleShip {

	private int numberOfTechnicians;
	
	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
			List<Weapon> weapons, int numberOfTechnicians) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		this.numberOfTechnicians = numberOfTechnicians;
	}
	
	public int getNumberOfTechnicians() {
		return numberOfTechnicians;
	}
	@Override
	public int getAnnualMaintananceCost() {
		int cost = 5000;
		float wCost = 0;
		for (Weapon w : weapons) {
			wCost += w.getAnnualMaintenanceCost();
		}
		wCost -= (float) ((wCost)*numberOfTechnicians)/(10);
		cost += (int)wCost;
		return cost;
	}

	@Override
	public String toString() {
		return  super.toString() + "\n"
				+"\t" +"NumberOfTechnicians=" + this.numberOfTechnicians;
	}
	
	
}
