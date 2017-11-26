import static java.lang.Math.toIntExact;

public class CountingFilter extends BloomFilter
{

	public CountingFilter(int size)
	{
		super(size);
	}
	
	static void addMember(int k, String member)
	{
		for (int i = 0; i < k; i++)
		{
			member += String.valueOf(i); //i+1?
			int index = toIntExact(Djb2.hash(member)%bloomFilter.length);
			bloomFilter[index]++;
		}
		
	}
	
	static int countMember(int k, String member)
	{
		int count = 0;
		if (existsMember(k, member))
		{
			for (int i = 0; i < k; i++)
			{
				member += String.valueOf(i); //i+1?
				int index = toIntExact(Djb2.hash(member)%bloomFilter.length);
				if (bloomFilter[index] > 0)
				{
					count = bloomFilter[index];
					break;
				} 
			}	
		}
		return count;
	}
}
