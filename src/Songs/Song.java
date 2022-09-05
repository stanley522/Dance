package Songs;

import Arrows.Direction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public Note getOnBeatNote(int ticks) {
        var onBeatNote = notes.stream().filter(note -> ((int) ((note.beat) * 500 / tempo)) == ticks - delay).findFirst();
        if (onBeatNote.isEmpty())
            return null;
        return onBeatNote.get();
    }

    public void setScript(String s) {
        script = s;
    }public void setCustomScript(String s) {
        script = s;
    }



    public ArrayList<Note> createNotes(SongDifficulty difficulty) {

        int t = 0;
        char tempC;
        var createNotes = new ArrayList<Note>();
        for (int i = 0; i < script.length(); i++) {
            var c = script.charAt(i);
            if (c == '0')
                t += 4;
            if (c == '.')
                t -= 2;
            if (c == '/')
                t -= 3;
            if (noteTypeMap.containsKey(c)) {
                if (difficulty == SongDifficulty.Custom)
                    createNotes.add(new Note(t, noteTypeMap.get(c)));
                else
                    createNotes.add(new Note(t, difficulty));
                t += 4;
            }
            if (c == 'r') {
                createNotes.add(new Note(t, difficulty));
                t += 4;
            }
        }
        notes = createNotes;
        return notes;
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
            Map.entry('b', NoteType.LeftRight)

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

    public void playMusic() {
        if (clip == null)
            return;
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
        return customScript!=null;
    }
}
