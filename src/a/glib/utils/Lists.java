// Part of glib: https://github.com/gnmmarechal/glib
package glib.utils;
import java.util.*;

public class Lists
{
	public static boolean hasDuplicates(String[] list)
	{
		Set<String> lump = new HashSet<String>();
		for (String i : list)
		{
			if (lump.contains(i)) return true;
			lump.add(i);
		}
		return false;
	}
	
	public static <T> List<T> getIntersection(List<T> listA, List<T> listB)
	{
		List<T> retList = new ArrayList<T>();
		
		for (T t : listA)
		{
			if (listB.contains(t))
				retList.add(t);
		}
		return retList;
	}
	
	public static <T> List<T> getUnion(List<T> listA, List<T> listB)
	{
		Set<T> retSet = new HashSet<T>();
		retSet.addAll(listA);
		retSet.addAll(listB);
		return new ArrayList<T>(retSet);
	}
}
