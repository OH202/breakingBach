package bb.lib;

public class Key
{
	
	private Pitch pitch;
	private int octave;
	
	public Key(Pitch pitch, int octave)
	{
		
		this.pitch = pitch;
		this.octave = octave;
		
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
		
		Key key = (Key) object;
		
		return (this.octave == key.octave) && (this.pitch == key.pitch);
		
	}
	
	@Override
	public int hashCode()
	{
	
		int result = this.pitch != null ? this.pitch.hashCode() : 0;
		result = 31 * result + this.octave;
		return result;
	
	}
	
}