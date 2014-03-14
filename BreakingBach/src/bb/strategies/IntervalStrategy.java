package bb.strategies;

import bb.lib.*;
import bb.util.*;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Chromosome;
import org.jgap.IChromosome;

public class IntervalStrategy extends FitnessFunction{

	public static int numberOfMajorIntervals = 6;
	public static int numberOfPerfectIntervals = 6;
	public static Scale scale;
	
	public static void setScale(Scale paramScale){
		
		IntervalStrategy.scale = paramScale;
		
	}
	
	private boolean currentNoteAndPreviousNoteAreNotes(Note previousNote, Note currentNote)
	{
	    return (!previousNote.isRest()) && (!currentNote.isRest());
	}
	
	@Override
	public double evaluate(IChromosome melody) {

		double errors = 1.0D;
		int actualMajorIntervals = 0;
		int actualPerfectIntervals = 0;
		
		Note previousNote = null;
		for (Gene gene: melody.getGenes()){
			
			Note note = GeneNoteConverter.fromGene(gene);
			
			if((previousNote != null) && currentNoteAndPreviousNoteAreNotes(previousNote, note)){
				
				if (this.scale.isMajorInterval(previousNote.getPitch(), note.getPitch())) {
					actualMajorIntervals++;
			    }
			    if (this.scale.isPerfectInterval(previousNote.getPitch(), note.getPitch())) {
			        actualPerfectIntervals++;
			    }
				
			}
			
			if (!note.isRest()) {
		        previousNote = note;
		    }
			
		}
		
		errors += Math.abs(IntervalStrategy.numberOfMajorIntervals - actualMajorIntervals);
		errors += Math.abs(IntervalStrategy.numberOfPerfectIntervals - actualPerfectIntervals);
		
		if(Double.compare(errors, 1.0D) == 0){
			
			errors = 0.0D;
			
		}
		
		return errors * errors;
		
	}

}
