import java.io.*;
import java.util.*;

public class Main
{
	
	 
	public static void main(String[] args) throws IOException
	{
		// Test Class for the Functionality
		
		// Input files
		File skillListFile = new File("skills.txt");
		File tableFile = new File("table.tb");
		
		// Read files
		Scanner fileScanner = new Scanner(skillListFile);
		List<String> skillList = new ArrayList<String>();
		List<String> tableA = new ArrayList<String>();
		
		while (fileScanner.hasNextLine())
		{
			skillList.add(fileScanner.nextLine());
		}
		fileScanner.close();
		Misc.setSkillSize(skillList.size());
		fileScanner = new Scanner(tableFile);
		
		while (fileScanner.hasNextLine())
		{
			tableA.add(fileScanner.nextLine());
		}
		fileScanner.close();
		
		List<User> userList = readTable(tableA);
		
		log("Loaded skill list (" + Misc.getSkillSize() + "):\n" + Arrays.toString(skillList.toArray()));
		
		log("Loaded user list (" + userList.size() + ").");
		
		// Creating job object
		User jobAIdealUser = new User();
		String[] neededSkills = {"Portuguese", "English", "Translation"};
		String[] skillListArray = new String[skillList.size()];
		skillListArray = skillList.toArray(skillListArray);
		jobAIdealUser.educationLevelID = 4;
		jobAIdealUser.addSkill(skillListArray, neededSkills);
		Job jobA = new Job(jobAIdealUser, "Portuguese-English Translator");
		log("Job: " + jobA.getTitle() + "\nIdeal User Education Level: " + jobA.idealUser.educationLevelID + "\nIdeal Skillset: " + Arrays.toString(jobA.idealUser.skillIDs.toArray()));
		
		// Analyse the data and compare to the ideal user, add to the list of ideal users 
		
		// To find perfect matches, it uses the bloom filter. If it doesn't find, it proceeds to analyse similarities
		
		List<User> perfectMatches = new ArrayList<User>();
		for (int i = 0; i < userList.size(); i++)
		{
			if (userList.get(i).hasSkill(jobA.idealUser.skillIDs))
				perfectMatches.add(userList.get(i));
		}
		
		for (User user : perfectMatches)
		{
			log("Found perfect match for " + jobA.getTitle() + " :\n User ID: " + user.userID + "\n Name: " + user.userName + "\n Skills: " + Arrays.toString(user.getSkills(skillListArray))); 
		}
		
		
	}
	
	public static void log(Object args)
	{
		System.out.println(args);
	}
	
	static ArrayList<User> readTable(List<String> table)
	{
		ArrayList<User> tableData = new ArrayList<User>();
		
		for (int i = 0; i < table.size(); i++)
		{
			User tempUser = new User(table.size());
			String line = table.get(i);
			String[] csvData = line.split(",");
			tempUser.userID = Integer.valueOf(csvData[0]);
			tempUser.educationLevelID = Integer.valueOf(csvData[2]);
			tempUser.userName = csvData[3];
			tempUser.userBirthdate = Long.valueOf(csvData[4]);
			tempUser.userTimestamp = Long.valueOf(csvData[5]);
			csvData = csvData[1].split(";");
			for (int j = 0; j < csvData.length; j++)
			{
				tempUser.addSkill(Integer.valueOf(csvData[j]));
			}
			tableData.add(tempUser);
			
		}
		
		
		return tableData;
	}
}
