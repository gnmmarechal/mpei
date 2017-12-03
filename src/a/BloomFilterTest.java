import java.util.*;
import glib.utils.Lists;
public class BloomFilterTest
{
	public static void main(String[] args)
	{
		int testes = 100;
		int x = 10; // k = elemNo/x
		Random rand = new Random();
		for (int j = 0; j < testes; j++)
		{
		
			int elemNo = rand.nextInt(200) + 1;
			int k = elemNo/x;
			int fp = 0;
			int m = 0;
			int g = 0;
			List<String> elem = new ArrayList<String>();
			List<String> elem2 = new ArrayList<String>();
			BloomFilter b = new BloomFilter(elemNo * 10); // Pelos nossos testes, é ideal que o tamanho seja 10* o num de elementos
			for (int i = 0; i < elemNo; i++)
			{
				String a = String.valueOf(rand.nextInt(200)) + String.valueOf(rand.nextInt(300)) + String.valueOf(rand.nextInt(219));
				if (!elem.contains(a))
					elem.add(a);
				else
					i--;
			}
			
			for (int i = 0; i < elemNo*2; i++)
			{
				String a = String.valueOf(rand.nextInt(200)) + String.valueOf(rand.nextInt(300)) + String.valueOf(rand.nextInt(219));
				if (!elem.contains(a))
					elem2.add(a);
				else
					i--;
			}
			
			List<String> elem3 = Lists.getUnion(elem, elem2);
			for (String e : elem)
			{
				b.addMember(k, e);
			}
			
			for (String e : elem3)
			{
				if (b.existsMember(k, e))
				{
					if (elem.contains(e))
						g++;
					else
						fp++;
				}
				else
					m++;
			}
			Main.log("k = " + k + "\nSet Size = " + elemNo + "\nFalse Positives = " +  fp + " (" + String.valueOf((double) 100 * (double) fp / (double) g) + "%)\nTotal Correct Checks = " + g + "\nNegative Checks = " + m);
		}
		
	}
}
