package bb.strategies;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.Gene;

import bb.controller.MelodyOverviewController;
import bb.lib.*;
import bb.util.*;



public class ScaleStrategy extends FitnessFunction{

	private static final int ERROR_COUNT_WHEN_NOTE_NOT_ON_SCALE = 1;
	  
	public static Scale scale;
	
	public static void setScale(Scale paramScale){
		
		ScaleStrategy.scale = paramScale;
		
	}
	
	@Override
	public double evaluate(IChromosome melody){
		  
	    double errors = 0.0D;
	    
	    for (Gene gene : melody.getGenes()){
	    	
	    	Note note = GeneNoteConverter.fromGene(gene);
	    	
		    if ((Pitch.REST != note.getPitch()) && (!this.scale.contains(note.getPitch()))){
		        errors += 1.0D;
		    }
		      
	    }
	      
	    return errors * errors * 10.0D;
	    
	}
	   
}
