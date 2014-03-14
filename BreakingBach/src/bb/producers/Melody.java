package bb.producers;

import java.util.List;
import bb.lib.Duration;
import bb.lib.Note;

public class Melody{
	
	private double fitnessValue;
	private List<Note> melody;
	private List<Duration> noteDurations;
	
	public Melody(double fitnessValue, List<Note> melody, List<Duration> noteDurations){
		
		this.fitnessValue = fitnessValue;
		this.melody = melody;
		this.noteDurations = noteDurations;
		
	}
	
	public double getFitnessValue(){
		
		return fitnessValue;
		
	}
	
	public List<Note> getMelody(){
		
		return melody;
		
	}
	
	public List<Duration> getNoteDurations(){
		
		return noteDurations;
		
	}
	
}