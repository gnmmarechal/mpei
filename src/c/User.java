import java.util.*;

public class User
{
	// Fields
	int userID;
	List<Integer> skillIDs = new ArrayList<Integer>();
	int educationLevelID;
	String userName;
	int userBirthdate;
	long userTimestamp;
	
	public User()
	{
	}
	
	public User(int userID, ArrayList<Integer> skillIDs, int educationLevelID, String userName, int userBirthdate, long userTimestamp)
	{
		this.userID = userID;
		this.skillIDs = skillIDs;
		this.educationLevelID = educationLevelID;
		this.userName = userName;
		this.userBirthdate = userBirthdate;
		this.userTimestamp = userTimestamp;
	}
	

}
