import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Grades extends JFrame
{
	Grades(Course course)
	{
		super("Grades");
		
		String currGrade;
		
		if (CourseNet.isStudent)
		{
			JTextArea grade = new JTextArea();
			grade.append(CourseNet.myDb.viewGrade(CourseNet.username, course));
			grade.setEditable(false);
			JScrollPane scroll = new JScrollPane(grade);
			scroll.setPreferredSize(new Dimension(300, 400));
			add(grade);
		}
		else
		{
			// Get the student (user?)names from the database
			String[] names = CourseNet.myDb.viewRoster(course);
			final String studentName = (String) JOptionPane.showInputDialog(null, "Choose a student to grade:",
			        "Choose Student", JOptionPane.QUESTION_MESSAGE, null, names, null);
			//System.out.println(studentName);
			
			// Print that student's current grade report here and allow it to be edited
			final Course c = course;
			currGrade = CourseNet.myDb.viewGrade(studentName, c);
			final JTextArea gradeReport = new JTextArea(currGrade);
			gradeReport.setLineWrap(true);
			gradeReport.setWrapStyleWord(true);
			JScrollPane scroll = new JScrollPane(gradeReport);
			scroll.setPreferredSize(new Dimension(300, 300));
			add(scroll);

			JButton postButton = new JButton("Post Grade");
			postButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					// Posting the grade
					String s = gradeReport.getText();
					Message m = new Message();
					m.text = s;
					m.username = CourseNet.username;
					//Send the grade to the database
					CourseNet.myDb.addGrade(studentName, s, c);
					JOptionPane.showMessageDialog(Grades.this, "Updated grade for student " + studentName);
					Grades.this.dispose();
				}
			});
			add(postButton, BorderLayout.PAGE_END);
		}
		
		setBounds(400, 100, 300, 400);
		setVisible(true);
	}
}
