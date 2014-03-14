package bb.controller;

import org.jgap.FitnessFunction;

import bb.controller.MainApp;


import bb.lib.*;
import bb.strategies.GlobalPitchDistributionStrategy;
import bb.strategies.IntervalStrategy;
import bb.strategies.ParallelIntervalStrategy;
import bb.strategies.ProportionRestAndNoteStrategy;
import bb.strategies.RepeatingNotesStrategy;
import bb.strategies.ScaleStrategy;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import bb.controller.MelodyMethods;

public class MelodyOverviewController{
	
	@FXML 
	private Button generate;
	
	@FXML 
	private ChoiceBox<?> numberNotes;
	@FXML 
	private ChoiceBox<?> numberEvolutions;
	
	
	@FXML 
	private RadioButton enableMajor;
	@FXML
	private RadioButton enableMinor;
	
	@FXML
	private ChoiceBox<?> majorScales;
	@FXML
	private ChoiceBox<?> minorScales;
	@FXML
	private ChoiceBox<?> fitnessFunctions;
	
	Integer numEvolutions;
	Integer numNotes;
	Scale scale;
	FitnessFunction functionType;
	
	public static MainApp mainApp;
	
	public static final String[] functionTypes = {"Pitch Dist.","Intervals", "Parallel Intervals","Notes:Rests","Repeating Notes","Scale"};
	
	public void setMainApp(MainApp mainApp) 
	{
		 
	      MelodyOverviewController.mainApp = mainApp;
	      
	      
	}
	
	//******METHODS******
	
	//General Actions
	public void handleGenerateMelody(ActionEvent event) throws NumberFormatException, Exception
	{
		
		System.out.println("GENERATING");
		handleNumberOfEvolutions(event);
		handleNumberOfNotes(event);
		handleScale(event);
		handleFitnessFunction(event);
		System.out.println(scale);
		
		MelodyMethods.executeMelody(numNotes,numEvolutions,scale,functionType);
			
	}
	
	public void handleNumberOfNotes(ActionEvent event)
	{
		
		System.out.println("CHECKING NOTES");
		
		if(numberNotes.getValue() != null){
			
			Object notes = numberNotes.getValue();
			String stringNotes = notes.toString();
			
			numNotes = Math.abs(Integer.parseInt(stringNotes));
			
		}
		
		enableButtons();
		
	}
	
	public void handleNumberOfEvolutions(ActionEvent event)
	{
	
		System.out.println("CHECKING EVO");
		
		if(numberEvolutions.getValue() != null){
			
			Object evolutions = numberEvolutions.getValue();
			String stringEvolutions = evolutions.toString();
			
			numEvolutions  = Math.abs(Integer.parseInt(stringEvolutions));
			
		}
		
		enableButtons();
		
	}
	
	public void handleScale(ActionEvent event){
		
		
		
		if(enableMajor.isSelected()){
			
			majorScales.setDisable(false);
			minorScales.setDisable(true);
			handleMajorScales(event);
			
		}else{
			
			minorScales.setDisable(false);
			majorScales.setDisable(true);
			handleMinorScales(event);
			
		}
		
		enableButtons();
		
	}
	
	public void handleMajorScales(ActionEvent event){
		
		
		
		System.out.println("MAJOR");
		Object majorScale = majorScales.getValue();
		String stringScale = majorScale.toString().toUpperCase();
		scale = Scale.fromString(stringScale);
		
	
		
		enableButtons();
		
	}
	
	public void handleMinorScales(ActionEvent event){
		
		
		
		System.out.println("MINOR");
		Object minorScale = majorScales.getValue();
		String stringScale = minorScale.toString().toUpperCase();
		scale = Scale.fromString(stringScale);
		
		
		enableButtons();
		
	}
	
	public void handleFitnessFunction(ActionEvent event){
		
		System.out.println("CHOOSING FUNCTION");
		 
	    Object objFunction = fitnessFunctions.getValue();
	    String function = objFunction.toString();
	       	
	    if (function == functionTypes[0]){functionType = new GlobalPitchDistributionStrategy();}
	                     
	    else if (function == functionTypes[1]){functionType = new IntervalStrategy();}
	                     
	    else if (function == functionTypes[2]){functionType = new ParallelIntervalStrategy();}
	                     
	    else if (function == functionTypes[3]){functionType = new ProportionRestAndNoteStrategy();}
	                     
	    else if (function == functionTypes[4]){functionType = new RepeatingNotesStrategy();}
	                     
	    else {functionType = new ScaleStrategy();}
	            
	}
	
	public void enableButtons(){
		
		//dirty, needs to be refactored
		if((numberNotes.getValue().toString().matches("[0-9]+") && (numberNotes.getValue().toString().length() >= 2)) && (numberEvolutions.getValue().toString().matches("[0-9]+") && (numberEvolutions.getValue().toString().length() >= 2)) && (numberNotes.getValue() != null) && (minorScales.getValue() != null) && (majorScales.getValue() != null)){
			
			generate.setDisable(false);
			
		}
		
	}
	
}