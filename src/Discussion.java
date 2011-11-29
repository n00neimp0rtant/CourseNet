import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Discussion extends JFrame
{
	public ArrayList<String> messages;
	
	Discussion(CourseInfo course)
	{
		super("Discussion Board");
		setLayout(new BorderLayout());
		
		// This needs to get the previous discussion posts
		messages = new ArrayList<String>();
		messages.add("Hello everyone,\nDoes it seem like this project is going to be ok after all? Because it seems to me like we're kind of kicking some serious butt.\nThanks! :)\nKyra");
		
		final JTextArea text = new JTextArea();
		for (String s : messages)
		{
			// Maybe we could even get a signature based on the username?
			text.append(s + "\n---\n");
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
				// Post to the discussion board--needs to store this string (somehow)
				String s = newPost.getText();
				text.append(s + "\n---\n");
				newPost.setText("");
			}
		});
		add(postButton, BorderLayout.PAGE_END);
		setBounds(200, 100, 400, 500);
		setVisible(true);
	}
}
