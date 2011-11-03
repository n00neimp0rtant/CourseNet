import java.awt.* ;
import java.awt.event.*;

import javax.swing.*;

public class LoginPanel extends ContentPanel
{	
	public JLabel title, usernameLabel, passwordLabel;
	public JTextField usernameField;
	public JPasswordField passwordField;
	public JButton loginButton;
	
	public LoginPanel()
	{
		setLayout(null);
		setOpaque(false);
		setSize(new Dimension(970, 678));
		// title
		title = new JLabel("CourseNet");
		title.setFont(new Font("Arial Black", Font.PLAIN, 72));
		title.setForeground(Color.white);
		title.setBounds(0, 130, title.getPreferredSize().width, title.getPreferredSize().height);
		add(centeredVersionOf(title));
		
		// username label
		usernameLabel = new JLabel("Username");
		usernameLabel.setForeground(Color.white);
		usernameLabel.setBounds(0, 260, usernameLabel.getPreferredSize().width, usernameLabel.getPreferredSize().height);
		add(centeredVersionOf(usernameLabel));
		
		// username field
		usernameField = new JTextField("");
		usernameField.setBounds(0, 275, 200, usernameField.getPreferredSize().height);
		add(centeredVersionOf(usernameField));
		
		// password label
		passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.white);
		passwordLabel.setBounds(0, 320, passwordLabel.getPreferredSize().width, passwordLabel.getPreferredSize().height);
		add(centeredVersionOf(passwordLabel));
		
		// password field
		passwordField = new JPasswordField("");
		passwordField.setBounds(0, 335, 200, passwordField.getPreferredSize().height);
		add(centeredVersionOf(passwordField));
		
		// login button
		loginButton = new JButton("Login");
		loginButton.setBounds(0, 430, loginButton.getPreferredSize().width, loginButton.getPreferredSize().height);
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				getLoginResponse();
			}
		});
		add(centeredVersionOf(loginButton));
		
	}
	public void getLoginResponse()
	{
		if(usernameField.getText().equals("login") && String.valueOf(passwordField.getPassword()).equals("password"))
			switchToPanel(Panel.COURSES);
		else
			switchToPanel(Panel.LOGIN);
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