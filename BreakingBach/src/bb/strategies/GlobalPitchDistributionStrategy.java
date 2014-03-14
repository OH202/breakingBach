package bb.strategies;

import bb.lib.*;
import bb.util.*;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Chromosome;
import org.jgap.IChromosome;

public class GlobalPitchDistributionStrategy extends FitnessFunction{

	public static Scale scale;
	private static double pitchAdherenceThreshold = 0.98D;
	private static int maximumPitchDifferenceInSemitones = 8;
	
	public static void setScale(Scale paramScale){
		
		GlobalPitchDistributionStrategy.scale = paramScale;
		
	}
	
	public double evaluate(IChromosome melody) {

		long numberOfNotesThatMustBeInMaximumPitch = Math.round(melody.getGenes().length * this.pitchAdherenceThreshold);
	    int numberOfNotesNotInPitchDistribution = 0;
	    Note startOfMelody = null;
	    for (Gene gene : melody.getGenes())
	    {
	      Note note = GeneNoteConverter.fromGene(gene);
	      if (startOfMelody != null)
	      {
	        if ((!startOfMelody.isRest()) && (!note.isRest()))
	        {
	          int differenceInSemitones = startOfMelody.getDifferenceInSemitones(note);
	          if (differenceInSemitones > this.maximumPitchDifferenceInSemitones) {
	            numberOfNotesNotInPitchDistribution++;
	          }
	        }
	      }
	      else if (!note.isRest()) {
	        startOfMelody = note;
	      }
	    }
	    return Math.abs(melody.getGenes().length - numberOfNotesNotInPitchDistribution - numberOfNotesThatMustBeInMaximumPitch);
	    
	}
	
}
