
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Discussion extends JFrame
{
	public ArrayList<Message> messages;

	Discussion(Course course)
	{
		super("Discussion Board");
		setLayout(new BorderLayout());

		// This needs to get the previous discussion posts
		final Course c;
		c = course;
		messages = new ArrayList<Message>();
		ArrayList<Message> myMessages = new ArrayList<Message>();
		myMessages = CourseNet.myDb.viewMessages(course);
		for(int i = 0; i < myMessages.size(); i++)
		{
			messages.add(i, new Message());
			messages.get(i).text = myMessages.get(i).text;
			messages.get(i).username = myMessages.get(i).username;
		}

		final JTextArea text = new JTextArea();
		for (Message m : messages)
		{
			// Maybe we could even get a signature based on the username?
			text.append(m.text + "\nSent by: " + m.username + "\n---\n");
		}
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		JScrollPane scrollText = new JScrollPane(text);
		scrollText.setPreferredSize(new Dimension(400, 250));
		add(scrollText, BorderLayout.PAGE_START);

		final JTextArea newPost = new JTextArea();
		newPost.setLineWrap(true);
		newPost.setWrapStyleWord(true);
		JScrollPane scrollPost = new JScrollPane(newPost);
		scrollPost.setPreferredSize(new Dimension(400, 250));
		add(scrollPost);

		JButton postButton = new JButton("Post to Discussion Board");
		postButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				// Posting to the discussion board!
				String s = newPost.getText();
				Message m = new Message();
				m.text = s;
				m.username = CourseNet.username;
				CourseNet.myDb.postMessage(c, m);
				text.append(s + "\nSent by: " + CourseNet.username + "\n---\n");
				newPost.setText("");
			}
		});
		add(postButton, BorderLayout.PAGE_END);
		setBounds(200, 100, 400, 500);
		setVisible(true);
	}
}