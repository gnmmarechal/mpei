import java.io.*;
import java.util.*;
import glib.utils.Lists;
public class Main
{
	
	 
	public static void main(String[] args) throws IOException
	{
		// Test Class for the Functionality
		
		// Input files
		File skillListFile = new File("skills.txt");
		File tableFile = new File("table.tb");
		File jobFile = new File("jobs.tb");
		
		// Read files
		List<String> skillList = readFile(skillListFile);
		
		Misc.setSkillSize(skillList.size());
		
		List<User> userListFinal = readUserTable(readFile(tableFile));
		
		String[] skillListArray = new String[skillList.size()];
		
		skillListArray = skillList.toArray(skillListArray);		
		
		log("Loaded skill list (" + Misc.getSkillSize() + ").");
		
		log("Loaded user list (" + userListFinal.size() + ").");
		
		// Creating job objects from file
		List<Job> jobList = readJobTable(readFile(jobFile));
		
		log("Loaded job list (" + jobList.size() + ").");
		
		List<List<User>> perfectJobMatchList = new ArrayList<List<User>>();
		
		List<List<User>> similarityOrderedMatchList = new ArrayList<List<User>>();
		

		for (Job job : jobList)
		{
			List<User> userList = new ArrayList<User>();
			for (User u : userListFinal)
			{
				userList.add(u);
			}
			// Find perfect matches for the job (first find minimum matches and then perfect matches. This eliminates all users that aren't necessary)
			log("Job : " + job.getTitle() + "\nIdeal Skillset : " + Arrays.toString(job.idealUser.getSkills().toArray()));
			log("=====Perfect Match Search=====");
			
			List<User> minimumMatchCandidates = new ArrayList<User>();
			List<User> minimumMatches = new ArrayList<User>();
			
			for (User u : userList)
			{
				if (u.hasSkill(job.minUser.skillIDs))
				{
					minimumMatchCandidates.add(u);
				}
			}
			
			for (int i = 0; i < minimumMatchCandidates.size(); i++)
			{
				if (minimumMatchCandidates.get(i).hasSkill(job.minUser.skillIDs))
				{
					minimumMatches.add(minimumMatchCandidates.get(i));
				}			
			}
			
			
			List<User> perfectMatchCandidates = new ArrayList<User>();
			
			for (int i = 0; i < minimumMatches.size(); i++)
			{
				if (minimumMatches.get(i).hasSkill(job.idealUser.skillIDs))
				{
					perfectMatchCandidates.add(minimumMatches.get(i));
					log("Perf cand");
				}	
			}
			
			// Double-check the users using a non-probabilistic method (the Bloom Filter can have false positives, but not false negatives, so we know that
			// every user in perfectMatchCandidates is possibly correct, but all of the others are definitely not perfect matches.
			// The exact method is slower for huge sets of data, but the list is also smaller because the probabilistic method was ran before.
			// The bloom filter may not be faster than the array for only 50 elements at most, however. --> THIS IS IMPORTANT.
			List<User> perfectMatches = new ArrayList<User>();
			
			for (int i = 0; i < perfectMatchCandidates.size(); i++)
			{
				if (perfectMatchCandidates.get(i).hasSkillExact(job.idealUser.skillIDs))
				{
					perfectMatches.add(perfectMatchCandidates.get(i));
				}
			}
			
			
			// Sort by number of skills and then education level
			perfectMatches.sort((o1, o2) -> new Integer(o2.skillIDs.size()).compareTo(new Integer(o1.skillIDs.size())));
			perfectMatches.sort((o1, o2) -> new Integer(o2.educationLevelID).compareTo(new Integer(o1.educationLevelID)));
			for (User user : perfectMatches)
			{
				log("Found perfect match for " + job.getTitle() + " :\n User ID: " + user.userID + "\n Name: " + user.userName + "\n Education Level: " + user.educationLevelID + "\n Skills: " + Arrays.toString(user.getSkills(skillListArray))); 
			}			
			perfectJobMatchList.add(perfectMatches);
			// log("Min Candidates: " + minimumMatchCandidates.size() + "\nMinimum Correct: " + minimumMatches.size() + "\nPerfect Candidates: " + perfectMatchCandidates.size() + "\nPerfects: " + perfectMatches.size());
			// log("Min False Positive Chance: " + String.valueOf(1.0 - (double) minimumMatches.size()/minimumMatchCandidates.size()) + "\nPerfect False Positive Chance: " + String.valueOf(1.0 - (double) perfectMatches.size()/perfectMatchCandidates.size()));
			
			log("Minimum Matches Found: " + minimumMatches.size());
			log("Perfect Matches found: " + perfectMatches.size());
			// Similarity Search
			
			log("=====Similar Match Search=====");
			
			// Compares the similarity between the ideal user and the skill intersection of the ideal user and the real one, then sorts by similarity and then by number of skills.
			userList.removeAll(perfectMatches);
			userList.removeAll(Lists.getIntersection(userList, minimumMatches));
			
			List<List<Integer>> skillsDocList = new ArrayList<List<Integer>>();
			
			for (User user : userList)
			{
				List<Integer> newDoc = new ArrayList<Integer>(user.getSkills());
				skillsDocList.add(newDoc);
			}
			
			if (skillsDocList.size() != 0)
			{
				int signatureSize = 1; 
	
				for (List<Integer> skills : skillsDocList)
				{
					signatureSize += skills.size();
				}
				signatureSize += job.idealUser.skillIDs.size();
				signatureSize = signatureSize/skillsDocList.size() - 1; // Signature size is the average skill number -1
				log(signatureSize);
				if (signatureSize < 1)
					signatureSize = 1;
	
				int[][] sigList = new int[skillsDocList.size()][signatureSize];
				
				
				for (int i = 0; i < skillsDocList.size(); i++) // Gets the signature of the intersection between the ideal user and each user
				{
					sigList[i] = MinHash2.signature(signatureSize, userList.size(), Lists.getIntersection(skillsDocList.get(i), job.idealUser.skillIDs));
					
				}
				
				// Get signature for the ideal user
				int[] idealSignature = MinHash2.signature(signatureSize, userList.size(), job.idealUser.skillIDs);
				
				// Compute similarity for every use
				double simList[] = new double[sigList.length];
				for (int i = 0; i < simList.length; i++)
				{
					simList[i] = Jaccard.getSimilarity(idealSignature, sigList[i]);
				}
				System.out.println(Arrays.toString(simList));
			}
			else
			{
				log("Empty list!");
			}
		}
		
	}
	
	public static void log(Object args)
	{
		System.out.println(args);
	}
	
	public static ArrayList<User> readUserTable(List<String> table)
	{
		ArrayList<User> tableData = new ArrayList<User>();
		
		for (int i = 0; i < table.size(); i++)
		{
			try
			{
				String line = table.get(i);
				String[] csvData = line.split(",");
				int userID = Integer.valueOf(csvData[0]);
				int educationLevelID = Integer.valueOf(csvData[2]);
				String userName = csvData[3];
				String phoneNumber = csvData[4];
				long userBirthdate = Long.valueOf(csvData[5]);
				long userTimestamp = Long.valueOf(csvData[6]);
				csvData = csvData[1].split(";");
				ArrayList<Integer> skillIDs = new ArrayList<Integer>();
				if (csvData.length > 0 && !csvData[0].equals(""))
				{
					for (int j = 0; j < csvData.length; j++)
					{
						skillIDs.add(Integer.valueOf(csvData[j]));
					}
				}
				User tempUser = new User();
				if (skillIDs.size() != 0)
					tempUser = new User(skillIDs.size());
				tempUser.userID = userID;
				tempUser.educationLevelID = educationLevelID;
				tempUser.userName = userName;
				tempUser.phoneNumber = phoneNumber;
				tempUser.userBirthdate = userBirthdate;
				tempUser.userTimestamp = userTimestamp;
				tempUser.addSkill(skillIDs);
				
				tableData.add(tempUser);
			} catch (Exception e)
			{
				log("WARNING: Invalid data. Skipping. Error: " + e.getMessage());
			}
			
		}
		
		
		return tableData;
	}
	
	public static ArrayList<Job> readJobTable(List<String> table)
	{
		ArrayList<Job> tableData = new ArrayList<Job>();
		for (int i = 0; i < table.size(); i++)
		{
			try
			{
				String[] csvData = table.get(i).split(",");
				int educationLevelID[] = {Integer.valueOf(csvData[1]), Integer.valueOf(csvData[3])};
				
				String[] csvData0 = csvData[0].split(";");
				String[] csvData1 = csvData[2].split(";");
				User idealUser = new User(csvData0.length);
				User minUser = new User(csvData1.length);
				ArrayList<Integer> skillIDs = new ArrayList<Integer>();
				if (csvData0.length > 0 && !csvData0[0].equals(""))
				{
					for (int j = 0; j < csvData0.length; j++)
					{
						skillIDs.add(Integer.valueOf(csvData0[j]));
					}
				}				
				idealUser.addSkill(skillIDs);
				
				skillIDs = new ArrayList<Integer>();
				if (csvData1.length > 0 && !csvData1[0].equals(""))
				{
					for (int j = 0; j < csvData1.length; j++)
					{
						skillIDs.add(Integer.valueOf(csvData1[j]));
					}
				}				
				minUser.addSkill(skillIDs);
				minUser.educationLevelID = educationLevelID[1];
				idealUser.educationLevelID = educationLevelID[0];		
				
				
				tableData.add(new Job(idealUser, minUser, csvData[4]));
			}
			catch (Exception e)
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
