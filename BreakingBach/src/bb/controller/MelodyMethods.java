package bb.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;

import org.jgap.impl.CompositeGene;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import bb.lib.Duration;
import bb.lib.Note;
import bb.lib.Scale;
import bb.producers.Melody;
import bb.strategies.*;
import bb.util.GeneMidiConverter;
import bb.util.GeneNoteConverter;
import bb.util.MidiFileWriter;

import static java.lang.System.out;

public class MelodyMethods{
	
	public static final int MIN_OCTAVE = 4;
	public static final int MAX_OCTAVE = 7;
	
	public IChromosome fittestChromosome;
	public Melody melody;
	public Genotype genotype;
	
	public static String path;
	
	public static void main(String args[]) throws Exception{
		
		ScaleStrategy myFunc = new ScaleStrategy();
		ScaleStrategy.setScale(Scale.C_MAJOR);
		MelodyMethods generator = new MelodyMethods();
		generator.generateMelody(myFunc,24,100);
		
		if (getOS() == "WIN"){
			
			String username = System.getProperty("user.name");
			System.out.println(username);
			String install = System.getProperty("user.dir");
			path = install;
			
		}
		
		if (getOS() == "MAC"){
			
			path = "Mac OS X";
			
		}
		
		if (getOS() == "LINUX/UNIX"){
			
			path = "/data/home";
			
		}
		
		
		out.println(path); 
		generator.save(path);
		
		
	}
	
	public static String getOS() {
	    String os = System.getProperty("os.name").toLowerCase();

	    if(os.indexOf("mac") >= 0){
	       return "MAC";
	    }
	    else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0){
	       return "LINUX/UNIX";
	    }
	    else if(os.indexOf("sunos") >= 0){
	       return "SOLARIS";
	    }
	    else{
	    return "WIN";
	    }
	    
	}
	    
	public static void executeMelody(int numberOfNotes, int numberOfEvolutions, Scale scale, FitnessFunction functionType) throws Exception{
		
		ScaleStrategy scaleFunc = new ScaleStrategy();
		ScaleStrategy.setScale(scale);
		
		RepeatingNotesStrategy repeatFunc = new RepeatingNotesStrategy();
		  
		ProportionRestAndNoteStrategy propFunc = new ProportionRestAndNoteStrategy();
		 
		ParallelIntervalStrategy paraFunc = new ParallelIntervalStrategy();
		  
		IntervalStrategy intervalFunc = new IntervalStrategy();
		 
		GlobalPitchDistributionStrategy globalFunc = new GlobalPitchDistributionStrategy();
		GlobalPitchDistributionStrategy.setScale(scale);
		
		FitnessFunction myFunc = globalFunc;
		 
		MelodyMethods generator = new MelodyMethods();
		generator.generateMelody(myFunc,numberOfNotes,numberOfEvolutions);
		
		if (getOS() == "WIN"){
			
			
			String install = System.getProperty("user.dir");
			path = install;
			
		}
		
		if (getOS() == "MAC"){
			
			String install = System.getProperty("user.dir");
			path = install;
			
		}
		
		if (getOS() == "LINUX/UNIX"){
			
			String install = System.getProperty("user.dir");
			path = install;
			
		}
		
		
		out.println(path); 
		generator.save(path);
		
	}
	
	public void generateMelody(FitnessFunction melodyFitnessFunction, int numberOfNotes, int evolutions) throws Exception{
		
		Genotype genotype = MelodyMethods.setupGenotype(melodyFitnessFunction, numberOfNotes);
        this.evolve(genotype, evolutions);
        
        
		
	}
	
	public static Genotype setupGenotype(FitnessFunction melodyFitness, int numberOfNotes) throws Exception{
		
		
		Configuration.reset();
		Configuration gaConf = new DefaultConfiguration();
			
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        gaConf.setFitnessEvaluator(new DeltaFitnessEvaluator());
			
        gaConf.setFitnessFunction(melodyFitness);
        
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
			
		gaConf.setPopulationSize(numberOfNotes); //number of Notes
		
		//Set up music genes
			
		CompositeGene gene = new CompositeGene(gaConf);
		//add the pitch gene
		gene.addGene(new IntegerGene(gaConf, 0, 12), false);
		//add the octave gene
		gene.addGene(new IntegerGene(gaConf, MIN_OCTAVE, MAX_OCTAVE), false);
		//add the length (3-5 == quarter to sixteenth as defined in Duration.java)
		//at the moment only quarter and eighth notes are used
		gene.addGene(new IntegerGene(gaConf, 1, 4), false);
		
		IChromosome sampleChromosome = new Chromosome(gaConf, gene, numberOfNotes);
		gaConf.setSampleChromosome(sampleChromosome);

		
		return Genotype.randomInitialGenotype(gaConf);
		
	}
	
	public void evolve(Genotype genotype, int evolutions)
	{
			
		this.fittestChromosome = genotype.getFittestChromosome();
		this.createMelodyFromChromosome(fittestChromosome);
		this.printSolution();
		
	}
	
	private void printSolution() {
        System.out.println("generatedMelody.getFitnessValue() = " + melody.getFitnessValue());
        System.out.print("[");
        for (Note note : melody.getMelody()) {
            System.out.print(note.getPitch());
            System.out.print(note.getOctave() + "-");
            System.out.print(note.getDuration());
            System.out.print(":");
        }
        System.out.println("]");
    }
	
	public void createMelodyFromChromosome(IChromosome chromosome){
		
		ArrayList<Note> notes = new ArrayList<Note>();
        ArrayList<Duration> noteDurations = new ArrayList<Duration>();
		
		for (Gene gene: chromosome.getGenes())
		{
			
			notes.add(GeneNoteConverter.fromGene(gene));
			
		}
		
		this.melody = new Melody(chromosome.getFitnessValue(),notes, noteDurations);
	
	}
	
	//!==============GENERATE MIDI FILE=====================
	
	public void save(String path) throws IOException, MidiUnavailableException {
        Sequence sequence = null;
        try {
            sequence = generateSequence();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.writeSequenceToMidiFile(sequence, path);
    }
	
	private Sequence generateSequence() throws InvalidMidiDataException, MidiUnavailableException{
		
		Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();

        long ticks = 0;
        for (Gene gene : this.fittestChromosome.getGenes()) {
            CompositeGene note = (CompositeGene) gene;
            Duration duration = Duration.getByIndex((Integer) note.geneAt(2).getAllele());
            ticks += duration.getTicks();
            track.add(new MidiEvent(GeneMidiConverter.toMidiMessage(note), ticks));
            track.add(new MidiEvent(GeneMidiConverter.noteOffMidiMessage(), 0));
        }
        // Because the last note is not played for some reason, we add it again as a workaround
        this.addLastNote(this.fittestChromosome, track, ticks);
        out.println(this.fittestChromosome.toString());
        out.println(sequence.toString());
        out.println(this);
        return sequence;
        
		
	}
	
    private void addLastNote(IChromosome chromosome, Track track, long ticks) throws InvalidMidiDataException {
        CompositeGene note = (CompositeGene) chromosome.getGene(chromosome.getGenes().length - 1);
        Duration duration = Duration.getByIndex((Integer) note.geneAt(2).getAllele());
        ticks += duration.getTicks();
        track.add(new MidiEvent(GeneMidiConverter.toMidiMessage(note), ticks));
        track.add(new MidiEvent(GeneMidiConverter.noteOffMidiMessage(), 0));
    }
    
	private void writeSequenceToMidiFile(Sequence sequence, String path) throws IOException {
        FileOutputStream os = null;
        
        File file = new File(path + "/newfile.mid");
        try {
            os = new FileOutputStream(file);
            
            MidiFileWriter midiFileWriter = new MidiFileWriter();
            midiFileWriter.write(sequence, 0, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
                if (os != null) {
                    os.close();
                    System.out.println("Done");
                }
            
        }
    }

}