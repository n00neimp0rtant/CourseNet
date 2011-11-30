import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Assignments extends JFrame
{
	Assignments(Course course)
	{
		super("Assignments");

		if (CourseNet.isStudent)
		{
			JTextArea text = new JTextArea();
			text.setFont(new Font("Arial Black", Font.PLAIN, 18));
			text.append("Due: ALL THE THINGS");
			text.setEditable(false);
			getContentPane().add(text);
		}
		else
		{
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
					Message m = new Message();
					m.text = s;
					m.username = CourseNet.username;
					//Send the grade to the database
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
