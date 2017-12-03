import java.util.Scanner;
import java.io.File;
public class GenJob // Adds a job to the list
{
	public static void main(String[] args) throws Exception
	{
		while (true)
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
			a = true;
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
			System.out.printf("IDEAL USER EDU LEVEL>");
			String idEdu = sc.nextLine();			
			System.out.printf("MIN SIM INDEX>");
			String d = sc.nextLine();
			
			File j = new File("jobs.tb");
			Main.addToFile(j, skillBString + "," + idEdu + "," + skillAString + "," + minEdu + "," + jobName + "," + d); 
		}
	}
}
