public class Djb2
{
	public static long hash(String str) // Same thing as Java's hashCode?
	{

		long hash = 0; // 0 instead of 3481

		for (int i = 0; i < str.length(); i++) 
		{
			hash = str.charAt(i) + ((hash << 5) - hash);
		}
		return hash;

	}	
}
