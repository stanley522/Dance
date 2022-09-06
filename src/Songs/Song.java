package Songs;

import Arrows.Direction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Song {
    String id;
    String name;
    String script;
    String customScript;
    ArrayList<Note> notes = new ArrayList<>();
    int delay = 0;
    int skip = 0;
    double tempo = 60;
    int length;
    public Clip clip;

    Song(int l, double t, int d, int s, String musicPath, String n, String i) {
        length = l;
        tempo = t;
        delay = d;
        skip = s;
        name = n;
        id = i;
        if (musicPath == null)
            return;
        var music = new File(musicPath);
        try {
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(music);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
            var frameLength = clip.getFrameLength() / length;
            clip.setFramePosition(skip * frameLength);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> getOnBeatNote(int ticks) {
        var onBeatNote = notes.stream().filter(note -> (int) (((note.beat) * 500 / tempo)) == ticks - delay);

        return new ArrayList<>(onBeatNote.collect(Collectors.toList()));
    }

    public void setScript(String s) {
        script = s;
    }

    public void setCustomScript(String s) {
        customScript = s;
    }


    public ArrayList<Note> createNotes(SongDifficulty difficulty) {
        var useScript = script;
        if (difficulty == SongDifficulty.Custom)
            useScript = customScript;
        int t = 0;
        char tempC;
        var createNotes = new ArrayList<Note>();
        for (int i = 0; i < useScript.length(); i++) {
            var c = useScript.charAt(i);
            if (c == '0') {
                t += 4;
                continue;
            }
            var randomNoteType = NoteType.randomNoteType(difficulty);
            if (c == '.')
                t -= 2;
            if (c == '/')
                t -= 3;
            if (c == '+')
                t -= 4;
            if (noteTypeMap.containsKey(c)) {
                if (difficulty == SongDifficulty.Custom)
                    createNotes.add(new Note(t, noteTypeMap.get(c)));
                else
                    createNotes.add(new Note(t, randomNoteType));
                if (Character.isUpperCase(c)) {
                    if (difficulty == SongDifficulty.Custom)
                        createNotes.add(new Note(t + 0.5, noteTypeMap.get(c)));
                    else
                        createNotes.add(new Note(t + 0.5, randomNoteType));
                }
                t += 4;
            }
            var randomDouble = randomDouble(difficulty);
            if (c == 'r') {
                createNotes.add(new Note(t, randomNoteType));
                if (difficulty == SongDifficulty.Crazy && randomDouble)
                    createNotes.add(new Note(t + 0.5, randomNoteType));
                t += 4;
            }
            if (difficulty == SongDifficulty.Custom || difficulty == SongDifficulty.Easy)
                continue;

            if (c == 2) {
                createNotes.add(new Note(t, randomNoteType));
                if (randomDouble)
                    createNotes.add(new Note(t + 0.5, randomNoteType));
                t += 4;
            }

            if (difficulty == SongDifficulty.Medium)
                continue;
            if (c == 3) {
                createNotes.add(new Note(t, randomNoteType));
                if (randomDouble)
                    createNotes.add(new Note(t + 0.5, randomNoteType));
                t += 4;
            }
            if (difficulty == SongDifficulty.Hard)
                continue;
            if (c == 4) {
                createNotes.add(new Note(t, randomNoteType));
                if (randomDouble)
                    createNotes.add(new Note(t + 0.5, randomNoteType));
                t += 4;
            }
        }
        notes = createNotes;
        return notes;
    }

    boolean randomDouble(SongDifficulty difficulty) {
        if (difficulty == SongDifficulty.Crazy)
            return new Random().nextInt(100) < 15;
        if (difficulty == SongDifficulty.Hard)
            return new Random().nextInt(100) < 5;
        return false;
    }

    Map<Character, NoteType> noteTypeMap = Map.ofEntries(
            Map.entry('w', NoteType.Up),
            Map.entry('x', NoteType.Down),
            Map.entry('a', NoteType.Left),
            Map.entry('d', NoteType.Right),
            Map.entry('q', NoteType.UpLeft),
            Map.entry('e', NoteType.UpRight),
            Map.entry('z', NoteType.DownLeft),
            Map.entry('c', NoteType.DownRight),
            Map.entry('v', NoteType.UpDown),
            Map.entry('b', NoteType.LeftRight),

            Map.entry('W', NoteType.Up),
            Map.entry('X', NoteType.Down),
            Map.entry('A', NoteType.Left),
            Map.entry('D', NoteType.Right),
            Map.entry('Q', NoteType.UpLeft),
            Map.entry('E', NoteType.UpRight),
            Map.entry('Z', NoteType.DownLeft),
            Map.entry('C', NoteType.DownRight),
            Map.entry('V', NoteType.UpDown),
            Map.entry('B', NoteType.LeftRight)

    );

    public double getTempo() {
        return tempo;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void playMusic(int volume) {
        if (clip == null)
            return;
        Objects.requireNonNull(clip);

        FloatControl clipVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (clipVolume != null) {
            clipVolume.setValue(20f * (float) Math.log10(volume / 100.0));
        }
        clip.start();

    }

    public void stopMusic() {
        if (clip == null)
            return;
        var frameLength = clip.getFrameLength() / length;
        clip.setFramePosition(skip * frameLength);
        clip.stop();
    }

    public boolean hasCustom() {
        return customScript != null;
    }
}
