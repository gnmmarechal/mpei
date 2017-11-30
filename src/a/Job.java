public class Job
{
	public User idealUser;
	private String jobTitle;
	public User minUser;
	
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
	
	public String getTitle()
	{
		return jobTitle;
	}
	
	public void setTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}
}
