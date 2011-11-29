import java.awt.* ;
import java.awt.event.* ;
import javax.swing.*;

public class CourseNet extends JApplet {

	// containerPanel
	ImagePanel containerPanel;
	ContentPanel stuffPanel;
	Thread panelWatchdog;
	static myDatabase myDb;
	
	public static boolean isStudent;
	public static String username;

	//Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                    startDb();
                    new Thread(
            		new Runnable() {
                        public void run() {
                        	while(true)
                        	{
                        		Panel panelType = stuffPanel.getNewPanelType();
                        		if(!(panelType.equals(Panel.IDLE)))
                        		{
                        			containerPanel.remove(stuffPanel);
                        			repaint();
                        			
                        			switch(panelType)
                        			{
                        			case BLANK:
                        				stuffPanel = new ContentPanel();
                        				break;
                        			case LOGIN:
                        				stuffPanel = new LoginPanel();
                        				break;
                        			case COURSES:
                            			stuffPanel = new CoursesPanel();
                            			break;
                        			case CALENDAR:
                            			stuffPanel = new CalendarPanel();
                            			break;
                        			}
                        			
                        			
                        			containerPanel.add(stuffPanel);
                        			repaint();
                        		}
                        		try {
									Thread.sleep(100);
									repaint();
								} catch (InterruptedException e){
									e.printStackTrace();
								}
                        	}
                        }
                    }).start();
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't complete successfully");
        }
    }
    
    private void createGUI()
    {
        //Create and set up the content pane.
		Image image = getToolkit().getImage("../background.jpg");
		containerPanel = new ImagePanel(image);
		containerPanel.setPreferredSize(new Dimension(970, 678));

		stuffPanel = new LoginPanel();
		stuffPanel.setOpaque(false);
		stuffPanel.setLayout(null);
		containerPanel.setLayout(null);
		containerPanel.add(stuffPanel);

		getContentPane().add(containerPanel);
    }
    
    // connects to database
    private void startDb()
    {
    	myDb = new myDatabase();
    	myDb.connect("jdbc:mysql://localhost/coursenet");
    	myDb.dropTables();
    	myDb.createTables();
    	myDb.init();
    }
}