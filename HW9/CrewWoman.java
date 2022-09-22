package il.ac.tau.cs.sw1.ex9.starfleet;

public class CrewWoman implements CrewMember {

	protected String name;
	protected int yearsInService;
	protected int age;
	
	public CrewWoman(int age, int yearsInService, String name){
		this.name = name;
		this.yearsInService = yearsInService;
		this.age = age;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public int getYearsInService() {
		return this.yearsInService;
	}

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
		CrewWoman other = (CrewWoman) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
