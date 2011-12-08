import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CoursesPanel extends ContentPanel
{
	public JButton loginButton, coursesButton, calendarButton;
	public JButton editButton, helpButton, addCourse;
	public JButton[] eventsButtons, gradeButtons, discussButtons, removeButtons;
	JLabel[] postings;
	public ArrayList<Course> courses;
	boolean editing;

	public CoursesPanel()
	{
		setLayout(null);
		setOpaque(false);
		setSize(new Dimension(970, 680));

		/* Navigation buttons */
		loginButton = new JButton("Logout");
		loginButton.setBounds(150, 50, loginButton.getPreferredSize().width, loginButton.getPreferredSize().height);
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.LOGIN);
			}
		});
		add(loginButton);

		coursesButton = new JButton("Courses");
		coursesButton.setBounds(450, 50, coursesButton.getPreferredSize().width, coursesButton.getPreferredSize().height);
		coursesButton.setSelected(true);	// Already on the Courses page
		coursesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.COURSES);
			}
		});
		add(coursesButton);

		calendarButton = new JButton("Calendar");
		calendarButton.setBounds(750, 50, calendarButton.getPreferredSize().width, calendarButton.getPreferredSize().height);
		calendarButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.CALENDAR);
			}
		});
		add(calendarButton);

		editButton = new JButton("Edit Courses");
		editButton.setBounds(450, 600, editButton.getPreferredSize().width, editButton.getPreferredSize().height);
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				if (editButton.isSelected())
				{
					editing = false;
					editButton.setText("Edit Courses");
					editButton.setSelected(false);
					addCourse.setVisible(false);
					addCourse = null;
					addCoursePostings();
				}
				else
				{
					editing = true;
					editButton.setText("Finish Editing");
					editButton.setSelected(true);
					addCoursePostings();
				}
			}
		});
		add(editButton);

		helpButton = new JButton("Help!");
		helpButton.setBounds(850, 600, helpButton.getPreferredSize().width, helpButton.getPreferredSize().height);
		helpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				new Help();
			}
		});
		add(helpButton);

		/* Course Information */
		// Get the list of the student's courses, and make the courses array
		if (CourseNet.isStudent)
		{
			courses = CourseNet.myDb.viewStudentCourses(CourseNet.username);
		}
		else
		{
			courses = CourseNet.myDb.viewTeacherCourses(CourseNet.username);
		}
		
		addCoursePostings();
	}

	private void addCoursePostings()
	{
		// Removing any existing visible old postings
		if (postings != null)
		{
			for (JLabel old : postings)
			{
				old.setVisible(false);
			}
			if (addCourse != null) addCourse.setVisible(false);
		}

		int x = 85, y = 150;
		postings = new JLabel[courses.size()];
		eventsButtons = new JButton[courses.size()];
		gradeButtons = new JButton[courses.size()];
		discussButtons = new JButton[courses.size()];
		removeButtons = new JButton[courses.size()];
		PostingListener l = new PostingListener();
		for (int i = 0; i < courses.size(); i++)
		{
			postings[i] = new JLabel(new ImageIcon("../post_it.jpg"));
			postings[i].setBounds(x, y, 250, 200);
			add(postings[i]);

			// Basic Course Info
			Course c = courses.get(i);
			JLabel courseTitle = new JLabel(c.number + ": " + c.name);
			courseTitle.setFont(new Font("Arial Black", Font.PLAIN, 14));
			courseTitle.setBounds(10, 10, courseTitle.getPreferredSize().width, courseTitle.getPreferredSize().height);
			postings[i].add(courseTitle);
			JLabel prof = new JLabel(c.teacher);
			prof.setBounds(15, 30, prof.getPreferredSize().width, prof.getPreferredSize().height);
			postings[i].add(prof);
			JLabel time = new JLabel(c.time);
			time.setBounds(15, 45, time.getPreferredSize().width, time.getPreferredSize().height);
			postings[i].add(time);
			JLabel loc = new JLabel(c.location);
			loc.setBounds(15, 60, loc.getPreferredSize().width, loc.getPreferredSize().height);
			postings[i].add(loc);
			JTextArea desc = new JTextArea("  " + c.description);
			desc.setEditable(false);
			desc.setOpaque(false);
			desc.setLineWrap(true);
			desc.setWrapStyleWord(true);
			desc.setBounds(15, 80, 200, 75);
			postings[i].add(desc);

			if (!CourseNet.isStudent && (c.pendingAdd || c.pendingDelete))
			{
				JLabel pending = new JLabel("Pending administrative approval");
				pending.setForeground(Color.red);
				pending.setBounds(15, 70, pending.getPreferredSize().width, pending.getPreferredSize().height);
				postings[i].add(pending);
			}

			eventsButtons[i] = new JButton("Events");
			eventsButtons[i].setBounds(10, 150, 70, eventsButtons[i].getPreferredSize().height);
			eventsButtons[i].addActionListener(l);
			postings[i].add(eventsButtons[i]);

			gradeButtons[i] = new JButton("Grades");
			gradeButtons[i].setBounds(90, 150, 70, gradeButtons[i].getPreferredSize().height);
			gradeButtons[i].addActionListener(l);
			postings[i].add(gradeButtons[i]);

			discussButtons[i] = new JButton("Talk");
			discussButtons[i].setBounds(170, 150, 70, discussButtons[i].getPreferredSize().height);
			discussButtons[i].addActionListener(l);
			postings[i].add(discussButtons[i]);

			if (editing)
			{
				removeButtons[i] = new JButton("X");
				removeButtons[i].setBounds(200, 10, removeButtons[i].getPreferredSize().height, removeButtons[i].getPreferredSize().height);
				removeButtons[i].addActionListener(l);
				postings[i].add(removeButtons[i]);
			}

			// Increment location for next course
			x += 275;
			if (700 < x)
			{
				x = 85;
				y+=225;
			}
		}
		if ((courses.size() < 6)  && editing)
		{	
			if (addCourse != null) addCourse.setVisible(false);
			addCourse = new JButton("Add a Course");
			addCourse.setVisible(true);
			addCourse.setBounds(x+75, y+50, 150, 100);
			addCourse.addActionListener(l);
			add(addCourse);
		}
		repaint();
	}
	
	class PostingListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JButton button = (JButton)event.getSource();

			if (button.getText().equals("Events"))
			{
				for (int i=0; i < eventsButtons.length; i++)
				{
					if (eventsButtons[i].equals(button))
					{
						new Assignments(courses.get(i));
						break;
					}
				}
			}
			if (button.getText().equals("Grades"))
			{
				for (int i=0; i < gradeButtons.length; i++)
				{
					if (gradeButtons[i].equals(button))
					{
						new Grades(courses.get(i));
						break;
					}
				}
			}
			if (button.getText().equals("Talk"))
			{
				for (int i=0; i < discussButtons.length; i++)
				{
					if (discussButtons[i].equals(button))
					{
						new Discussion(courses.get(i));
						break;
					}
				}
			}
			if (button.getText().equals("X"))
			{
				for (int i=0; i < removeButtons.length; i++)
				{
					if (removeButtons[i].equals(button))
					{
						// if student, dropCourse
						if (CourseNet.isStudent)
						{
							//System.out.println(courses.get(i).name);
							CourseNet.myDb.dropCourse(courses.get(i), CourseNet.username);
						}
						// if teacher, deleteCourse
						else
						{
							//System.out.println(courses.get(i).name);
							CourseNet.myDb.deleteCourse(courses.get(i));
						}
						courses.remove(i);
						courses.trimToSize();
						addCoursePostings();
						break;
					}
				}
			}
			if (button.getText().equals("Add a Course"))
			{
				Course c;
				
				if (CourseNet.isStudent)
				{
					ArrayList<Course> courseList = new ArrayList<Course>();
					
					// pick a new course
					courseList = CourseNet.myDb.listOpenCourses();

					// display them and get the student's choice
					c = (Course) JOptionPane.showInputDialog(null, "Choose a course to add:",
					        "Add Course", JOptionPane.QUESTION_MESSAGE, null, courseList.toArray(), null);
					
					CourseNet.myDb.addCourse(c, CourseNet.username);
				}
				else
				{
					c = new Course();
					c.name = JOptionPane.showInputDialog("Enter the name of your new course");
					c.number = JOptionPane.showInputDialog("Enter the course number");
					c.description = JOptionPane.showInputDialog("Enter course description");
					c.location = JOptionPane.showInputDialog("Enter location");
					c.time = JOptionPane.showInputDialog("Enter the class time");
					CourseNet.myDb.createCourse(c, CourseNet.username);
				}
				
				courses.add(c);
				addCoursePostings();
			}
		}
	}
}