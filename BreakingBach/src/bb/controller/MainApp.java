package bb.controller;

import java.io.IOException;
import bb.controller.RootLayoutController;
import bb.controller.MelodyOverviewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application{

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	//stuff
	
	public static void main(String[] args)
	{
		
		//launch(args);
		Application.launch(MainApp.class, (java.lang.String[])null);
		
	}
	
	public MainApp(){}
	
	@Override
	public void start(Stage primaryStage)
	{
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Breaking Bach");
		this.primaryStage.getIcons().add(new Image("file:resources/images/note_icon.png"));
		
		try {
		    // Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/bb/gui/view/RootLayout.fxml"));
		    
		    rootLayout = (BorderPane) loader.load();
		    
		    Scene scene = new Scene(rootLayout);
		    primaryStage.setScene(scene);

		    // Give the controller access to the main app
		    RootLayoutController controller = loader.getController();
		    controller.setMainApp(this);
		    
		    primaryStage.setResizable(false);
		    primaryStage.show();
		    
		  } catch (IOException e) {
		    // Exception gets thrown if the fxml file could not be loaded
		    e.printStackTrace();
		  }
		
		showMelodyOverview();
		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void showMelodyOverview() 
	{
		
		try
		{
			
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/bb/gui/view/MelodyOverview.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);
			
			//Give the controller access to the main app
			MelodyOverviewController controller = loader.getController();
			
			controller.setMainApp(this);
			
			
		}
		
		catch(IOException e)
		{
			
			e.printStackTrace();
			
		}
		
	}
	
}

