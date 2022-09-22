package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myBattleShip {
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}
	
	@Override
	public int getAnnualMaintananceCost() {
		int cost = 2500;
		for (Weapon w : weapons) {
			cost += w.getAnnualMaintenanceCost();
		}
		cost += (int)(1000*maximalSpeed);
		return cost;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
