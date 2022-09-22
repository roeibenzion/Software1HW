package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{
	
	
	public class Pair
	{
		private T key;
		private Integer num;
		
		public Pair(T k, Integer n)
		{
			key = k;
			num = n;
		}
	}
	
	public class ComparePair implements Comparator<Pair>
	{

	@Override
	public int compare(Pair arg0, Pair arg1) {
		if(arg0.num == arg1.num)
			return (arg0.key.compareTo(arg1.key));
		return (arg0.num > arg1.num ? -1 : (arg1.num > arg0.num ? 1 : 0));
	}
	}
	
	private List<Pair> histPairs;
	private int curr;
	
	public HashMapHistogramIterator(HashMap<T, Integer> hist)
	{
		curr = 0;
		histPairs = new ArrayList<Pair>();
		if(hist != null) {
			Set<T> temp = hist.keySet();
			for (T t : temp) {
				Pair p = new Pair(t, hist.get(t));
				histPairs.add(p);
			}
			Collections.sort(histPairs, new ComparePair());
		}
	}
	@Override
	public boolean hasNext() {
		if(histPairs.size() == 0)
			return false;
		
		return !(histPairs.size() <= curr);
	}

	@Override
	public T next() {
		T temp = histPairs.get(curr).key;
		curr++;
		return temp; 
	}
	
	/*
	public void remove(T item, int num)
	{
		Pair p = new Pair(item, num);
		int index = histPairs.indexOf(item);
		if(num == 0)
		{
			if(index == curr)
				curr++;
			if(index < curr)
				curr--;
			histPairs.remove(index);
		}
		else {
			histPairs.remove(index);
			histPairs.add(index, p);
		}
		Collections.sort(histPairs, new ComparePair());
	}
	
	public void add(T item, int num)
	{
		Pair p = new Pair(item, num);
		int index = histPairs.indexOf(item);
		histPairs.remove(index);
		histPairs.add(index, p);
		Collections.sort(histPairs, new ComparePair());
	}
	*/
	/*
	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
	*/
}
