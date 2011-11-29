import java.awt.Font;

import javax.swing.*;


public class EventPopup extends JFrame
{
	EventPopup(Event e)
	{
		super(e.title);
		JTextArea text = new JTextArea();
		text.append(e.date+"\n");
		text.append(e.description);
		text.setEditable(false);
		getContentPane().add(text);
		setBounds(350, 100, text.getPreferredSize().width+50, text.getPreferredSize().height+50);
		setVisible(true);
	}
}
