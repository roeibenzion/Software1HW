package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;

public class mySpaceShpiComparator<T extends Spaceship> implements Comparator<T> {

	@Override
	public int compare(Spaceship o1, Spaceship o2) {
		Integer o1FirePower = o1.getFirePower(), o2FirePower = o2.getFirePower();
		int cmp = o1FirePower.compareTo(o2FirePower);
		if(cmp != 0)
			return -cmp;
		Integer o1ManuYear = o1.getCommissionYear(), o2ManuYear = o2.getCommissionYear();
		cmp = o1ManuYear.compareTo(o2ManuYear);
		if(cmp != 0)
			return -cmp;
		return (o1.getName().compareTo(o2.getName()));
	}

}
