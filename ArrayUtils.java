
public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		// TODO
		if(((direction != 'R') && (direction != 'L')) || array.length == 0)
			return array;
		
		if(move < 0)
		{
			move  = -move;
			if(direction == 'R')
				direction = 'L';
			else
				direction = 'R';
		}
		move %= array.length; //Normalise move
		int [] temp = new int [array.length];
		int go = 0;
		if(direction == 'R')
		{
			for (int i = 0; i < array.length; i++) {
				go = (move + i)%array.length;
				temp[go] = array[i]; 
			}
		}
		else
		{
			for (int i = 0; i < array.length; i++) {
				go = (i - move)%array.length;
				if(go < 0)
					go = array.length+go;
				temp[go] = array[i]; 
			}
		}
		for (int i = 0; i < temp.length; i++) {
			array[i] = temp[i];
		}
		return array;
	}


	public static int findShortestPath(int[][] m, int i, int j) {
		// TODO
		int min = findShortestPath(m,i, 0, j);
		if(min == Integer.MAX_VALUE)
			return -1;
		return min;
	}
	private static int findShortestPath(int [][] m, int k ,int count, int j)
	{
		if(k == j)
			return count;
		int min = Integer.MAX_VALUE;
		for (int p = 0; p < m.length; p++) {
			if(k == p)
				continue;
			if(m[k][p] == 1)
			{
				m[k][p] = 0; 
				min = Math.min(findShortestPath(m, p,count+1, j), min);
				m[k][p] = 1;
			}
		}
		return min;
	}
}
