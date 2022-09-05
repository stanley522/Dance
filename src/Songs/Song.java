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
    ArrayList<Note> notes = new ArrayList<>();
    int delay = 0;
    int skip = 0;
    double tempo = 60;
    int length;
    public Clip clip;

    Song(int l, double t, int d, int s, String musicPath) {
        length = l;
        tempo = t;
        delay = d;
        skip = s;
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
        var onBeatNote = notes.stream().filter(note -> ((int)((note.beat) * 500 / tempo)) == ticks-delay).findFirst();
        if (onBeatNote.isEmpty())
            return null;
        return onBeatNote.get();
    }

    public ArrayList<Note> createNotes(String s) {
        int t = 0;
        char tempC;
        for (int i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if (c == '0')
                t += 4;
            if (c == '.')
                t -= 2;
            if (c == '/')
                t -= 3;
            if (noteTypeMap.containsKey(c)) {
                notes.add(new Note(t, noteTypeMap.get(c)));
                t += 4;
            }
            if (c == 'r') {
                notes.add(new Note(t));
                t += 4;
            }
        }
        return notes;
    }

    Map<Character, NoteType> noteTypeMap = Map.ofEntries(
            Map.entry('a', NoteType.Up)

    );

    public double getTempo() {
        return tempo;
    }

    public int getLength() {
        return length;
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
}
