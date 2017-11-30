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
	
	// Other Variables
	private static int k = 3; // Hash functions to use
	
	public User()
	{
		this(Misc.getSkillSize()); // Ideal BloomFilter size -> 10k, where k is the number of elements. 
		// k is set to the maximum value (provided by the Misc class) when the real size is not given as an argument.
		// User(int size) should be used instead in most scenarios.
	}
	
	public User(int elementNumber)
	{
		skillIDFilter = new BloomFilter(elementNumber*100);
	}
	
	public User(int skillIDSize, int userID, ArrayList<Integer> skillIDs, int educationLevelID, String userName, int userBirthdate, long userTimestamp)
	{
		this.userID = userID;
		this.skillIDs = skillIDs;
		this.skillIDFilter = new BloomFilter(skillIDSize*10);
		for (int i = 0; i < skillIDs.size(); i++)
		{
			skillIDFilter.addMember(k, skillIDs.get(i));
		}
		
		this.educationLevelID = educationLevelID;
		this.userName = userName;
		this.userBirthdate = userBirthdate;
		this.userTimestamp = userTimestamp;
	}
	
	public boolean hasSkill(int skillID)
	{
		return skillIDFilter.existsMember(k, skillID);
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

}
