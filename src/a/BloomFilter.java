import static java.lang.Math.toIntExact;
public class BloomFilter
{
	protected short[] bloomFilter; // Protected so that I can access it from CountingFilter

	public BloomFilter(int size)
	{
		bloomFilter = new short[size];
	}
	
	short[] getArray()
	{
		return bloomFilter;
	}
	
	int getSize()
	{
		return bloomFilter.length;
	}
	
	void addMember(int k, Object memberObject)
	{
		String member = String.valueOf(memberObject);
		for (int i = 0; i < k; i++)
		{
			member += String.valueOf(i); //i+1?
			int index = toIntExact(Djb2.hash(member)%bloomFilter.length);
			bloomFilter[index] = 1;
		}
		
	}
	
	void removeMember(int k, Object memberObject)
	{
		String member = String.valueOf(memberObject);
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
	
	boolean existsMember(int k, Object memberObject)
	{
		String member = String.valueOf(memberObject);		
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
}
