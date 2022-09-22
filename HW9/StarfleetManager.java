package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		mySpaceShpiComparator<Spaceship> c = new mySpaceShpiComparator<Spaceship>();
		List<Spaceship> ret = new ArrayList<Spaceship>();
		Iterator<Spaceship> itr = fleet.iterator();
		while(itr.hasNext())
		{
			ret.add(itr.next());
		}
		Collections.sort(ret, c);
		List<String> names = new ArrayList<String>();
		for (Spaceship s : ret) {
			names.add(s.toString());
		}
		return names;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String, Integer> numberClass = new HashMap<String, Integer>();
		Iterator<Spaceship> itr = fleet.iterator();
		while(itr.hasNext())
		{
			Spaceship item = itr.next();
			String className = item.getClass().getSimpleName();
			if(numberClass.keySet().contains(className))
					numberClass.put(className, numberClass.get(className)+1);
			else
				numberClass.put(className, 1);
		}
		return numberClass;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship spaceship : fleet) {
			sum += spaceship.getAnnualMaintananceCost();
		}
		return sum;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> weapons = new HashSet<String>();
		for (Spaceship spaceship : fleet) {
			if(spaceship instanceof myBattleShip)
			{
				myBattleShip battleship = (myBattleShip)spaceship;
				List<Weapon> wp = battleship.getWeapons();
				for (Weapon weapon : wp) {
					weapons.add(weapon.getName());
				}
			}
		}
		return weapons;

	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship spaceship : fleet) {
			sum += spaceship.getCrewMembers().size();
		}
		return sum;
	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		float sum = 0, num = 0;
		for (Spaceship spaceship : fleet) {
			sum += getAverageAgeOfShipOfficers(spaceship.getCrewMembers());
			num += getNumOfShipOfficers(spaceship.getCrewMembers());
		}
		return sum/num;
	}

	private static float getAverageAgeOfShipOfficers(Set<? extends CrewMember> ship)
	{
		int sum = 0;
		for (CrewMember crewMember : ship) {
			if(crewMember instanceof Officer)
				sum += crewMember.getAge();
		}
		return sum;
	}
	
	private static float getNumOfShipOfficers(Set<? extends CrewMember> ship)
	{
		int num = 0;
		for (CrewMember crewMember : ship) {
			if(crewMember instanceof Officer)
				num++;
		}
		return num;
	}
	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer, Spaceship> ranks = new HashMap<Officer, Spaceship>();
		for (Spaceship spaceship : fleet) {
			Iterator<? extends CrewMember> itr = spaceship.getCrewMembers().iterator();
			Officer ranked = null;
			while(itr.hasNext())
			{
				CrewMember officer = itr.next();
				if((ranked == null && officer instanceof Officer))
				{
					ranked = (Officer)officer;
				}
				if(officer instanceof Officer && (ranked.compareTo((Officer)(officer)) == -1))
				{
					ranked = (Officer)officer;
				}
			}
			if(ranked != null)
				ranks.put(ranked, spaceship);
		}
		return ranks;

	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank, Integer> rankMap = new HashMap<OfficerRank, Integer>();
		for (Spaceship spaceship: fleet) {
			Iterator<? extends CrewMember> itr = spaceship.getCrewMembers().iterator();
			while(itr.hasNext())
			{
				CrewMember member = itr.next();
				if(member instanceof Officer)
				{
					Officer officer = (Officer)member;
					if(rankMap.containsKey(officer.getRank()))
					rankMap.put(officer.getRank(), rankMap.get(officer.getRank()) + 1);
					else
						rankMap.put(officer.getRank(), 1);
				}
			}
		}
		List<Map.Entry<OfficerRank, Integer>> lst = new ArrayList<Map.Entry<OfficerRank,Integer>>();
		for(Map.Entry<OfficerRank, Integer> entry : rankMap.entrySet())
		{
			lst.add(entry);
		}
		Collections.sort(lst, new myRankComparator());
		return lst;
	}

}
