package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {
	
	protected static int numOfCruisers = 0;
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		numOfCruisers++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear, maximalSpeed, crewMembers, Arrays.asList(new Weapon("Laser Cannons",10,100)));
	}
	
	@Override
	public int getAnnualMaintananceCost() {
		int cost = super.getAnnualMaintananceCost();
		cost += numOfCruisers*50;
		return cost;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}