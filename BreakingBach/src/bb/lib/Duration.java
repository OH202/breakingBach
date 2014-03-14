package bb.lib;

//imports
import java.util.HashMap;
import java.util.Map;

public enum Duration{
	
	WHOLE(1,16L), HALF(2,8L), QUARTER(3, 4L), EIGHTH(4, 2L), SIXTEENTH(5,1L);

	private static final Map<Integer, Duration> indexToEnumMap;
	
	private final int index;
	private final long ticks;
	
	static{
		
		indexToEnumMap = new HashMap<Integer, Duration>();
		
		for (Duration note: values()){
			
			indexToEnumMap.put(Integer.valueOf(note.index), note);
			
		}
		
	}
	
	private Duration(int index, long ticks){
		
		this.index = index;
		this.ticks = ticks;
		
	}
	
	public int getIndex(){
		
		return this.index;
		
	}
	
	public long getTicks(){
		
		return this.ticks;
		
	}
	
	public static Duration getByIndex(int index){
		
		return indexToEnumMap.get(Integer.valueOf(index));
		
	}
}