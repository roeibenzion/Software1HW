package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class mySpaceShip implements Spaceship {

	protected String name;
	protected int commissionYear;
	protected float maximalSpeed;
	protected Set<CrewMember> crewMembers;
	protected final int DEFAULT_FIREPOWER = 10;
	protected int fire = 0;
	
	public mySpaceShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers) {
		super();
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = new HashSet<CrewMember>();
		for (CrewMember member : crewMembers) {
			this.crewMembers.add(member);
		}
	}

	public String getName() {
		return name;
	}

	public int getCommissionYear() {
		return commissionYear;
	}

	public float getMaximalSpeed() {
		return maximalSpeed;
	}

	public Set<CrewMember> getCrewMembers() {
		return crewMembers;
	}

	@Override
	public int getFirePower() {
		return DEFAULT_FIREPOWER;
	}

	@Override
	public abstract int getAnnualMaintananceCost();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		myBattleShip other = (myBattleShip) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "\n" +
				"\t" + "Name=" + this.name + "\n"+
				"\t" + 	"CommissionYear=" + this.commissionYear + "\n" + 
				"\t" +"MaximalSpeed=" + this.maximalSpeed + "\n" +  
				"\t" +"FirePower=" + this.getFirePower() + "\n" + 
				"\t" +"CrewMembers=" + this.crewMembers.size() + "\n" + 
				"\t" +"AnnualMaintenanceCost=" + this.getAnnualMaintananceCost();
				
	}

}
