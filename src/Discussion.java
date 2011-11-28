import java.awt.*;
import javax.swing.*;

public class Discussion extends JFrame
{
	Discussion(CourseInfo course)
	{
		super("Discussion Board");
		JTextArea text = new JTextArea();
		text.append("Yes, indeed. That is a very interesting point.");
		text.setEditable(false);
		getContentPane().add(text);
		setBounds(400, 100, 300, 500);
		setVisible(true);
	}
}
