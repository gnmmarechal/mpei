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
		List<String> skillList = readFile(skillListFile);
		Misc.setSkillSize(skillList.size());
		List<User> userList = readTable(readFile(tableFile));
		
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
	
	public static ArrayList<User> readTable(List<String> table)
	{
		ArrayList<User> tableData = new ArrayList<User>();
		
		for (int i = 0; i < table.size(); i++)
		{
			try
			{
				User tempUser = new User(table.size());
				String line = table.get(i);
				String[] csvData = line.split(",");
				tempUser.userID = Integer.valueOf(csvData[0]);
				tempUser.educationLevelID = Integer.valueOf(csvData[2]);
				tempUser.userName = csvData[3];
				tempUser.phoneNumber = csvData[4];
				tempUser.userBirthdate = Long.valueOf(csvData[5]);
				tempUser.userTimestamp = Long.valueOf(csvData[6]);
				csvData = csvData[1].split(";");
				if (csvData.length > 0 && !csvData[0].equals(""))
				{
					for (int j = 0; j < csvData.length; j++)
					{
						tempUser.addSkill(Integer.valueOf(csvData[j]));
					}
				}
				tableData.add(tempUser);
			} catch (Exception e)
			{
				log("WARNING: Invalid data. Skipping. Error: " + e.getMessage());
			}
			
		}
		
		
		return tableData;
	}
	
	public static List<String> readFile(File fileToRead) throws IOException
	{
		List<String> file = new ArrayList<String>();
		Scanner fileScanner = new Scanner(fileToRead);
		while (fileScanner.hasNextLine())
		{
			file.add(fileScanner.nextLine());
		}
		fileScanner.close();		
		return file;
	}
	
	public static void printFile(File fileToPrint, List<String> contents) throws Exception
	{
		PrintWriter pw = new PrintWriter(fileToPrint);
		for (String line : contents)
		{
			pw.println(line);
		}
		pw.close();
	}
	
	public static void alphaSort(File fileToSort) throws Exception
	{
		List<String> file = readFile(fileToSort);
		Collections.sort(file);
		printFile(fileToSort, file);
	}
	
	public static void addToFile(File fileToPrint, List<String> contents) throws Exception
	{
		List<String> file = new ArrayList<String>();
		if (fileToPrint.exists() && fileToPrint.isFile())
			file = readFile(fileToPrint);
		for (String line : contents)
		{
			file.add(line);
		}
		printFile(fileToPrint, file);
	}
	
	public static void addToFile(File fileToPrint, String line) throws Exception
	{
		List<String> lineList = new ArrayList<String>();
		lineList.add(line);
		addToFile(fileToPrint, lineList);
	}
}
