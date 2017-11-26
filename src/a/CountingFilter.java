import static java.lang.Math.toIntExact;

public class CountingFilter // Doesn't extend BloomFilter as I didn't want to expose bloomFilter, even as protected.
{
	static private short[] bloomFilter;

	public CountingFilter(int size)
	{
		bloomFilter = new short[size];
	}
	
	static short[] getArray()
	{
		return bloomFilter;
	}
	
	static int getSize()
	{
		return bloomFilter.length;
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
	
	static void removeMember(int k, String member)
	{
		for (int i = 0; i < k; i++)
		{
			member += String.valueOf(i); //i+1?
			int index = toIntExact(Djb2.hash(member)%bloomFilter.length);
			if (bloomFilter[index] > 0)
			{
				bloomFilter[index]--;
				break;
			} 
		}		
	}
	
	static boolean existsMember(int k, String member)
	{
		boolean exists = true;
		for (int i = 0; i < k; i++)
		{
			member += String.valueOf(i);
			int index = toIntExact(Djb2.hash(member)%bloomFilter.length);
			if (bloomFilter[index] == 0)
			{
				exists = false;
				break;
			}
		}
		
		return exists;
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
