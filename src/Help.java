import javax.swing.*;

public class Help extends JFrame
{
	Help()
	{
		super("Help!");
		JTextArea text = new JTextArea("Helping you out. :)\n\n\n\n\n\n\n\n\n\n\n\nNow with more text!");
		text.setEditable(false);
		getContentPane().add(text);
		setBounds(400, 100, 300, 500);
		setVisible(true);
	}
}
