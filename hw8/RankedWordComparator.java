package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

class RankedWordComparator implements Comparator<RankedWord>{
	
	rankType cType;
	public RankedWordComparator(rankType cType) {
		this.cType = cType;
	}
	
	@Override
	public int compare(RankedWord o1, RankedWord o2) {
		int cmp1 = o1.getRankByType(cType); 
		int cmp2 = o2.getRankByType(cType);
		
		return (cmp2 > cmp1 ? -1 : (cmp2 < cmp1 ? 1 : 0));
	}	
}
