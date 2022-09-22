package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;
    private int left;
    
    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
        left = c;
    }

    public static class Item implements Comparable<Item> {
    	private double calculate; //added - this is an inner class so the private vars can be reached.
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
            calculate = v/w; //added
        }
        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
		@Override
		public int compareTo(Item o) {
			// TODO Auto-generated method stub
			if(o.calculate > this.calculate)
				return 1;
			if(this.calculate > o.calculate)
				return -1;
			return 0;
		}
    }
    
    @Override
    public Iterator<Item> selection() {
    	Collections.sort(lst);
    	return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
    	return left > 0;
    }
    @Override
    public void assign(List<Item> candidates_lst, Item element) {
	    	
    		if(left - element.weight >= 0)
    		{
    			candidates_lst.add(element);
    			left-=element.weight;
    		}
    		else
    		{
    			Item e = calculateRelative(element, left);
    			candidates_lst.add(element);
    			left -= e.weight;
    		}
    }
    
    private Item calculateRelative(Item i, double left)
    {
    	double factor = left/i.weight;
		i.weight = left;
		i.value = factor*i.value;
		i.calculate  = i.value / i.weight;
		return i;
    }
    
    @Override
    public boolean solution(List<Item> candidates_lst) {
    	boolean ret = left == 0 || (isNotLeft(candidates_lst));
    	if(ret)
    		left = capacity;
        return ret;
    }
    
    private boolean isNotLeft(List<Item> candidates_lst)
    {
    	for (Item item : lst) {
			if(!candidates_lst.contains(item))
				return false;
		}
    	return true;
    }
}
