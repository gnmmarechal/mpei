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
		
	}
}

