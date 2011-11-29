import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Grades extends JFrame
{
	Grades(Course course)
	{
		super("Grades");
		
		if (CourseNet.isStudent)
		{
			JTextArea text = new JTextArea();
			text.setFont(new Font("Arial Black", Font.PLAIN, 72));
			text.append("A+");
			text.setEditable(false);
			getContentPane().add(text);
		}
		else
		{
			// Get the student (user?)names from the database
			String[] names = {"Jane", "John", "Joan"};
			final String studentName = (String) JOptionPane.showInputDialog(null, "Choose a student to grade:",
			        "Choose Student", JOptionPane.QUESTION_MESSAGE, null, names, null);
			System.out.println(studentName);
			
			// Print that student's current grade report here
			final Course c = course;
			String currGrade = "A+";
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
