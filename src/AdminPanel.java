import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class AdminPanel extends ContentPanel
{
	public JButton loginButton;
	public JLabel adminLabel;

	public AdminPanel()
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
		
		//
		adminLabel = new JLabel("Administration!");
		adminLabel.setFont(new Font("Arial Black", Font.PLAIN, 72));
		adminLabel.setForeground(Color.white);
		adminLabel.setBounds(0, 130, adminLabel.getPreferredSize().width, adminLabel.getPreferredSize().height);
		add(centeredVersionOf(adminLabel));
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
