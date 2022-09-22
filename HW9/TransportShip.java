package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip extends mySpaceShip{

	protected int cargoCapacity;
	protected int passengerCapacity;
	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity)
	{
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
	}



	@Override
	public int getAnnualMaintananceCost() {
		int cost = 3000 + 5*cargoCapacity + 3*passengerCapacity;
		return cost;
	}
	
	public int getCargoCapacity() {
		return cargoCapacity;
	}



	public int getPassengerCapacity() {
		return passengerCapacity;
	}



	@Override
	public String toString() {
		return super.toString() + "\n" +
				 "\t" +"CargoCapacity=" + this.cargoCapacity + "\n"+
				 "\t" +"PassengerCapacity=" + this.passengerCapacity;
				
	}
	
	
}
