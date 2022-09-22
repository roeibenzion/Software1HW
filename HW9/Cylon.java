package il.ac.tau.cs.sw1.ex9.starfleet;

public class Cylon implements CrewMember {

	protected CrewWoman pretend;
	protected int modelNumber;
	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		pretend = new CrewWoman(age, yearsInService, name);
		this.modelNumber = modelNumber;
	}
	@Override
	public String getName() {
		return pretend.getName();
	}
	@Override
	public int getAge() {
		return pretend.getAge();
	}
	@Override
	public int getYearsInService() {
		return pretend.getYearsInService();
	}
	public int getModelNumber() {
		return modelNumber;
	}
	@Override
	public int hashCode() {
		return pretend.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return pretend.equals(obj);
	}
	
	
}
