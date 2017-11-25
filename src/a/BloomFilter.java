public class BloomFilter
{
	static private short[] bloomFilter;

	public BloomFilter(int size)
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
			int index = Dbj2.hash(member)%bloomFilter.length;
			bloomFilter[index] = 1;
		}
		
	}
	
	static void removeMember(int k, String member)
	{
		for (int i = 0; i < k; i++)
		{
			member += String.valueOf(i); //i+1?
			int index = Dbj2.hash(member)%bloomFilter.length;
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
			int index = Dbj2.hash(member)%bloomFilter.length;
			if (bloomFilter[index] == 0)
			{
				exists = false;
				break;
			}
		}
		
		return exists;
	}
}
