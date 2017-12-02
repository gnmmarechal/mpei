import java.util.*;

public class User
{
	// Fields
	int userID;
	List<Integer> skillIDs = new ArrayList<Integer>();
	BloomFilter skillIDFilter; // The skills' IDs are stored using both the List and the BloomFilter, and either can be used.
	int educationLevelID;
	String userName;
	long userBirthdate; // In seconds
	long userTimestamp;
	String phoneNumber;
	
	// Other Variables
	private static int k = 10; // Hash functions to use (3). The formula k = m/n * ln(2) tells us that the optimal k is 7.
	
	public User()
	{
		this(Misc.getSkillSize()); // Ideal BloomFilter size -> 10k, where k is the number of elements. 
		// k is set to the maximum value (provided by the Misc class) when the real size is not given as an argument.
		// User(int size) should be used instead in most scenarios.
	}
	
	public User(int elementNumber)
	{
		skillIDFilter = new BloomFilter(elementNumber*10);
	}
	
	public User(int userID, ArrayList<Integer> skillIDs, int educationLevelID, String userName, long userBirthdate, long userTimestamp, String phoneNumber)
	{
		this(skillIDs.size());
		this.userID = userID;
		this.skillIDs = skillIDs;

		for (int i = 0; i < skillIDs.size(); i++)
		{
			addSkill(skillIDs.get(i));
		}
		
		this.educationLevelID = educationLevelID;
		this.userName = userName;
		this.userBirthdate = userBirthdate;
		this.userTimestamp = userTimestamp;
		this.phoneNumber = phoneNumber;
	}
	
	public boolean hasSkill(int skillID)
	{
		return skillIDFilter.existsMember(k, skillID);
	}
	
	public boolean hasSkill(int[] skillIDArray)
	{
		for (int skill : skillIDArray)
		{
			if (!hasSkill(skill))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean hasSkill(List<Integer> skillIDList)
	{
		int[] tempArray = new int[skillIDList.size()];
		tempArray = skillIDList.stream().mapToInt(i->i).toArray();
		return hasSkill(tempArray);
	}

	public boolean hasSkillExact(int skillID)
	{
		return skillIDs.contains(skillID);
	}
		
	public boolean hasSkillExact(int[] skillIDArray)
	{
		for (int skill : skillIDArray)
		{
			if (!hasSkillExact(skill))
				return false;
		}
		return true;
	}	
	
	public boolean hasSkillExact(List<Integer> skillIDList)
	{
		int[] tempArray = new int[skillIDList.size()];
		tempArray = skillIDList.stream().mapToInt(i->i).toArray();
		return hasSkillExact(tempArray);		
	}
	
	public void addSkill(int skillID)
	{
		skillIDs.add(skillID);
		skillIDFilter.addMember(k, skillID);
	}
	
	public void addSkill(int[] skillIDArray)
	{
		for (int skill : skillIDArray)
		{
			addSkill(skill);
		}
	}
	
	public void addSkill(List<Integer> skillIDList)
	{
		int[] tempArray = new int[skillIDList.size()];
		tempArray = skillIDList.stream().mapToInt(i->i).toArray();
		addSkill(tempArray);
	}
	
	public void addSkill(String[] skillNameList,String skillName)
	{
		for (int i = 0; i < skillNameList.length; i++)
		{
			if (skillNameList[i].equals(skillName))
			{
				addSkill(i+1);
			}
		}
		
	}
	
	public void addSkill(String[] skillNameList, String[] skillNames)
	{
		for (int i = 0; i < skillNames.length; i++)
		{
			addSkill(skillNameList, skillNames[i]);
		}
		
	}
	
	public String[] getSkills(String[] skillNameList)
	{
		String[] tempArray = new String[skillIDs.size()];
		for (int i = 0; i < skillIDs.size(); i++)
		{
			tempArray[i] = skillNameList[skillIDs.get(i)-1];
		}
		return tempArray;
		
	}
	
	public List<Integer> getSkills()
	{
		return skillIDs;
	}
	
	public int[] getSkillsArray()
	{
		int[] tempArray = new int[skillIDs.size()];
		tempArray = skillIDs.stream().mapToInt(i->i).toArray();
		return tempArray;
	}
	
	public String toString()
	{
		String skillIDString = "";
		for (int i = 0; i < skillIDs.size(); i++)
		{
			skillIDString += skillIDs.get(i);
			if (i < skillIDs.size() - 1)
				skillIDString += ";";
		}		
		return new String(userID + "," + skillIDString + "," + educationLevelID + "," + userName + "," + phoneNumber + "," + userBirthdate + "," + userTimestamp);
	}

}
