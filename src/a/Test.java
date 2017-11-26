import java.util.*;

public class Test 
{
	
	public static void main (String[] args) 
	{			
		String[] Membros = {"Espanha", "Portugal", "Itália", "EUA", "Alemanha"};
		String[] Teste = {"Itália", "Polónia", "França", "Espanha", "EUA", "Alemanha", "Banana"};
		BloomFilter B = new BloomFilter(100);
		int k = 3;
		System.out.println(Arrays.toString(B.getArray()));
		
		for (int i = 0; i < Membros.length; i++)
		{
			B.addMember(k, Membros[i]);
		}
		
		B.removeMember(k, "EUA");
		
		for (int i = 0; i < Teste.length; i++)
		{
			boolean ok = B.existsMember(k, Teste[i]);
			String R = "NO";
			if (ok)
				R = "YES";
			System.out.println(Teste[i] + " " + R);
		}
		
		
		
	}
}

