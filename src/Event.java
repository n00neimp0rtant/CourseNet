
public class Event implements Comparable<Event>
{
	public String date, title, description, timeStamp;

	
	public int compareTo(Event e)
	{
		return date.compareTo(e.date);
	}
	
}
