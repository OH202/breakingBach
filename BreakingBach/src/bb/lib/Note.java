package bb.lib;

public final class Note{
	
	public static final int MIN_OCTAVE = 0;
	public static final int MAX_OCTAVE = 10;
	
	private final Pitch pitch;
	private final Duration duration;
	
	private final int octave;
	
	private Note(Pitch pitch, int octave, Duration duration){
		
		this.pitch = pitch;
		this.octave = octave;
		this.duration = duration;
		
	}
	
	public static Note createNote(Pitch pitch, int octave, Duration duration){
	
		if((octave < 0) || (octave > 10)){
			
			throw new IllegalArgumentException("Octave must be between 0 and 10.");
			
		}
		
		return new Note(pitch, octave, duration);
		
	}
	
	public int toMidiNoteNumber(){
		
		return this.pitch.toMidiNoteNumber(this.octave);
		
	}
	
	public boolean isRest(){
		
		return Pitch.REST == this.pitch;
		
	}
	
	public Note getNextSemitone(){
		
		if (isUpOneOctave()){
			
			if (this.octave == 10){
				
				throw new IllegalStateException("Maximum octave reached");
				
			}
			
			return createNote(this.pitch.getNextSemitone(), this.octave + 1, this.duration);
			
		}
		
		return createNote(this.pitch.getNextSemitone(), this.octave, this.duration);
		
	}
	
	public boolean isUpOneOctave(){
		
		return Pitch.B == this.pitch;
		
	}
	
	public Note getPreviousSemitone(){
			
		if (isDownOneOctave()){
				
			if (this.octave == 10){
					
				throw new IllegalStateException("Minimum octave reached");
					
			}
			
			return createNote(this.pitch.getPreviousSemitone(), this.octave - 1, this.duration);
				
		}
			
		return createNote(this.pitch.getPreviousSemitone(), this.octave, this.duration);
		
	}
	
	public boolean isDownOneOctave(){
		
		return Pitch.C == this.pitch;
		
	}
	
	public boolean higherThan(Note otherNote){
		
		return ((this.pitch.higherThan(otherNote.pitch) && (this.octave > otherNote.octave)));
		
	}
	
	public boolean lowerThan(Note otherNote){
		
		return ((this.pitch.higherThan(otherNote.pitch) && (this.octave < otherNote.octave)));
		
	}
	
	public int getDifferenceInSemitones(Note otherNote){
		
		int differenceInSemitones = this.pitch.getDifferenceInSemitones(otherNote.pitch);
		differenceInSemitones += Math.abs(this.octave - otherNote.octave);
		
		return differenceInSemitones;
		
	}
	
	public Pitch getPitch(){
		
		return this.pitch;
		
	}
	
	public int getOctave(){
		
		return this.octave;
		
	}
	
	public Duration getDuration(){
		
		return this.duration;
		
	}
	
	@Override
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		builder.append("[Note: [pitch: ").append(this.pitch).append("]");
		builder.append(", [octave: ").append(this.octave).append("]");
		return builder.toString();
		
	}
	@Override
	public boolean equals(Object object)
	{
		
		if (this == object)
		{
			
			return true;
			
		}
		
		if ((object == null) || (getClass() != object.getClass()))
		{
			
			return false;
			
		}
		
		Note note = (Note) object;
		
		return (this.octave == note.octave) && (this.pitch == note.pitch);
		
	}
	
	@Override
	public int hashCode()
	{
		
		int result = this.pitch != null? this.pitch.hashCode() : 0;
		result = 31 * result + this.octave;
		return result;
		
	}
	
	
}