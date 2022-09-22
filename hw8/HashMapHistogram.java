package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{
	
	HashMap<T, Integer> histMap;
	
	@Override
	public Iterator<T> iterator() {
		HashMapHistogramIterator<T> itr = new HashMapHistogramIterator<T>(histMap);
		return itr;
	}

	
	public HashMap<T, Integer> getHistMap() {
		return histMap;
	}


	public void setHistMap(HashMap<T, Integer> histMap) {
		this.histMap = histMap;
	}


	@Override
	public void addItem(T item) {
		Iterator<T> itr = iterator();
		while(itr.hasNext())
		{
			T temp = itr.next();
			if(temp.equals(item))
			{
				histMap.put(item, histMap.get(item)+1);
				return;
			}
		}
		if(histMap == null)
		{
			histMap = new HashMap<T, Integer>();
		}
		histMap.put(item, 1);
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {
		try {
			int rem = histMap.get(item)-1;
			if(rem == 0)
				histMap.remove(item);
			else
				histMap.put(item, histMap.get(item)-1);
		} catch (NullPointerException e) {
			throw new IllegalItemException();
		}
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {
		if(k < 1)
			throw new IllegalKValueException(k);
		
		if(histMap == null)
		{
			histMap = new HashMap<T, Integer>();
			histMap.put(item, k);
			return;
		}
		Iterator<T> itr = iterator();
		while(itr.hasNext())
		{
			T temp = itr.next();
			if(temp.equals(item))
			{
				histMap.put(item, histMap.get(item)+k);
				return;
			}
		}
		histMap.put(item, k);
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
		
		try {
			int rem = histMap.get(item)-k;
			if(rem < 0 || k < 1)
				throw new IllegalKValueException(k);
			if(rem == 0)
				histMap.remove(item);
			else
				histMap.put(item, histMap.get(item)-k);
		} catch (NullPointerException e) {
			throw new IllegalItemException();
		}
	}

	@Override
	public int getCountForItem(T item) {
		try {
			int num = histMap.get(item);
			return num;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public void addAll(Collection<T> items) {
		if(histMap == null)
			histMap = new HashMap<T, Integer>();
		
		Iterator<T> itemsItr = items.iterator();
		while(itemsItr.hasNext())
		{
			boolean added = false;
			T item = itemsItr.next();
			Iterator<T> itr = iterator();
			while(itr.hasNext())
			{
				T tItem = itr.next();
				if(tItem.equals(item))
				{
					histMap.put(tItem, histMap.get(tItem) + 1);
					added = true;
					break;
				}
			}
			if(!added)
				histMap.put(item, 1);
		}
	}

	@Override
	public void clear() {
		histMap.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		Set<T> st = new HashSet<T>();
 		Iterator<T> itr = iterator();
 		while(itr.hasNext())
 		{
 			st.add(itr.next());
 		}
 		return st;
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {
		HashMap<T, Integer> nMap = new HashMap<T, Integer>();
		Iterator<T> itr = anotherHistogram.iterator();
		while(itr.hasNext())
		{
			boolean added = false;
			T anotherItem = itr.next();
			Iterator<T> tItr = iterator();
			while(tItr.hasNext())
			{
				T item = tItr.next();
				if(item.equals(anotherItem))
				{
					nMap.put(item, getCountForItem(item) + anotherHistogram.getCountForItem(anotherItem));
					added = true;
					break;
				}
			}
			if(!added)
			{
				nMap.put(anotherItem, anotherHistogram.getCountForItem(anotherItem));
			}
		}
		histMap = nMap;
	}
}
