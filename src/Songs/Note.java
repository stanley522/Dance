package Songs;

import java.util.Random;

public class Note {
    int beat;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    Note(int t, SongDifficulty difficulty) {
        beat = t;
        var n = new Random().nextInt(100);
        var types = NoteType.values();
        NoteType type = NoteType.Up;
        for (int i = 0; i < types.length; i++) {

            if (n < types[i].getProbrobility(difficulty)) {
                type = types[i];
                break;
            }
            n -= types[i].getProbrobility(difficulty);
        }
        this.up = type.up;
        this.down = type.down;
        this.left = type.left;
        this.right = type.right;
    }

    Note(int t, NoteType type) {
        beat = t;
        this.up = type.up;
        this.down = type.down;
        this.left = type.left;
        this.right = type.right;
    }
}
