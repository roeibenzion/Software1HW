package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public abstract class myBattleShip extends mySpaceShip {

	protected List<Weapon> weapons;
	
	public myBattleShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.weapons = weapons;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}
	
	public int getFirePower()
	{
		int sum = 0;
		for (Weapon w : weapons){
			sum += w.getFirePower();
		}
		return sum + DEFAULT_FIREPOWER;
	}
	public abstract int getAnnualMaintananceCost();

	@Override
	public String toString() {
		return super.toString() + "\n" +
				"\t" +"WeaponArray=" + this.weapons.toString();
				
	}


	
	
}
