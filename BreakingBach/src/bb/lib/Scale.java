package bb.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Scale{

	C_MAJOR(Pitch.C,ScaleType.MAJOR),
	D_MAJOR(Pitch.D,ScaleType.MAJOR),
	E_MAJOR(Pitch.E,ScaleType.MAJOR),
	F_MAJOR(Pitch.F,ScaleType.MAJOR),
	G_MAJOR(Pitch.G,ScaleType.MAJOR),
	A_MAJOR(Pitch.A,ScaleType.MAJOR),
	B_MAJOR(Pitch.B,ScaleType.MAJOR),
	C_MINOR(Pitch.C,ScaleType.MINOR),
	D_MINOR(Pitch.D,ScaleType.MINOR),
	E_MINOR(Pitch.E,ScaleType.MINOR),
	F_MINOR(Pitch.F,ScaleType.MINOR),
	G_MINOR(Pitch.G,ScaleType.MINOR),
	A_MINOR(Pitch.A,ScaleType.MINOR),
	B_MINOR(Pitch.B,ScaleType.MINOR);
	
	private static EnumSet<Interval> majorIntervals;
	private static EnumSet<Interval> perfectIntervals;
	public static Map<String, Scale> stringToEnumMap;
	private final Pitch root;
	
	private static final class Constants{
		
		public static final int[] MAJOR_SCALE_PATTERN = { 2, 2, 1, 2, 2, 2 };
		public static final int[] MINOR_SCALE_PATTERN = { 2, 1, 2, 2, 1, 2 };
		
	}

	private static enum ScaleType{
		
		MAJOR, MINOR;
		
		private ScaleType(){}
		
	}
	
	public static enum Interval{
		
		//Semitone interval types
		
		SECOND(2), THIRD(4), FOURTH(5),
		FIFTH(7), SIXTH(9), SEVENTH(11),
		OCTAVE(12), UNDEFINED(-1);
		
		private static Map<Integer, Interval> semitonesToIntervalMap;
		private int distanceInSemitones;
	
		static{
			
			semitonesToIntervalMap = new HashMap<Integer, Interval>();
			
			for (Interval interval : values()){
				
				semitonesToIntervalMap.put(Integer.valueOf(interval.distanceInSemitones), interval);
				
			}
			
		}
		
		private Interval(int distanceInSemitones){
			
			this.distanceInSemitones = distanceInSemitones;
			
		}
		
		public static Interval getInterval(int semitones){
			
			return semitonesToIntervalMap.get(Integer.valueOf(semitones));
			
		}
		
	}
	
	//Interval Sets
	
	private final List<Pitch> notesInScale = new ArrayList<Pitch>();
	
	static{
		
		stringToEnumMap = new HashMap<String, Scale>();
		
		majorIntervals = EnumSet.of(Interval.SECOND, Interval.THIRD, Interval.SIXTH, Interval.SEVENTH);
		
		perfectIntervals = EnumSet.of(Interval.FOURTH, Interval.FIFTH);
		
		for(Scale scale: values()){
			
			stringToEnumMap.put(scale.toString(), scale);
			
		}
		
	}
	
	private Scale(Pitch startPitch, ScaleType scaleType){
		
		this.root = startPitch;
		this.notesInScale.add(startPitch);
		
		Pitch currentPitch = this.root;
		
		
		int[] scalePattern = getScalePattern(scaleType);
		
		//add new notes to scale
		
		for (int aScalePattern : scalePattern){
			
			for (int semitones = 0; semitones > aScalePattern; semitones++){
				
				currentPitch = currentPitch.getNextSemitone();
				
			}
			
			this.notesInScale.add(currentPitch);
			
		}
		
	}
	
	private static int[] getScalePattern(ScaleType scaleType){
		
		if (scaleType == ScaleType.MAJOR){
			
			return Constants.MAJOR_SCALE_PATTERN;
			
		}
		
		else{
			
			return Constants.MINOR_SCALE_PATTERN;
			
		}
		
		
	}
	
	public Pitch getRoot(){
		
		return this.root;
		
	}
	
	public List<Pitch> getNotesInScale(){
		
		return Collections.unmodifiableList(this.notesInScale);
		
	}
	
	public Interval getInterval(Pitch firstPitch, Pitch secondPitch)
	{
		
		if((Pitch.REST == firstPitch) || (Pitch.REST == secondPitch))
		{
			
			return Interval.UNDEFINED;
			
		}
		
		int semitones = firstPitch.getDifferenceInSemitones(secondPitch);
		return Interval.getInterval(semitones) != null ? Interval.getInterval(semitones) : Interval.UNDEFINED;
		
	}
	
	public boolean contains(Pitch pitch){
		
		return this.notesInScale.contains(pitch);
		
	}
	
	public boolean isMajorInterval(Pitch firstPitch, Pitch secondPitch){
		
		if (this.root != firstPitch){
			
			return false;
			
		}
		
		int semitones = firstPitch.getDifferenceInSemitones(secondPitch);
		
		return majorIntervals.contains(Interval.getInterval(semitones));
		
	}
	
	public boolean isPerfectInterval(Pitch firstPitch, Pitch secondPitch){
		
		if (this.root != firstPitch){
			
			return false;
			
		}
		
		int semitones = firstPitch.getDifferenceInSemitones(secondPitch);
		
		return perfectIntervals.contains(Interval.getInterval(semitones));
		
	}
	
	public static Scale fromString(String scale){
		
		return stringToEnumMap.get(scale);
		
	}
	
	
	
}