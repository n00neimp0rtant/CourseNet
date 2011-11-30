import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

public class Assignments extends JFrame
{
	Assignments(Course course)
	{
		super("Assignments");

		ArrayList<Event> assignments = CourseNet.myDb.viewCourseEvents(CourseNet.username);

		final JTextArea text = new JTextArea();
		Collections.sort(assignments);
		for (Event e : assignments)
		{
			// Posting messages with a timestamp signature
			text.append(e.title + "\nPosted on: " + e.timeStamp + "\n---\n");
		}
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		JScrollPane scrollText = new JScrollPane(text);
		scrollText.setPreferredSize(new Dimension(400, 250));
		add(scrollText, BorderLayout.PAGE_START);
		text.setEditable(false);
		

		if (CourseNet.isStudent) add(text);
		
		else
		{
			JButton postButton = new JButton("Post New Assignment");
			postButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					// Posting the event
					Event assig = new Event();
					
					assig.date = JOptionPane.showInputDialog("Enter the date of this assignment");
					
					assig.title = JOptionPane.showInputDialog("Enter the title of this assignment");
					
					assig.description = JOptionPane.showInputDialog("Enter a description of this assignment");
					
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter=  new SimpleDateFormat("yyyy.MM.dd @ HH:mm:SSS");
					assig.timeStamp = formatter.format(currentDate.getTime());
					
					//Send the event to the database
					
					JOptionPane.showMessageDialog(Assignments.this, "Posted new assignment");
					Assignments.this.dispose();
				}
			});
			add(postButton, BorderLayout.PAGE_END);
		}

		setBounds(400, 100, 300, 400);
		setVisible(true);
	}
}
