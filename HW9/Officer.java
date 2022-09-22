package il.ac.tau.cs.sw1.ex9.starfleet;

public class Officer extends CrewWoman implements Comparable<Officer> {
		
	protected OfficerRank rank;
	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(age, yearsInService, name);
		this.rank = rank;
	}
	public OfficerRank getRank() {
		return rank;
	}
	@Override
	public int compareTo(Officer o) {
		return this.rank.compareTo(o.rank);
	}
}
