/* myDatabase.java -- wrapper for database queries -- CourseNet */
/* Created by Jacob Lightner */

// imports
import java.sql.*;
import java.util.*;

public class myDatabase 
{	
	// global variables
	Connection conn = null;
	Statement s;
	ResultSet rs;
	
	String query;
	StringBuilder sb;
	
	// constructor
	public myDatabase()
	{
		
	}
	
	// connect method
	public void connect(String url)
	{
		// connection details
		String username = "root";
		String password = null;
		String driverName = "com.mysql.jdbc.Driver";
		
		try
        {
            Class.forName (driverName).newInstance();
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e)
        {
            System.err.println("Cannot connect to database server.");
        }
	}
	
	// drop tables method
	public void dropTables() 
	{
		try {
			// connect
			s = conn.createStatement();
		
			// drop all tables
			s.executeUpdate("drop table if exists Students");
			s.executeUpdate("drop table if exists Teachers");
			s.executeUpdate("drop table if exists Courses");
			s.executeUpdate("drop table if exists Enrollment");
			s.executeUpdate("drop table if exists Discussion");
			s.executeUpdate("drop table if exists Documents");
			s.executeUpdate("drop table if exists Events");
		} catch (SQLException e) {}
	}
	
	// create tables method
	// still need discussion and calendar
	public void createTables()
	{
		try {
			// connect
			s = conn.createStatement();
		 
			// students
			s.executeUpdate("create table Students (id int unsigned not null auto_increment PRIMARY KEY, "
                + "name char(40), username char(40), password char(40), email char(40))");
			// teachers
			s.executeUpdate("create table Teachers (id int unsigned not null auto_increment PRIMARY KEY, "
                + "name char(40), username char(40), password char(40), email char(40))");
			// courses
			s.executeUpdate("create table Courses (id int unsigned not null auto_increment PRIMARY KEY, "
        		+ "number char(10), name char(40), description char(100), location char(40), time char(40), "
        		+ " assignments char(100), status char(10))");
			// enrollment
			s.executeUpdate("create table Enrollment (id int unsigned not null auto_increment PRIMARY KEY, "
				+ "course_name char(40), course_teacher char(40), roster char(200))");
			// discussion
			s.executeUpdate("create table Discussion (id int unsigned not null auto_increment PRIMARY KEY, "
					+ "course_name char(40), user char(40), text char(100))");
			// documents
			s.executeUpdate("create table Documents (id int unsigned not null auto_increment PRIMARY KEY, "
					+ "doc_name char(40), course_name char(40))");
			// events
			s.executeUpdate("create table Events (id int unsigned not null auto_increment PRIMARY KEY, " +
					"username char(40), title char(40), description char(100), date char(40))");
			
		} catch (SQLException e) {System.out.println("Creation failed: " + e);}
	}
	
	// send a query to the database
	// query method
	// is this really necessary???
	public void sendQuery(String query) 
	{
		try {
			// connect
			s = conn.createStatement();
		
			// send query
			s.executeUpdate(query);
		} catch (SQLException e) {}
	}
	
	// check login password
	// returns true if password correct
	// check password
	// return boolean based on comparison
	public boolean checkPassword(String username, String password)
	{	
		try {
			s = conn.createStatement();	
			
			// check teachers table
			rs = s.executeQuery("select * from Teachers where username='" + username + "'");
			if (rs.next())
			{
				rs.first();
				if (password.equals(rs.getString("password")))
					return true;
			}
		
			// check students table
			rs = s.executeQuery("select * from Students where username='" + username + "'");
			if (rs.next())
			{
				rs.first();
				if (password.equals(rs.getString("password")))
					return true;
			}
		
			return false;
			} catch (SQLException e) {return false;}
	}
	
	// search for a course
	// takes in array of keywords from search bar
	// search courses
	// takes in searched keywords
	// returns array of courses
	public ArrayList<Course> listOpenCourses()
	{
		ArrayList<Course> allCourses = new ArrayList<Course>();
		int numCourses = 0;
		Course temp;
		try {
			s = conn.createStatement();
			
			query = "select * from Courses";
			rs = s.executeQuery(query);
			
			//rs.first();
			while (rs.next())
			{
				if (rs.getString("status").equals("Open"))
				{
					temp = new Course();
					temp.name = rs.getString("name");
					temp.number = rs.getString("number");
					temp.description = rs.getString("description");
					temp.location = rs.getString("location");
					temp.time = rs.getString("time");
					allCourses.add(temp);
					numCourses++;
				}
			}
			
			for (int i = 0; i < allCourses.size(); i++)
			{
				query = "select * from Enrollment where course_name='" + allCourses.get(i).name + "'";
				rs = s.executeQuery(query);
				rs.first();
				allCourses.get(i).teacher = rs.getString("course_teacher");
			}
			
			return allCourses;
			
		} catch (SQLException e) {System.out.println("Course listing failed: " + e); return null;}
	}
	
	// create a new course
	// create course 
	public void createCourse(Course c, String username)
	{
		
		try {
			s = conn.createStatement();
			
			// update course table
			query = "insert into Courses (number, name, description, location, time, status) " +
					"values ('" + c.number + "', '" + c.name + "', '" + c.description + "', '" + c.location + 
							"', '" + c.time + "', '" + "Open" + "')";
			s.executeUpdate(query);
			
			// update enrollment table
			query = "insert into Enrollment (course_name, course_teacher, roster) values ('" + c.name + "', '" +
						username + "', '')";
			s.executeUpdate(query);
			
		} catch (SQLException e) {System.out.println("Course creation failed:" + e);}
	}

	// change a course's status
	// change a courses status
	public void changeCourseStatus(Course c)
	{
		try {
			s = conn.createStatement();

			query = "select * from Courses where name='" + c.name + "'";
			rs = s.executeQuery(query);
			
			rs.first();
			if (rs.getString("status").equals("Open"))
			{
				s.executeUpdate("update Courses set status='Closed' where name='" + c.name + "'");
			}
			else
			{
				s.executeUpdate("update Courses set status='Open' where name='" + c.name + "'");
			}
			
		} catch (SQLException e) {System.out.println("Course status change failed: " + e);}
	}
	
	// delete a course
	// delete a course
	public void deleteCourse(Course c)
	{
		try {
			s = conn.createStatement();
			
			// delete from course table
			query = "delete from Courses where name='" + c.name + "'";
			s.executeUpdate(query);
			
			// delete from enrollment table
			query = "delete from Enrollment where course_name='" + c.name + "'";
			s.executeUpdate(query);
			
		} catch (SQLException e) {System.out.println("Course deletion failed: " + e);}
	}

	// add student to course
	// need to stipulate only for open courses
	// add student to course
	public void addCourse(Course c, String username)
	{
		try {
			s = conn.createStatement();
			
			query = "select * from Enrollment where course_name='" + c.name + "'";
			rs = s.executeQuery(query);
			rs.first();
			String list = rs.getString("roster");
			sb = new StringBuilder(list);
			sb.append(username + ",");
			s.executeUpdate("update Enrollment set roster='" + sb.toString() + "' where course_name='" 
					+ c.name + "'");
			
		} catch (SQLException e) {System.out.println("Class add failed: " + e);}
	}

	// drop student from course
	// drop student from course
	public void dropCourse(Course c, String username)
	{
		try {
			boolean found = false;
			s = conn.createStatement();
			
			query = "select * from Enrollment where course_name='" + c.name + "'";
			rs = s.executeQuery(query);
			rs.first();
			String list = rs.getString("roster");
			String[] names = list.split(",");
			for (int i = 0; i < names.length - 1; i++)
			{
				if (found)
					names[i] = names[i+1];
				
				if (names[i].equals(username))
				{
					names[i] = names[i+1];
					found = true;
				}
			}
			names[names.length-1] = "";
			
			sb = new StringBuilder(names[0] + ",");
			for (int i = 1; i < names.length-1; i++)
			{
				sb.append(names[i] + ",");
			}
			
			s.executeUpdate("update Enrollment set roster='" + sb.toString() + "' where course_name='"
					+ c.name + "'");
			
		} catch (SQLException e) {System.out.println("Course drop failed: " + e);}
	}

	// post document to course
	public void postDocument(Course c, String docName)
	{
		try {
			s = conn.createStatement();
			
			query = "insert into Documents (doc_name, course_name) values ('" + docName + "', '" + c.name + "')";
			s.executeUpdate(query);
		} catch (SQLException e) {System.out.println("Document posting failed: " + e);}
	}

	// send email to students 
	// need to figure out email procedure
	// only displays right now
	public void emailStudents(Course c, String message)
	{
		try {
			s = conn.createStatement();
			
			query = "select * from Enrollment where course_name='" + c.name + "'";
			rs = s.executeQuery(query);
			
			rs.first();
			String line = rs.getString("roster");
			String[] names = line.split(",");
			String[] emails = new String[names.length];
			for (int i = 0; i < names.length; i++)
			{
				query = "select * from Students where username='" + names[i] + "'";
				rs = s.executeQuery(query);
				rs.first();
				emails[i] = rs.getString("email");
			}
			
			for (int i = 0; i < emails.length; i++)
			{
				// Figure out actual email procedure
				System.out.println("Sending to " + emails[i]);
				System.out.println(message);
				System.out.println("------");
			}
			
		} catch (SQLException e) {System.out.println("Email failed: " + e);}
	}

	// show course roster
	// returns array of names
	public String[] viewRoster(Course c)
	{
		try {
			s = conn.createStatement();
			
			query = "select * from Enrollment where course_name='" + c.name + "'";
			rs = s.executeQuery(query);
			
			rs.first();
			String line = rs.getString("roster");
			String[] usernames = line.split(",");
			
			String[] names = new String[usernames.length];
			for (int i = 0; i < usernames.length; i++)
			{
				query = "select * from Students where username='" + usernames[i] + "'";
				rs = s.executeQuery(query);
				rs.first();
				names[i] = rs.getString("name");
			}
			
			return names;
		} catch (SQLException e) {System.out.println("Roster viewing failed: " + e); return null;}
	}
	
	// submit homework
	// just email it to teacher???
	public void submitHomework(Course c, String username, String docName)
	{
		
	}
		
	// return array of all students courses
	public ArrayList<Course> viewStudentCourses(String username)
	{
		int numCourses = 0;
		ArrayList<Course> myCourses = new ArrayList<Course>();
		ArrayList<String> courseNames = new ArrayList<String>();
		Course temp;
		String line;
		String[] names;
		
		try {
			s = conn.createStatement();
			
			query = "select * from Enrollment";
			rs = s.executeQuery(query);
			rs.first();
			while(rs.next())
			{
				line = rs.getString("roster");
				names = line.split(",");
				for (int i = 0; i < names.length; i++)
				{
					if (names[i].equals(username))
					{
						courseNames.add(numCourses, rs.getString("course_name"));
						numCourses++;
						i = 1000;
					}
				}
			}
			
			for (int i = 0; i < numCourses; i++)
			{
				query = "select * from Courses where name='" + courseNames.get(i) + "'";
				rs = s.executeQuery(query);
				rs.first();
				temp = new Course();
				temp.name = courseNames.get(i);
				temp.number = rs.getString("number");
				temp.description = rs.getString("description");
				temp.location = rs.getString("location");
				temp.time = rs.getString("time");
				//line = rs.getString("assignments");
				//if (line.length() > 0)
				//	temp.assignments = line.split(",");
				//else
				//	temp.assignments = null;
				myCourses.add(i, temp);
			}
			
			return myCourses;
			
		} catch (SQLException e) {System.out.println("Course view failed: " + e); return null;}
	}
	
	public ArrayList<Course> viewTeacherCourses(String username)
	{
		try {
			Course temp;
			ArrayList<Course> myCourses = new ArrayList<Course>();
			ArrayList<String> courseNames = new ArrayList<String>();
			String name;
			int numCourses = 0;
			s = conn.createStatement();
			
			query = "select * from Teachers where username='" + username + "'";
			rs = s.executeQuery(query);
			rs.first();
			name = rs.getString("name");
			
			query = "select * from Enrollment where course_teacher='" + username + "'";
			rs = s.executeQuery(query);
			
			rs.first();
			while (rs.next())
			{
				courseNames.add(numCourses, rs.getString("course_name"));
				numCourses++;
			}
			
			for (int i = 0; i < numCourses; i++)
			{
				query = "select * from Courses where name='" + courseNames.get(i) + "'";
				rs = s.executeQuery(query);
				rs.first();
				temp = new Course();
				temp.name = courseNames.get(i);
				temp.teacher = name;
				temp.number = rs.getString("number");
				temp.description = rs.getString("description");
				temp.location = rs.getString("location");
				temp.time = rs.getString("time");
				myCourses.add(i, temp);
			}
			
			return myCourses;
			
		} catch (SQLException e) {System.out.println("Course view failed: " + e); return null;}
	}
	
	// returns whether or not user is student
	public boolean isStudent(String username)
	{
		try {
			s = conn.createStatement();
			
			query = "select * from Students where username='" + username + "'";
			rs = s.executeQuery(query);
			
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {System.out.println("Student check failed: " + e); return false;}
	}
	
	// add a new event
	public void addEvent(Event e, String username)
	{
		try {
			s = conn.createStatement();
			
			query = "insert into Events (username, title, description, date) values " +
					"('" + username + "', '" + e.title + "', '" + e.description + "', '" + e.date + "')";
			s.executeUpdate(query);
			
		} catch (SQLException ex) {System.out.println("Event add failed: " + ex);}
	}
	
	// view all of users events
	public ArrayList<Event> viewEvents(String username)
	{
		ArrayList<Event> eventList = new ArrayList<Event>();
		Event temp;
		try {
			s = conn.createStatement();
			
			query = "select * from Events where username='" + username + "'";
			rs = s.executeQuery(query);
			
			while (rs.next())
			{
				temp = new Event();
				temp.title = rs.getString("title");
				temp.description = rs.getString("description");
				temp.date = rs.getString("date");
				eventList.add(temp);
			}
			
			return eventList;
			
		} catch (SQLException e) {System.out.println("View event failed: " + e); return null;}
	}
	
	// post a message to the discussion board
	public void postMessage(Course c, Message m)
	{
		try {
			s = conn.createStatement();
			
			query = "insert into Discussion (course_name, user, text) values ('" + c.name + "', '" +
					m.username + "', '" + m.text + "')";
			s.executeUpdate(query);
			
		} catch (SQLException e) {System.out.println("Post message failed: " + e);}
	}
	
	// return all messages for a given course
	public ArrayList<Message> viewMessages(Course c)
	{
		ArrayList<Message> messages = new ArrayList<Message>();
		Message temp;
		try {
			s = conn.createStatement();
			
			query = "select * from Discussion where course_name='" + c.name + "'";
			rs = s.executeQuery(query);
			
			//rs.first();
			while (rs.next())
			{
				temp = new Message();
				temp.text = rs.getString("text");
				temp.username = rs.getString("user");
				messages.add(temp);
			}
			
			return messages;
			
		} catch (SQLException e) {System.out.println("View message failure: " + e); return null;}
	}
}


