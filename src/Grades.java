import java.awt.*;
import javax.swing.*;

public class Grades extends JFrame
{
	Grades(CourseInfo course)
	{
		super("Grades");
		JTextArea text = new JTextArea();
		text.setFont(new Font("Arial Black", Font.PLAIN, 72));
		text.append("A+");
		text.setEditable(false);
		getContentPane().add(text);
		setBounds(400, 100, 300, 500);
		setVisible(true);
	}
}
