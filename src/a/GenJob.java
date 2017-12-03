import java.util.Scanner;
public class GenJob // Adds a job to the list
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.printf("JOB NAME>");
		String jobName = sc.nextLine();
		boolean a = true;
		String skillAString = "";
		while (a)
		{
			System.out.printf("MIN USER SKILLS>");
			String in = sc.nextLine();
			if (in.equals("-1"))
				a = false;
			else
				skillAString +=  in + ";";
		}
		skillAString = skillAString.substring(0, skillAString.length() - 1);
		System.out.printf("MIN USER EDU LEVEL>");
		String minEdu = sc.nextLine();
		
		String skillBString = "";
		while (a)
		{
			System.out.printf("IDEAL USER SKILLS>");
			String in = sc.nextLine();
			if (in.equals("-1"))
				a = false;
			else
				skillBString +=  in + ";";
		}
		skillBString = skillBString.substring(0, skillBString.length() - 1);
	}
}
