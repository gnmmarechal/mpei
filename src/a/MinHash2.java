import java.util.*;
public class MinHash2
{
	private int sigSize;
	private int dicSize;
	
	public static int[][] signatures(int sigSize, int dicSize, List<List<Integer>> docSetList)
	{
		int[][] minSign = new int[docSetList.size()][sigSize];
		for (int i = 0; i < docSetList.size(); i++)
		{
			minSign[i] = signature(sigSize, dicSize, docSetList.get(i));
		}
		
		return minSign;
	}
	
	public static int[] signature(int sigSize, int dicSize, List<Integer> docSet)
	{
		int[] minSign = new int[sigSize];
		String hashind = "";
		for (int i = 0; i < sigSize; i++)
		{
			int mini = Integer.MAX_VALUE;
			hashind += String.valueOf(i);
			for (int j = 0; j < docSet.size(); j++)
			{
				String n = String.valueOf(docSet.get(j)) + hashind;
				// System.out.println(n);
				int gHash = hash(n);
				if (gHash < mini)
					mini = gHash;
			}
			minSign[i] = mini;
		}
		
		
		return minSign;
	}
	
	public static int hash(String value)
	{
		// return Math.abs(value.hashCode());
		return Djb2.hash(value);
	}
}
