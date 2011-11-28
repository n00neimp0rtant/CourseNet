import java.awt.Font;

import javax.swing.*;

public class Assignments extends JFrame
{
	Assignments(CourseInfo course)
	{
		super("Assignments");
		JTextArea text = new JTextArea();
		text.setFont(new Font("Arial Black", Font.PLAIN, 18));
		text.append("Due: ALL THE THINGS");
		text.setEditable(false);
		getContentPane().add(text);
		setBounds(400, 100, 300, 500);
		setVisible(true);
	}
}
