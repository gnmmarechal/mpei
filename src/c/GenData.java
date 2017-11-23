import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GenData
{
	// Files
	static File namesFile = new File("names.txt");
	
	
	public static void main(String[] args)
	{
		System.out.println("PROGRAM C (Name Subject to Change) - Version 1.0\n");
		// Other Variables
		int userNumber = 0;
		List<String> namesList = new ArrayList<String>();
		List<User> userList = new ArrayList<User>();
		
		// Handle arguments
		if (args.length > 0)
			userNumber = Integer.parseInt(args[0]);
		else
		{
			Scanner sca = new Scanner(System.in);
			System.out.printf("USER NUMBER>");
			userNumber = sca.nextInt();
		}		
		
		// Read the names file to the list
		try
		{
			Scanner fileScanner = new Scanner(namesFile);
			while (fileScanner.hasNextLine())
			{
				namesList.add(fileScanner.nextLine());
			}
		} catch (Exception e) // Handle exceptions by halting the program
		{
			System.err.println("ERROR: " + e.getClass().getCanonicalName() + " : " + e.getMessage());
			return;
		}
		
		System.out.println("Number of users to be generated: " + userNumber);
		System.out.println("Number of different names to be used: " + namesList.size());
		
		// Create user list
		for (int user = 0; user < userNumber; user++)
		{
			User tempUser = new User();
			tempUser.userID = user + 1;
			
			// Name
			System.out.println("Generating name for user " + tempUser.userID);
			Random rand = new Random();
			int namesNumber = ThreadLocalRandom.current().nextInt(1, 4 + 1);
			String sIfOne = "";
			if (namesNumber != 1) sIfOne = "s";
			System.out.println("User will have " + namesNumber + " name" + sIfOne);
			String fullName = "";
			for (int i = 0; i < namesNumber; i++)
			{
				fullName += namesList.get(ThreadLocalRandom.current().nextInt(0, namesList.size())) + " ";
			}
			fullName = fullName.substring(0, fullName.length() - 1);
			
			tempUser.userName = fullName;
			System.out.println("User Name: "+ tempUser.userName);
			
			// Birthdate
			System.out.println("Generating birthdate for user " + tempUser.userID);
			int lowerBound = 657170474;
			int higherBound = 909631274;
			tempUser.userBirthdate = rand.nextInt(higherBound - lowerBound) + lowerBound;
			
			// Education Level
			tempUser.educationLevelID = rand.nextInt(6);
			System.out.println("User " + tempUser.userID + " has education level set to " + tempUser.educationLevelID);
			// Skill IDs
			// For now, skills range from 1 to 50
			int skillAmount = rand.nextInt(14);
			for (int i = 0; i < skillAmount; i++)
			{
				int randSkill = rand.nextInt(50 - 1) + 1;
				if (tempUser.skillIDs.contains(randSkill))
					i--;
				else
					tempUser.skillIDs.add(randSkill);
			}
			System.out.println("User " + tempUser.userID + " has skills set to : " + Arrays.toString(tempUser.skillIDs.toArray()));
			
			// Adding user to the user list
			tempUser.userTimestamp = System.currentTimeMillis();
			userList.add(tempUser);

		} 
		
		// Create the tables and write to file
		// ...
	}
}
