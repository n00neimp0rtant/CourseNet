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

		ArrayList<Event> assignments = CourseNet.myDb.viewEvents(CourseNet.username);

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
			add(text, BorderLayout.NORTH);
			final Course c = course;
			final JTextArea assignment = new JTextArea();
			assignment.setLineWrap(true);
			assignment.setWrapStyleWord(true);
			JScrollPane scroll = new JScrollPane(assignment);
			scroll.setPreferredSize(new Dimension(300, 300));
			add(scroll);

			JButton postButton = new JButton("Post Assignment");
			postButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					// Posting the event
					String s = assignment.getText();
					Event assig = new Event();
					assig.title = s;
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter=  new SimpleDateFormat("yyyy.MM.dd @ HH:mm:SSS");
					assig.timeStamp = formatter.format(currentDate.getTime());
					System.out.println(assig.timeStamp);
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
