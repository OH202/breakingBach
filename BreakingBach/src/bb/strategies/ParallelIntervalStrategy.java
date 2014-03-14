package bb.strategies;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import bb.lib.*;

import bb.util.*;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class ParallelIntervalStrategy extends FitnessFunction{

	private static int numberOfParallelIntervalsThatSoundGood;
	private static int numberOfParallelIntervalsThatSoundBad;
	private EnumSet<Scale.Interval> intervalsThatSoundGoodInParallel;
	private EnumSet<Scale.Interval> intervalsThatSoundBadInParallel;
	private Scale scale;
	
	public ParallelIntervalStrategy(){
		
	  ParallelIntervalStrategy.numberOfParallelIntervalsThatSoundGood = 10;
	    
	  ParallelIntervalStrategy.numberOfParallelIntervalsThatSoundBad = 0;
	    
	  this.intervalsThatSoundGoodInParallel = EnumSet.of(Scale.Interval.THIRD, Scale.Interval.SIXTH);
	    
	  this.intervalsThatSoundBadInParallel = EnumSet.of(Scale.Interval.FIFTH);
	    
	  this.scale = Scale.C_MAJOR;
	
	}
	
	public static void setNumberOfParallelIntervalsThatSoundGood(int numberOfParallelIntervalsThatSoundGood){
		
	    ParallelIntervalStrategy.numberOfParallelIntervalsThatSoundGood = numberOfParallelIntervalsThatSoundGood;
	    
	}
	
	private int calculateNumberOfParallelIntervals(List<NoteInterval> intervals){
		
	    int numberOfParallelIntervals = 0;
	    NoteInterval[] intervalArray = intervals.toArray(new NoteInterval[intervals.size()]);
	    NoteInterval previousInterval = null;
	    
	    for (int i = 0; i < intervalArray.length; i++){
	    	
	      if (previousInterval != null){
	    	  
	        int nextIntervalindex = getIndexOfNextInterval(i);
	        
	        if (nextIntervalindex < intervalArray.length){
	        	
	          NoteInterval nextInterval = intervalArray[nextIntervalindex];
	          if (parallelInterval(previousInterval, nextInterval)){
	        	  
	            numberOfParallelIntervals++;
	            previousInterval = intervalArray[nextIntervalindex];
	            i = nextIntervalindex;
	            
	          }
	          else{
	        	  
	            previousInterval = intervalArray[i];
	            
	          }
	          
	        }
	        
	      }
	      
	      else
	      {
	        previousInterval = intervalArray[i];
	      }
	      
	    }
	    
	    return numberOfParallelIntervals;
	    
	  }
	  
	  private int getIndexOfNextInterval(int i){
		  
	    return i + 1;
	    
	  }
	  
	  private boolean parallelInterval(NoteInterval previousInterval, NoteInterval nextInterval){
		  
	    return (this.intervalsThatSoundGoodInParallel.contains(previousInterval.getInterval())) && (previousInterval.getInterval() == nextInterval.getInterval()) && (nextInterval.getStartNoteOfInterval().higherThan(previousInterval.getStartNoteOfInterval()));
	    
	  }
	  
	  private static class NoteInterval{
		  
	    private Scale.Interval interval;
	    private Pitch startPitchOfInterval;
	    
	    public NoteInterval(Scale.Interval interval, Pitch startPitchOfInterval){
	    	
	      this.interval = interval;
	      this.startPitchOfInterval = startPitchOfInterval;
	      
	    }
	    
	    public Scale.Interval getInterval(){
	    	
	      return this.interval;
	      
	    }
	    
	    public Pitch getStartNoteOfInterval(){
	    	
	      return this.startPitchOfInterval;
	      
	    }
	  }
	
	
	@Override
	protected double evaluate(IChromosome melody) {
		
		double errors = 1.0D;
	    
	    int numberOfParallelIntervals = 0;
	    List<NoteInterval> intervals = new ArrayList();
	    Pitch previousPitch = null;
	    for (Gene gene : melody.getGenes())
	    {
	      Note note = GeneNoteConverter.fromGene(gene);
	      Pitch currentPitch = note.getPitch();
	      if (previousPitch != null) {
	        intervals.add(new NoteInterval(this.scale.getInterval(previousPitch, currentPitch), previousPitch));
	      }
	      previousPitch = currentPitch;
	    }
	    numberOfParallelIntervals = calculateNumberOfParallelIntervals(intervals);
	    errors += Math.abs(this.numberOfParallelIntervalsThatSoundGood - numberOfParallelIntervals);
	    if (Double.compare(errors, 1.0D) == 0) {
	      errors = 0.0D;
	    }
	    
	    return errors * errors;
	    
	}

}
