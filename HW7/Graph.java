package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,...,n]
    List<Set<Integer>> sets;
    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1+1;
        sets = null;
    }
    public static class Edge implements Comparable<Edge>{
        int node1, node2;
        double weight;
        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }

		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			int ret = o.weight > this.weight ? -1 : (this.weight > o.weight ? 1 : 0);
			if(ret == 0)
			{
				ret = o.node1 > this.node1 ? -1 : (this.node1 > o.node1 ? 1 : 0);
				if(ret == 0)
					ret = o.node2 > this.node2 ? -1 : (this.node2 > o.node2 ? 1 : 0);
			}
			return ret;
		}
    }

    @Override
    public Iterator<Edge> selection() {
    	Collections.sort(lst);
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
    	if(element.node1 == element.node2) 
    		return false;
    	if(sets == null)
    		return true;
    	Iterator<Set<Integer>> itr = sets.iterator();
    	while(itr.hasNext())
    	{
    		boolean found1 = false, found2 = false;
    		Set<Integer> st = itr.next();
    		for (Integer n : st) {
				if(element.node1 == n)
					found1 = true;
				if(element.node2 == n)
					found2 = true;
			}
    		if(found1 && found2)
    			return false;
    	}
    	return true;
    }
    
    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
    	candidates_lst.add(element);
    	if(sets == null)
    	{
    		sets = new ArrayList<Set<Integer>>();
    		createSet(element);
    		return;
    	}
    	Iterator<Set<Integer>> itr = sets.iterator();
    	int index1 = -1, index2 = -1;
    	while(itr.hasNext())
    	{
    		Set<Integer> st = itr.next();
    		for (Integer n : st) {
    			if(element.node1 == n)
    				index1 = sets.indexOf(st);
    			if(element.node2 == n)
    				index2 = sets.indexOf(st);
    		}
    	}
    	if(index1 == -1 && index2 == -1) //implies new set
    	{
    		createSet(element);
    	}
    	else if(index2 == -1) //merge1
    	{
    		addElement(index1, element);
    	}
    	
    	else if(index1 == -1) //merge2
    	{
    		addElement(index2, element);
    	}
    	
    	else //implies union
    	{
    		addElement(index1, element);
    		addElement(index2, element);
    		union(index1, index2);
    	}
    }
    
    private void createSet(Edge element)
    {
    	Set<Integer> st = new HashSet<Integer>();
		st.add(element.node1);
		st.add(element.node2);
		sets.add(st);
    }
    
    private void addElement(int i, Edge element)
    {
    	sets.get(i).add(element.node1);
		sets.get(i).add(element.node2);
    }
    private void union(int index1, int index2)
    {
    	Set<Integer> st = new HashSet<Integer>();
		st.addAll(sets.get(index1));
		st.addAll(sets.get(index2));
		sets.remove(index1);
		if(index2 > index1)
		sets.remove(index2-1);
		else
			sets.remove(index2);
		sets.add(st);
    }
    @Override
    public boolean solution(List<Edge> candidates_lst) {
    	 if(candidates_lst.size() == n-1 && sets.size() == 1 && allFound())
    	 {
    		 sets = null;
    		 return true;
    	 }
    	 return false;
    }
    
    private boolean allFound()
    {
    	int [] arr = new int [n]; 
    	Iterator<Set<Integer>> itr = sets.iterator();
    	while(itr.hasNext())
    	{
    		Set<Integer> st = itr.next();
    		for (Integer integer : st) {
				if(integer < n)
					arr[integer] = 1;
			}
    	}
    	for (int i = 0; i < arr.length; i++) {
			if(arr[i] == 0)
				return false;
		}
    	return true;
    }
}
