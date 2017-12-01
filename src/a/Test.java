import java.util.*;

public class Test // Test class
{
	
	public static void main (String[] args) 
	{
		// Testing the Bloom Filter

		String[] Membros = {"Espanha", "Portugal", "Itália", "EUA", "Alemanha"};
		String[] Teste = {"Itália", "Polónia", "França", "Espanha", "EUA", "Alemanha", "Banana"};
		System.out.println("Bloom Filter Test:\nMembers : " + Arrays.toString(Membros) + "\nTest: " + Arrays.toString(Teste));
		
		System.out.println("Initializing Bloom Filter...");
		int m = 100;
		BloomFilter B = new BloomFilter(m);
		
		int k = 3;
		System.out.println("Using " + k + " hash functions and " + m + " size.");
		System.out.println(Arrays.toString(B.getArray()) + "\nAdding members...");
		
		for (int i = 0; i < Membros.length; i++)
		{
			B.addMember(k, Membros[i]);
		}
		String remMem = "EUA";
		System.out.println("Removing member " + remMem + "...");
		B.removeMember(k, remMem);
		
		System.out.println("Testing membership:\n");
		for (int i = 0; i < Teste.length; i++)
		{
			boolean ok = B.existsMember(k, Teste[i]);
			String R = "NO";
			if (ok)
				R = "YES";
			System.out.println(Teste[i] + " " + R);
		}
		
		// Testing the Counting Filter
		
		System.out.println("Testing Counting Filter...");
		CountingFilter C = new CountingFilter(m);
		
		int times = 2;
		String addMem = "TEST";
		System.out.println("Adding member " + addMem + " " + String.valueOf(times) + ".");
		for (int i = 0; i < times; i++)
		{
			C.addMember(k, "TEST");	
		}
		
		System.out.println("Counting membership.");
		System.out.println(addMem + ": " + C.countMember(k, "TEST"));
		times = 1;
		System.out.println("Removing " + addMem + " " + times + " times.\nCounting membership.");
		
		for (int i = 0; i < times; i++)
		{
			C.removeMember(k, "TEST");
		}
		
		System.out.println(addMem + ": " + C.countMember(k, "TEST"));		
		
		// Testing Jaccard Distance
		
		ArrayList<ArrayList<Integer>> testList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> skillListA = new ArrayList<Integer>();
		
		skillListA.add(11);
		skillListA.add(12);
		skillListA.add(13);
		skillListA.add(25);
		
		ArrayList<Integer> skillListB = new ArrayList<Integer>();
		
		skillListB.add(11);
		skillListB.add(145);
		skillListB.add(12);
		skillListB.add(25);

		ArrayList<Integer> skillListC = new ArrayList<Integer>();
		
		skillListC.add(11);
		skillListC.add(145);
		skillListC.add(12);
		skillListC.add(25);			
		
		System.out.println("A: " + Arrays.toString(skillListA.toArray()));
		System.out.println("B: " + Arrays.toString(skillListB.toArray()));
		System.out.println("C: " + Arrays.toString(skillListC.toArray()));			
		testList.add(skillListA);
		testList.add(skillListB);
		testList.add(skillListC);
		
		double[][] jaccardMatrix = Jaccard.getDistanceMatrix(3, testList);
		System.out.println(Jaccard.getDistance(jaccardMatrix, 1, 1));
		
		printGrid(jaccardMatrix);
		
		int userSize = 10;
		int member = 10;
		System.out.println("Creating user and testing.");
		User test = new User(userSize);
		System.out.println("Adding member: " + member);
		test.skillIDFilter.addMember(3, member);
		System.out.println("Does member " + member + " exist? : " + test.skillIDFilter.existsMember(3, 10));
	}
	public static void printGrid(double[][] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.println(Arrays.toString(array[i]));
		}
		
	}	
	
	public static void printGrid(long[][] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.println(Arrays.toString(array[i]));
		}		
	}
}

