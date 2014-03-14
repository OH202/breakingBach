package bb.lib;

import java.util.HashMap;
import java.util.Map;

public enum Pitch{
	
	REST(0), C(1), C_SHARP(2), D(3), D_SHARP(4), E(5), E_SHARP(6), F(7), F_SHARP(8), G(9), G_SHARP(10), A(11), A_SHARP(12), B(13), B_SHARP(14);
	
	private static final Map<Integer, Pitch> indexToEnumMap;
	
	private final int noteNumber;
	
	static{
		
		indexToEnumMap = new HashMap<Integer, Pitch>();
		
		for (Pitch pitch: values()){
			
			indexToEnumMap.put(Integer.valueOf(pitch.noteNumber), pitch);
			
		}
		
	}
	
	private Pitch(int noteNumber){
		
		this.noteNumber = noteNumber;
		
	}
	
	public int toMidiNoteNumber(int octave){
		
		return this.noteNumber + 12 * octave - 1;
		
	}
	
	public static Pitch getByIndex(int index)
	{
		
		return indexToEnumMap.get(Integer.valueOf(index));
		
	}
	
	public Pitch getNextSemitone(){
		
		int nextNote = this.noteNumber + 1;
		
		if (nextNote > 12){
			
			nextNote = 1;
			
		}
		
		return indexToEnumMap.get(Integer.valueOf(nextNote));
		
	}
	
	public Pitch getPreviousSemitone(){
		
		int previousNote = this.noteNumber - 1;
		
		if (previousNote <= 0){
			
			previousNote = 12;
			
		}
		
		return indexToEnumMap.get(Integer.valueOf(previousNote));
		
	}
	
	public int getNoteNumber(){
		
		return this.noteNumber;
		
	}
	
	public int getDifferenceInSemitones(Pitch otherPitch){
		
		if (this == REST)
		{
			
			throw new IllegalStateException("Cannot calculate the semitones from [" + this + "] to [" + otherPitch + "]");
			
		}
		
		if (otherPitch == REST)
		{
			
			throw new IllegalArgumentException("The supplied note may not be a rest");
			
		}
		
		return Math.abs(this.noteNumber - otherPitch.noteNumber);
		
	}
	
	public boolean higherThan(Pitch otherPitch){
		
		return this.noteNumber > otherPitch.noteNumber;
		
	}
	
}