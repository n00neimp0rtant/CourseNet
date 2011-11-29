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
	public JButton[] detailButtons;
	public JButton addButton;
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

		date = Calendar.getInstance();
		currentDate = new JLabel(week[date.get(Calendar.DAY_OF_WEEK)] + ", " +
				date.get(Calendar.DAY_OF_MONTH) + " " + month[date.get(Calendar.MONTH)] + " " +
				date.get(Calendar.YEAR));
		currentDate.setFont(new Font("Arial Black", Font.PLAIN, 32));
		currentDate.setForeground(Color.white);
		currentDate.setBounds(100, 100, currentDate.getPreferredSize().width, currentDate.getPreferredSize().height);
		add(currentDate);

		events = new ArrayList<Event>();
		for (int i = 0; i < 21; i++)
		{
			events.add(i, new Event());
			events.get(i).date = "0" + String.valueOf(i) + "/11/11";
			if (i%2 == 0)
				events.get(i).title = "Longer Title";
			else
				events.get(i).title = "Title";
			events.get(i).description = "This is a very important event that you should go attend." + String.valueOf(i);
		}
		events.add(9, new Event());
		events.get(9).date = "07/31/1992";
		events.get(9).title = "Another Event That Is Even Longer";
		events.get(9).description = "This description should be long enough to overflow the field width....";

		Collections.sort(events);
		offset = 0;
		dates = new JLabel[10];
		titles = new JLabel[10];
		descs = new JLabel[10];
		detailButtons = new JButton[10];

		addEvents();

		JButton moreEvents = new JButton("See More Events");
		moreEvents.setBounds(450, 600, moreEvents.getPreferredSize().width, moreEvents.getPreferredSize().height);
		moreEvents.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				offset += 10;
				if (events.size() <= offset) offset = 0;
				addEvents();
			}
		});
		add(moreEvents);
		if (!CourseNet.isStudent)
		{
			final JButton editButton = new JButton("Edit Events");
			editButton.setBounds(750, 600, editButton.getPreferredSize().width, editButton.getPreferredSize().height);
			editButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					if (editButton.isSelected())
					{
						editing = false;
						editButton.setText("Edit Events");
						editButton.setSelected(false);
						addEvents();
					}
					else
					{
						editing = true;
						editButton.setText("Finish Editing");
						editButton.setSelected(true);
						addEvents();
					}
				}
			});
			add(editButton);
		}
	}

	void addEvents()
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
			if (editing) detailButtons[i] = new JButton("X");
			else detailButtons[i] = new JButton("+");
			detailButtons[i].addActionListener(l);
			if (editing) detailButtons[i].setToolTipText("Remove this event");
			else detailButtons[i].setToolTipText("See more event details");
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
		if (addButton != null) addButton.setVisible(false);
		if (editing)
		{
			addButton = new JButton("Add New Event");
			addButton.addActionListener(l);
			addButton.setBounds(150, 600, addButton.getPreferredSize().width, addButton.getPreferredSize().height);
			add(addButton);
		}
	}

	class DetailsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton theButton = (JButton)e.getSource();

			if (theButton.equals(addButton))
			{
				// Needs to be replaced with actual new event getting.
				Event newEvent = new Event();
				newEvent.date = JOptionPane.showInputDialog("Enter the date of your new event");
				newEvent.title = JOptionPane.showInputDialog("Enter the title of your new event");
				newEvent.description = JOptionPane.showInputDialog("Enter a description of your new event");
				events.add(newEvent);
				addEvents();
				return;
			}

			for (int i=0; i < detailButtons.length; i++)
			{
				if (detailButtons[i].equals(theButton))
				{
					if (theButton.getText().equals("X"))
					{
						events.remove(i+offset);
						addEvents();
					}
					
					else new EventPopup(eventsToDisplay[i]);

					break;
				}
			}
		}
	}
}