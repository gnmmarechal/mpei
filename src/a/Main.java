import java.io.*;
import java.util.*;

public class Main
{
	 
	public static void main(String[] args) throws IOException
	{
		// Test Class for the Functionality
		
		// Input files
		File skillListFile = new File("skills.txt");
		File tableAFile = new File("tableA.tb");
		File tableBFile = new File("tableB.tb");
		
		// Read files
		Scanner fileScanner = new Scanner(skillListFile);
		List<String> skillList = new ArrayList<String>();
		
		while (fileScanner.hasNextLine())
		{
			skillList.add(fileScanner.nextLine());
		}
		fileScanner.close();
		
		List<User> userList = new ArrayList<User>();
		
		log("Loaded skill list:\n" + Arrays.toString(skillList.toArray()));
		
	}
	
	public static void log(String args)
	{
		System.out.println(args);
	}
}
