import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CoursesPanel extends ContentPanel
{
	public JButton loginButton, coursesButton, calendarButton, editButton, helpButton, addCourse;
	public JButton[] assigButtons, gradeButtons, discussButtons, removeButtons;
	JLabel[] postings;
	public ArrayList<CourseInfo> courses;
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
		courses = new ArrayList<CourseInfo>();
		for (int i = 0; i < 4; i++)
		{
			courses.add(i, new CourseInfo());
			courses.get(i).className = "Course Name";
			courses.get(i).profName = "John Smith";
			courses.get(i).meetTime = "MWF 9 - 9:50";
		}
		// Fix the above.

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
		assigButtons = new JButton[courses.size()];
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
			JLabel courseTitle = new JLabel(courses.get(i).className);
			courseTitle.setFont(new Font("Arial Black", Font.PLAIN, 14));
			courseTitle.setBounds(10, 10, courseTitle.getPreferredSize().width, courseTitle.getPreferredSize().height);
			postings[i].add(courseTitle);
			JLabel prof = new JLabel(courses.get(i).profName);
			prof.setBounds(15, 30, prof.getPreferredSize().width, prof.getPreferredSize().height);
			postings[i].add(prof);
			JLabel time = new JLabel(courses.get(i).meetTime);
			time.setBounds(15, 50, time.getPreferredSize().width, time.getPreferredSize().height);
			postings[i].add(time);

			assigButtons[i] = new JButton("Assignments");
			assigButtons[i].setBounds(10, 150, 70, assigButtons[i].getPreferredSize().height);
			assigButtons[i].addActionListener(l);
			postings[i].add(assigButtons[i]);

			gradeButtons[i] = new JButton("Grades");
			gradeButtons[i].setBounds(90, 150, 70, gradeButtons[i].getPreferredSize().height);
			gradeButtons[i].addActionListener(l);
			postings[i].add(gradeButtons[i]);

			discussButtons[i] = new JButton("Discussion");
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
			addCourse = new JButton("Add a Course");
			addCourse.setBounds(x+75, y+50, 150, 100);
			addCourse.addActionListener(l);
			add(addCourse);
		}
	}
	
	class PostingListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JButton button = (JButton)event.getSource();

			if (button.getText().equals("Assignments"))
			{
				for (int i=0; i < assigButtons.length; i++)
				{
					if (assigButtons[i].equals(button))
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
			if (button.getText().equals("Discussion"))
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
						if (CourseNet.isStudent)
						{
							// Remove students from course
						}
						else	// Professor
						{
							// Remove course
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
				int i = courses.size();
				courses.add(i, new CourseInfo());
				courses.get(i).className = "New Class";
				courses.get(i).profName = "This will have to do a lot more stuff";
				courses.get(i).meetTime = "in order to actually add a course";
				addCoursePostings();
			}
		}
	}
}