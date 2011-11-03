import javax.swing.JPanel;

public class ContentPanel extends JPanel
{
	public Panel newPanelType = Panel.IDLE;
	
	public Panel getNewPanelType()
	{
		return newPanelType;
	}
	public void resetNewPanelType()
	{
		newPanelType = Panel.IDLE;
	}
	
	protected void switchToPanel(Panel panelType)
	{
		newPanelType = panelType;
	}
}