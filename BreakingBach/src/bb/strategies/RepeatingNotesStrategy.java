package bb.strategies;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.Gene;
import org.jgap.impl.CompositeGene;

import bb.lib.*;
import bb.util.*;



public class RepeatingNotesStrategy extends FitnessFunction{

	public static int duplicateNotesThreshold = 10;
	public static int duplicateRestThreshold = 10;
	
	public void setDuplicateNotesThreshold(int duplicateNotesThreshold){
		
		if(duplicateNotesThreshold < 0){
			
			throw new IllegalArgumentException("duplicateNotesThreshold must be greater than 0");
			
		}
		
		this.duplicateNotesThreshold = duplicateNotesThreshold;
		
	}
	
	public void setDuplicateRestThreshold(int duplicateRestThreshold){
		
		if(duplicateRestThreshold < 0){
			
			throw new IllegalArgumentException("duplicateRestsThreshold must be greater than 0");
			
		}
		
		this.duplicateRestThreshold = duplicateRestThreshold;
		
	}

	@Override
	protected double evaluate(IChromosome melody) {

		double errors = 0.0D;
		
		Note previous = null;
		int sameNoteCount = 1;
		int restCount = 1;
		
		for (Gene gene: melody.getGenes()){
			
			if (previous != null){
				
				Note current = GeneNoteConverter.fromGene(gene);
				
				if (current.isRest()){
					
					restCount++;
					
				} else {
					
					sameNoteCount++;
					
				}
				
			}
			
			else {
				
				sameNoteCount = 1;
				restCount = 1;
				
			}
			
			previous = GeneNoteConverter.fromGene(gene);
			
		}
		
		if (sameNoteCount > this.duplicateNotesThreshold){
			
			errors += 1.0D;
			
		}
		
		if (restCount > this.duplicateRestThreshold){
			
			errors += 1.0D;
			
		}
		
		return errors * 2.0D;
		
	}

}
