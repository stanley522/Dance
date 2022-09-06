package Songs;

import java.util.Random;

public enum NoteType {
    Up(true, false, false, false, 25, 20, 14, 10),
    Down(false, true, false, false, 25, 20, 14,10),
    Left(false, false, true, false, 25, 20, 14,10),
    Right(false, false, false, true, 25, 20, 14,10),
    UpLeft(true, false, true, false, 0, 3, 6,10),
    UpRight(true, false, false, true, 0, 3, 6,10),
    DownLeft(false, true, true, false, 0, 3, 6,10),
    DownRight(false, true, false, true, 0, 3, 6,10),
    UpDown(true, true, false, false, 0, 4, 10,10),
    LeftRight(false, false, true, true, 0, 4, 10,10);

    final boolean up;
    final boolean down;
    final boolean left;
    final boolean right;
    final int probrobilityEasy;
    final int probrobilityMedium;
    final int probrobilityHard;
    final int probrobilityCrazy;

    public int getProbrobility(SongDifficulty difficulty) {
        switch (difficulty) {
            case Easy -> {
                return probrobilityEasy;
            }
            case Medium -> {
                return probrobilityMedium;
            }
            case Hard -> {
                return probrobilityHard;
            }
            case Crazy -> {
                return probrobilityCrazy;
            }
        }
        return probrobilityEasy;
    }

    NoteType(boolean up, boolean down, boolean left, boolean right, int pEasy, int pMid, int pHard, int pCrazy) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        probrobilityEasy = pEasy;
        probrobilityMedium = pMid;
        probrobilityHard = pHard;
        probrobilityCrazy = pCrazy;
    }

    public static NoteType randomNoteType(SongDifficulty difficulty){
        var n = new Random().nextInt(100);
        var types = NoteType.values();
        NoteType type = NoteType.Up;

        for (NoteType noteType : types) {

            if (n < noteType.getProbrobility(difficulty)) {
                type = noteType;
                break;
            }
            n -= noteType.getProbrobility(difficulty);
        }

        return type;
    }
}
