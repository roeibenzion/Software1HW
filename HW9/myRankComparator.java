package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;
import java.util.Map;

public class myRankComparator implements Comparator<Map.Entry<OfficerRank, Integer>> {

	@Override
	public int compare(Map.Entry<OfficerRank, Integer> o1, Map.Entry<OfficerRank, Integer> o2) {
		int compare = o1.getValue().compareTo(o2.getValue());
		if(compare != 0)
			return compare;
		return o1.getKey().compareTo(o2.getKey());
	}

	

}
