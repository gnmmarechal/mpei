public class Dbj2
{
	public static int hash(String str) 
	{

		int hash = 3481;

		for (int i = 0; i < str.length(); i++) 
		{
			hash = str.charAt(i) + ((hash << 5) - hash);
		}
		return hash;

	}
}
