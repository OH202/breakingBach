package bb.strategies;

import bb.lib.*;

import bb.util.*;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class ProportionRestAndNoteStrategy extends FitnessFunction{

	public static double maxPercentageOfRests = 20.0D;
	
	public void setMaximumPercentageOfRests(double maxPercentageOfRests){
		
		double restsProportion = Double.compare(maxPercentageOfRests, 0.0D);
		
		if((restsProportion == -1) || (restsProportion == 1)){
			
			throw new IllegalArgumentException("maximum percentage of rests must be between 0 and 100");
			
		}
		
		this.maxPercentageOfRests = maxPercentageOfRests;
		
	}
	
	private double calculateProportionOfRests(IChromosome chromosome, int numberOfRests){
	    
		return numberOfRests / chromosome.getGenes().length * 100.0D;
		
	}
	
	public double evaluate(IChromosome melody){
		
	    int numberOfRests = 0;
	    
	    for (Gene gene : melody.getGenes()){
	    	
	      Note note = GeneNoteConverter.fromGene(gene);
	      if (note.isRest()) {
	    	  
	        numberOfRests++;
	        
	      }
	      
	    }
	    
	    double percentageOfRests = calculateProportionOfRests(melody, numberOfRests);
	    
	    return Math.abs(this.maxPercentageOfRests - percentageOfRests) * 2.0D;
	    
	}
	
}
