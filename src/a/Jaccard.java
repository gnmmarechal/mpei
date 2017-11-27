import java.util.*;
import glib.utils.Lists;

public class Jaccard
{
	public static double[][] getDistance(int userNumber, ArrayList<ArrayList<Integer>> dataSet)
	{
		double[][] jaccardDistance = new double[userNumber][userNumber];
		int startVal = 1;
		for (int i = 0; i < userNumber; i++)
		{
			for (int j = 0; j < userNumber; j++)
			{
				jaccardDistance[i][j] = startVal;
			}
			
		}
		
		
		for (int i = 0; i < userNumber; i++)
		{
			for (int j = i + 1; j < userNumber; j++)
			{
				int intersectionSize = Lists.getIntersection(dataSet.get(i), dataSet.get(i+1)).size();
				int unionSize = Lists.getUnion(dataSet.get(i), dataSet.get(i+1)).size();
				
				System.out.println("INTERSECTION: " + intersectionSize);
				System.out.println("UNION: " + unionSize);
				jaccardDistance[i][j] = (double) 1 - ((double) intersectionSize/((double) dataSet.get(i).size() + (double) dataSet.get(j).size() - (double) intersectionSize));
				// jaccardDistance[i][j] = (double) intersectionSize/unionSize;
				// Jaccard Similarity : J(A, B) = |A ∩ B| / |A ∪ B|
				// So Jaccard Distance = 1 - JS
				if (unionSize == 0) jaccardDistance[i][j] = 1;
				System.out.println("J: " + jaccardDistance[i][j] + " J2: " + String.valueOf((double) intersectionSize/unionSize));
			}
			
		}
		return jaccardDistance;
	}
}
