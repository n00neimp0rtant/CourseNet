
public class Course {

	public String name;
	public String number;
	public String teacher;
	public String description;
	public String location;
	public String time;
	public String[] assignments;
	
	// blank constructor
	public Course()
	{
		name = null;
		number = null;
		description = null;
		location = null;
		time = null;
		teacher = null;
	}
	
	// constructor with data
	public Course(String num, String na, String descript, String loc, String t)
	{
		name = na;
		number = num;
		description = descript;
		location = loc;
		time = t;
	}
	
	public String toString()
	{
		return number;
	}
}
