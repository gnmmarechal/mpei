import java.io.*;
import java.util.*;
import glib.utils.Lists;
import java.util.stream.Collectors;
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
		
		List<Integer> ogUserNo = new ArrayList<Integer>();
		for (Job job : jobList)
		{
			List<User> userList = new ArrayList<User>();
			
			for (User u : userListFinal)
			{
				userList.add(u);
			}
			ogUserNo.add(userList.size());
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

				
			List<List<User>> tempList = perfectMatches.stream()
				.collect(Collectors.groupingBy(x -> x.educationLevelID))
				.entrySet().stream()
				.map(e -> { List<User> c = new ArrayList<User>(); c.addAll(e.getValue()); return c; })
				.collect(Collectors.toList());
			tempList.sort((o1, o2) -> new Integer(o2.get(0).educationLevelID).compareTo(new Integer(o1.get(0).educationLevelID)));
			perfectMatches = 
				tempList.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());	
			tempList = perfectMatches.stream()
				.collect(Collectors.groupingBy(x -> x.skillIDs.size()))
				.entrySet().stream()
				.map(e -> { List<User> c = new ArrayList<User>(); c.addAll(e.getValue()); return c; })
				.collect(Collectors.toList());
			tempList.sort((o1, o2) -> new Integer(o2.get(0).skillIDs.size()).compareTo(new Integer(o1.get(0).skillIDs.size())));
			perfectMatches = 
				tempList.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());					
			for (User user : perfectMatches)
			{
				log("Found perfect match for " + job.getTitle() + " :\n User ID: " + user.userID + "\n Name: " + user.userName + "\n Education Level: " + user.educationLevelID + "\n Skill Number: " + user.skillIDs.size() + "\n Skills: " + Arrays.toString(user.getSkills(skillListArray))); 
			}			
			perfectJobMatchList.add(perfectMatches);
			// log("Min Candidates: " + minimumMatchCandidates.size() + "\nMinimum Correct: " + minimumMatches.size() + "\nPerfect Candidates: " + perfectMatchCandidates.size() + "\nPerfects: " + perfectMatches.size());
			// log("Min False Positive Chance: " + String.valueOf(1.0 - (double) minimumMatches.size()/minimumMatchCandidates.size()) + "\nPerfect False Positive Chance: " + String.valueOf(1.0 - (double) perfectMatches.size()/perfectMatchCandidates.size()));
			
			log("Minimum Match Candidates: " + minimumMatchCandidates.size());
			log("Minimum Matches Found: " + minimumMatches.size());
			log("Perfect Match Candidates: " + perfectMatchCandidates.size());
			log("Perfect Matches found: " + perfectMatches.size());
			
			// Similarity Search
			
			log("=====Similar Match Search=====");
			
			// Compares the similarity between the ideal user and the skill intersection of the ideal user and the real one, then sorts by similarity and then by number of skills.
			userList.removeAll(perfectMatches);
			userList.removeAll(Lists.getIntersection(userList, minimumMatches));
			
			
			if (userList.size() != 0)
			{
				List<List<Integer>> skillsDocList = new ArrayList<List<Integer>>();
				
				for (User user : userList)
				{
					List<Integer> newDoc = new ArrayList<Integer>(user.getSkills());
					skillsDocList.add(newDoc);
				}				
				int signatureSize = 0; 
	
				for (List<Integer> skills : skillsDocList)
				{
					signatureSize += skills.size();
				}
				signatureSize += job.idealUser.skillIDs.size();
				signatureSize = signatureSize/skillsDocList.size() - 1; // Signature size is the average skill number -1

				if (signatureSize < 1)
					signatureSize = 1;
				
				log("Using signature size: " + signatureSize);
				int[][] sigList = new int[skillsDocList.size()][signatureSize];
				
				
				for (int i = 0; i < skillsDocList.size(); i++) // Gets the signature of the intersection between the ideal user and each user
				{
					sigList[i] = MinHash2.signature(signatureSize, userList.size(), Lists.getIntersection(skillsDocList.get(i), job.idealUser.skillIDs));
					
				}
				
				// Get signature for the ideal user
				int[] idealSignature = MinHash2.signature(signatureSize, userList.size(), job.idealUser.skillIDs);
				
				// Compute similarity for every use
				for (int i = 0; i < sigList.length; i++)
				{
					userList.get(i).similarityIndex = Jaccard.getSimilarity(idealSignature, sigList[i]);
				}
				// Sort by similarity index, number of skills and then education level
				// List<List<User>> tempList = new ArrayList<List<User>>();


				tempList = userList.stream()
					.collect(Collectors.groupingBy(x -> x.educationLevelID))
					.entrySet().stream()
					.map(e -> { List<User> c = new ArrayList<User>(); c.addAll(e.getValue()); return c; })
					.collect(Collectors.toList());
				tempList.sort((o1, o2) -> new Integer(o2.get(0).educationLevelID).compareTo(new Integer(o1.get(0).educationLevelID)));
				userList = 
					tempList.stream()
					.flatMap(List::stream)
					.collect(Collectors.toList());
				
				tempList = userList.stream()
					.collect(Collectors.groupingBy(x -> x.skillIDs.size()))
					.entrySet().stream()
					.map(e -> { List<User> c = new ArrayList<User>(); c.addAll(e.getValue()); return c; })
					.collect(Collectors.toList());
				tempList.sort((o1, o2) -> new Integer(o2.get(0).skillIDs.size()).compareTo(new Integer(o1.get(0).skillIDs.size())));
				userList = 
					tempList.stream()
					.flatMap(List::stream)
					.collect(Collectors.toList());				

				tempList = userList.stream()
					.collect(Collectors.groupingBy(x -> x.similarityIndex))
					.entrySet().stream()
					.map(e -> { List<User> c = new ArrayList<User>(); c.addAll(e.getValue()); return c; })
					.collect(Collectors.toList());
				tempList.sort((o1, o2) -> new Double(o2.get(0).similarityIndex).compareTo(new Double(o1.get(0).similarityIndex)));
				userList = 
					tempList.stream()
					.flatMap(List::stream)
					.collect(Collectors.toList());					
				
				for (User u : userList)
				{
					log("User data for job " + job.getTitle() + " :\n User ID: " + u.userID + "\n Name: " + u.userName + "\n Education Level: " + u.educationLevelID + "\n Skill Number: " + u.skillIDs.size() + "\n Skills: " + Arrays.toString(u.getSkills(skillListArray)) + "\n Similarity Index: " + u.similarityIndex); 
				}
				
				log("Current user list size: " + userList.size());
				
				// Remove people with similarity below minSimilarity
				List<User> prepList = new ArrayList<User>();
				
				for (User u : userList)
				{
					if (u.similarityIndex >= job.minSimIndex)
						prepList.add(u);
				}
				
				userList = new ArrayList<User>();
				
				for (User u : prepList)
					userList.add(u);
				log("Removed users with similarity index below " + job.minSimIndex + ".\nFinal user list size: " + userList.size());
				
			}
			else
			{
				log("Empty List!");
			}
			similarityOrderedMatchList.add(userList);
		}
		
		// Write to file
		for (int i = 0; i < jobList.size(); i++)
		{
			Job job = jobList.get(i);
			log("=====Printing Data to CSV File=====");
			log("Original User List Size: " + ogUserNo.get(i));
			int pSize = perfectJobMatchList.get(i).size();
			int rSize = similarityOrderedMatchList.get(i).size();
			double rPercent = 0;
			double pPercent = 0;
			if (pSize != 0)
				pPercent = 100 * (double) pSize/ (double) ogUserNo.get(i);
			if (rSize != 0)
				rPercent = 100 * (double) (rSize + pSize)/ (double) ogUserNo.get(i);
			log("Relevant Users: " + String.valueOf(rSize+pSize) + " (" + rPercent + "%)\nPerfect Users: " + pSize + " (" + pPercent + "%)");
			String jobFileNameA = job.getTitle() + "_perfect.csv";
			File jobFileA = new File(jobFileNameA);
			String jobFileNameB = job.getTitle() + ".csv";
			File jobFileB = new File(jobFileNameB);
			log("File Name: " + jobFileNameA);
			try
			{
				List<String> toWriteA = new ArrayList<String>();
				toWriteA.add("ID,NAME,SKILL NAMES, EDUCATION LEVEL, AGE, PHONE NUMBER");
				double pSkillNo = 0;
				for (User u : perfectJobMatchList.get(i))
				{
					pSkillNo += u.skillIDs.size();
					Long time= System.currentTimeMillis() / 1000 - u.userBirthdate;
					int years = Math.round(time) / 31536000;
					String[] skillArr = u.getSkills(skillListArray);
					String sk = "";
					for (int j = 0; j < u.skillIDs.size(); j++)
					{
						sk += skillArr[j];
						if (j < skillArr.length - 1)
							sk+= ";";
					}
					
					String edLev = "";
					switch(u.educationLevelID)
					{
						case 0:
							edLev = "None";
							break;
						case 1:
							edLev = "Primary School";
							break;
						case 2:
							edLev = "High School";
							break;
						case 3:
							edLev = "Bachelor's Degree";
							break;
						case 4:
							edLev = "Master's Degree";
							break;
						case 5:
							edLev = "Doctorate";
							break;
					}
					toWriteA.add(u.userID + "," + u.userName + "," + sk + "," + edLev + "," + years + "," + u.phoneNumber);
				}
				if (pSkillNo != 0)
				{
					pSkillNo = (double) pSkillNo / (double) perfectJobMatchList.get(i).size();
				}
				List<String> toWriteAA = new ArrayList<String>();
				toWriteAA.add("Average Skill Number:," + pSkillNo + ",Job:," + job.getTitle());
				printFile(jobFileA, toWriteAA);
				addToFile(jobFileA, toWriteA);
					
			} catch (Exception e)
			{
				log("Error printing data! : " + e.getMessage());
			}
			log("File Name: " + jobFileNameB);			
			try
			{
				List<String> toWriteB = new ArrayList<String>();
				toWriteB.add("ID,NAME,SKILL NAMES, EDUCATION LEVEL, AGE, PHONE NUMBER");
				double pSkillNo = 0;
				for (User u : similarityOrderedMatchList.get(i))
				{
					pSkillNo += u.skillIDs.size();
					Long time= System.currentTimeMillis() / 1000 - u.userBirthdate;
					int years = Math.round(time) / 31536000;
					String[] skillArr = u.getSkills(skillListArray);
					String sk = "";
					for (int j = 0; j < u.skillIDs.size(); j++)
					{
						sk += skillArr[j];
						if (j < skillArr.length - 1)
							sk+= ";";
					}
					
					String edLev = "";
					switch(u.educationLevelID)
					{
						case 0:
							edLev = "None";
							break;
						case 1:
							edLev = "Primary School";
							break;
						case 2:
							edLev = "High School";
							break;
						case 3:
							edLev = "Bachelor's Degree";
							break;
						case 4:
							edLev = "Master's Degree";
							break;
						case 5:
							edLev = "Doctorate";
							break;
					}
					toWriteB.add(u.userID + "," + u.userName + "," + sk + "," + edLev + "," + years + "," + u.phoneNumber);
				}
				if (pSkillNo != 0)
				{
					pSkillNo = (double) pSkillNo / (double) similarityOrderedMatchList.get(i).size();
				}
				List<String> toWriteBB = new ArrayList<String>();
				toWriteBB.add("Average Skill Number:," + pSkillNo + ",Job:," + job.getTitle());
				printFile(jobFileB, toWriteBB);
				addToFile(jobFileB, toWriteB);
					
			} catch (Exception e)
			{
				log("Error printing data! : " + e.getMessage());
			}			
		}
		log("=====DONE=====");
		System.in.read();
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
				
				
				tableData.add(new Job(idealUser, minUser, csvData[4], Double.valueOf(csvData[5])));
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
