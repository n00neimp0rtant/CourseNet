import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CalendarPanel extends ContentPanel
{
	final static public String[] week = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
																		"Friday", "Saturday"};
	final static public String[] month = {"January", "February", "March", "April", "May", "June", "July",
							"August", "September", "October", "November", "December"};
	public Calendar date;
	public JLabel currentDate;
	public JButton loginButton, coursesButton, calendarButton;
	
	public CalendarPanel()
	{
		setLayout(null);
		setOpaque(false);
		setSize(new Dimension(970, 680));
		
		/* Navigation buttons */
		loginButton = new JButton("Logout");
		loginButton.setBounds(150, 50, loginButton.getPreferredSize().width, loginButton.getPreferredSize().height);
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.LOGIN);
			}
		});
		add(loginButton);

		coursesButton = new JButton("Courses");
		coursesButton.setBounds(450, 50, coursesButton.getPreferredSize().width, coursesButton.getPreferredSize().height);
		coursesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.COURSES);
			}
		});
		add(coursesButton);

		calendarButton = new JButton("Calendar");
		calendarButton.setBounds(750, 50, calendarButton.getPreferredSize().width, calendarButton.getPreferredSize().height);
		calendarButton.setSelected(true);	// Already on the Calendar page
		calendarButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				switchToPanel(Panel.CALENDAR);
			}
		});
		add(calendarButton);
		
		date = Calendar.getInstance();
		
		currentDate = new JLabel(week[date.get(Calendar.DAY_OF_WEEK)] + ", " +
					date.get(Calendar.DAY_OF_MONTH) + " " + month[date.get(Calendar.MONTH)] + " " +
										date.get(Calendar.YEAR));
		currentDate.setFont(new Font("Arial Black", Font.PLAIN, 48));
		currentDate.setForeground(Color.white);
		currentDate.setBounds(125, 125, currentDate.getPreferredSize().width, currentDate.getPreferredSize().height);
		add(currentDate);
	}
}