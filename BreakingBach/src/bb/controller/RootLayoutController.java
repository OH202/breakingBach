package bb.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import bb.controller.MainApp;

public class RootLayoutController{

	public static MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) 
	{
		 
	      RootLayoutController.mainApp = mainApp;
	      
	}
	
	public void handleExit()
	{
		
		System.exit(0);
		
	}
	
	public void handleAbout() throws URISyntaxException, IOException
	{
		URI u = new URL("https://github.com/OH202/breakingBach").toURI();
        Desktop.getDesktop().browse(u);
		
		
	}
	
}
