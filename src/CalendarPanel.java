import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CalendarPanel extends ContentPanel
{
	public JLabel title;
	public JButton loginButton, coursesButton, calendarButton;
	
	public CalendarPanel()
	{
		setLayout(null);
		setOpaque(false);
		setSize(new Dimension(970, 678));
		
		title = new JLabel("Calendar");
		title.setFont(new Font("Arial Black", Font.PLAIN, 72));
		title.setForeground(Color.white);
		title.setBounds(0, 130, title.getPreferredSize().width, title.getPreferredSize().height);
		add(centeredVersionOf(title));
		
		loginButton = new JButton("Login");
		loginButton.setBounds(0, 430, loginButton.getPreferredSize().width, loginButton.getPreferredSize().height);
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.LOGIN);
			}
		});
		add(centeredVersionOf(loginButton));
		
		coursesButton = new JButton("Courses");
		coursesButton.setBounds(300, 430, coursesButton.getPreferredSize().width, coursesButton.getPreferredSize().height);
		coursesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.COURSES);
			}
		});
		add(coursesButton);
		
		calendarButton = new JButton("Calendar");
		calendarButton.setBounds(970-300, 430, calendarButton.getPreferredSize().width, calendarButton.getPreferredSize().height);
		calendarButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.CALENDAR);
			}
		});
		add(calendarButton);
		
		
	}
	
	public int horizontalCenterValue(Container guiObject)
	{
		return 485-((guiObject.getBounds().width)/2);
	}
	public int horizontalCenterValue(int width)
	{
		return 485-(width/2);
	}
	public Container centeredVersionOf(Container guiObject)
	{
		guiObject.setBounds(horizontalCenterValue(guiObject), guiObject.getBounds().y, guiObject.getBounds().width, guiObject.getBounds().height);
		return guiObject;
	}
}