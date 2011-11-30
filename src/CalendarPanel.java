import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

public class CalendarPanel extends ContentPanel
{
	public JLabel currentDate;
	public JButton loginButton, coursesButton, calendarButton;
	public JButton[] detailButtons;
	public JLabel[] dates, titles, descs;
	public ArrayList<Event> events;
	public Event[] eventsToDisplay;
	public int offset;
	public boolean editing;

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

		// Displaying the current date
		Calendar date = Calendar.getInstance();
		SimpleDateFormat formatter=  new SimpleDateFormat("EEEE, dd MMMM yyyy");
		String currentDate = formatter.format(date.getTime());
		JLabel dateToDisplay = new JLabel(currentDate);
		dateToDisplay.setFont(new Font("Arial Black", Font.PLAIN, 32));
		dateToDisplay.setForeground(Color.white);
		dateToDisplay.setBounds(100, 100, dateToDisplay.getPreferredSize().width, dateToDisplay.getPreferredSize().height);
		add(dateToDisplay);

		// Getting events to display
		events = CourseNet.myDb.viewAllEvents(CourseNet.username);
		
		Collections.sort(events);
		offset = 0;
		dates = new JLabel[10];
		titles = new JLabel[10];
		descs = new JLabel[10];
		detailButtons = new JButton[10];

		// Displaying events
		displayEvents();

		// Button to see events that don't fit
		JButton moreEvents = new JButton("See More Events");
		moreEvents.setBounds(450, 600, moreEvents.getPreferredSize().width, moreEvents.getPreferredSize().height);
		moreEvents.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				offset += 10;
				if (events.size() <= offset) offset = 0;
				displayEvents();
			}
		});
		add(moreEvents);
	}

	void displayEvents()
	{
		if (eventsToDisplay != null)
		{
			for (int i = 0; i<10; i++)
			{
				if (detailButtons[i] != null) detailButtons[i].setVisible(false);
				if (dates[i] == null) break;
				dates[i].setVisible(false);
				titles[i].setVisible(false);
				descs[i].setVisible(false);
			}
		}
		eventsToDisplay = new Event[10];
		for (int i=0; i<10; i++)
		{
			if (events.size() <= i+offset) break;
			eventsToDisplay[i] = events.get(i+offset);
		}
		int y = 150;
		DetailsListener l = new DetailsListener();
		for (int i = 0; i < 10; i++)
		{
			if (events.size() <= i+offset) break;
			int x = 100;
			Event e = events.get(i+offset);
			detailButtons[i] = new JButton("+");
			detailButtons[i].addActionListener(l);
			detailButtons[i].setToolTipText("See more event details");
			detailButtons[i].setBounds(x, y+10, 20, 20);
			add(detailButtons[i]);
			x+=25;
			dates[i] = new JLabel(e.date + ": ");
			dates[i].setFont(new Font("Arial Black", Font.PLAIN, 28));
			dates[i].setForeground(Color.white);
			dates[i].setBounds(x, y, dates[i].getPreferredSize().width, dates[i].getPreferredSize().height);
			add(dates[i]);
			x+=(dates[i].getPreferredSize().width+5);
			titles[i] = new JLabel(e.title + "--");
			titles[i].setFont(new Font("Arial Black", Font.PLAIN, 24));
			titles[i].setForeground(Color.white);
			titles[i].setBounds(x, y+5, titles[i].getPreferredSize().width, titles[i].getPreferredSize().height);
			add(titles[i]);
			x+=(titles[i].getPreferredSize().width+5);
			descs[i] = new JLabel(e.description);
			descs[i].setForeground(Color.lightGray);
			descs[i].setBounds(x, y+15, 900-x, descs[i].getPreferredSize().height);
			add(descs[i]);
			// Increment for next line
			y += (dates[i].getPreferredSize().height + 5);
		}
		
		repaint();
	}

	class DetailsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for (int i=0; i < detailButtons.length; i++)
			{
				if (detailButtons[i].equals(e.getSource()))
				{
					new EventPopup(eventsToDisplay[i]);
					break;
				}
			}
		}
	}
}