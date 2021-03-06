public class Job
{
	public User idealUser;
	private String jobTitle;
	public User minUser;
	public double minSimIndex = 0.0;
	
	public Job()
	{
		this.idealUser = new User();
		this.minUser = new User();
	}
	public Job(User idealUser)
	{
		this.idealUser = idealUser;
		this.minUser = new User();
	}
	
	public Job(User idealUser, String jobTitle)
	{
		this(idealUser);
		this.jobTitle = jobTitle;
	}
	
	public Job(User idealUser, User minUser)
	{
		this.idealUser = idealUser;
		this.minUser = minUser;
	}
	public Job(User idealUser, User minUser, String jobTitle)
	{
		this(idealUser, minUser);
		this.jobTitle = jobTitle;
	}
	
	public Job(User idealUser, User minUser, String jobTitle, double minSimIndex)
	{
		this(idealUser, minUser, jobTitle);
		this.minSimIndex = minSimIndex;
	}
	public String getTitle()
	{
		return jobTitle;
	}
	
	public void setTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}
}
