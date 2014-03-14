package bb.util;

import bb.lib.*;
import org.jgap.Gene;
import org.jgap.impl.CompositeGene;
import org.jgap.impl.IntegerGene;

public final class GeneNoteConverter
{
	
  public static Note fromGene(Gene gene)
  {
	  
    CompositeGene noteGene = (CompositeGene)gene;
    IntegerGene pitch = (IntegerGene)noteGene.geneAt(0);
    IntegerGene octave = (IntegerGene)noteGene.geneAt(1);
    IntegerGene duration = (IntegerGene)noteGene.geneAt(2);
    return Note.createNote(Pitch.getByIndex(((Integer)pitch.getAllele()).intValue()),
    		((Integer)octave.getAllele()).intValue(),
    		Duration.getByIndex(((Integer)duration.getAllele()).intValue()));
    
  }
  
}
