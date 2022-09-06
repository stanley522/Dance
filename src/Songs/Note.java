package Songs;

import java.util.Random;

public class Note {
    double beat;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    Note(double t, NoteType type) {
        beat = t;
        this.up = type.up;
        this.down = type.down;
        this.left = type.left;
        this.right = type.right;
    }
}
