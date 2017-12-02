public class Djb2
{
	public static int hash(String str) // Same thing as Java's hashCode if using 0 and don't use Math.abs?
	{
		// Usar int em vez de long?

		int hash = 3481; // 0 instead of 3481

		for (int i = 0; i < str.length(); i++) 
		{
			hash = str.charAt(i) + ((hash << 5) - hash);
		}
		return Math.abs(hash);

	}
	
	/*public static long hash(int i)
	{
		return (new Integer(i)).hashCode();
	}*/
}
