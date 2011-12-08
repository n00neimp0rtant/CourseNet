import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;



public class AdminPanel extends ContentPanel
{
	public JButton loginButton, approveAdd, approveDelete, addStudent, addProf;
	public JLabel adminLabel;

	public AdminPanel()
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
		
		approveAdd = new JButton("Approve Added Course");
		approveAdd.setBounds(100, 150, 300, 200);
		approveAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<Course> courseList = CourseNet.myDb.listOpenCourses();

				// display them and get the choice
				Course c = (Course) JOptionPane.showInputDialog(null, "Choose a course to approve:",
				        "See Course Details", JOptionPane.QUESTION_MESSAGE, null, courseList.toArray(), null);
				
				String info = c.number + " "  + c.name + "\n" + c.teacher + "\n" + c.time + "\n" + c.location + "\n" + c.description;
				
				int action = JOptionPane.showConfirmDialog(null, info, "Approve Course?", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (action == JOptionPane.YES_OPTION)
				{
					System.out.println("APPROVAL");
					//CourseNet.myDb.approveAddCourse(c, CourseNet.username);
				}
				else if(action == JOptionPane.NO_OPTION)
				{
					System.out.println("The course does not exist at all");
				}
			}
		});
		add(approveAdd);
		
		approveDelete = new JButton("Approve Course Deletion");
		approveDelete.setBounds(440, 150, 300, 200);
		approveDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<Course> courseList = CourseNet.myDb.listOpenCourses();

				// display them and get the choice
				Course c = (Course) JOptionPane.showInputDialog(null, "Choose a course to allow deletion:",
				        "See Course Details", JOptionPane.QUESTION_MESSAGE, null, courseList.toArray(), null);
				
				String info = c.number + " "  + c.name + "\n" + c.teacher + "\n" + c.time + "\n" + c.location + "\n" + c.description;
				
				int action = JOptionPane.showConfirmDialog(null, info, "Approve Deletion?", JOptionPane.YES_NO_CANCEL_OPTION);
				if (action == JOptionPane.YES_OPTION)
				{
					System.out.println("DELETED");
					//CourseNet.myDb.approveAddCourse(c, CourseNet.username);
				}
				else if(action == JOptionPane.NO_OPTION)
				{
					System.out.println("The course goes back to active status");
				}
			}
		});
		add(approveDelete);
	}
}
