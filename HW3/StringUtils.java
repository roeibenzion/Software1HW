
public class StringUtils {

	public static String findSortedSequence(String str) {
		// TODO
		str = str.trim();
		String [] words = str.split(" ");
		String ret = "", temp = words[0];
		for (int i = 1; i < words.length; i++) {
			if (isOrdered(words[i-1], words[i]))
				temp += " " + words[i];
			else
				temp = words[i];
			if(ret.length() <= temp.length())
				ret = temp;
		}
		return ret; //Replace this with the correct returned value
	}
	
	private static boolean isOrdered(String s1, String s2)
	{
		int i = 0;
		while(!s1.isEmpty() && !s2.isEmpty() && i < s1.length() && i < s2.length())
		{
			if(s1.charAt(i) < s2.charAt(i))
				return true;
			if(s2.charAt(i) < s1.charAt(i))
				return false;
			i++;
		}
		return true;
	}
	
	public static boolean isEditDistanceOne(String a, String b){
		// TODO
		if(a == null || b == null)
			return false;
		
		if(a.isEmpty() && b.isEmpty())
			return true;
		
		if(a.isEmpty() || b.isEmpty())
			return false;
		
		if(a.equals(b))
			return true;
		
		if(Math.abs(a.length() - b.length()) > 1)
			return false;
		
		if(a.length() == b.length())
			return searchOneDiff(a, b);
		
		if(a.length() > b.length())
			return add(a, b);
		
		else
			return add(b,a);
	}
	
	private static boolean searchOneDiff(String a, String b)
	{
		int i = 0, count = 0;
		while(i < a.length()) //length of a == length of b
		{
			if(a.charAt(i) != b.charAt(i))
			{
				count++;
				if(count > 1)
					return false;
			}
			i++;
		}
		return true;
	}
	
	private static boolean add(String a, String b)
	{
		int i = 0, j = 0, moved = 0;
		while(i < a.length() && j < b.length())
		{
			if(a.charAt(i) != b.charAt(j))
			{
				moved++;
				if(moved > 1)
					return false;
			}
			else
				j++;
			i++;
		}
		return true;
	}
}
