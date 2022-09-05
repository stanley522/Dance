package Songs;

public enum NoteType {
    Up(true, false, false, false, 25, 20, 14),
    Down(false, true, false, false, 25, 20, 14),
    Left(false, false, true, false, 25, 20, 14),
    Right(false, false, false, true, 25, 20, 14),
    UpLeft(true, false, true, false, 0, 3, 6),
    UpRight(true, false, false, true, 0, 3, 6),
    DownLeft(false, true, true, false, 0, 3, 6),
    DownRight(false, true, false, true, 0, 3, 6),
    UpDown(true, true, false, false, 0, 4, 10),
    LeftRight(false, false, true, true, 0, 4, 10);

    final boolean up;
    final boolean down;
    final boolean left;
    final boolean right;
    final int probrobilityEasy;
    final int probrobilityMedium;
    final int probrobilityHard;

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
        }
        return probrobilityEasy;
    }

    NoteType(boolean up, boolean down, boolean left, boolean right, int pEasy, int pMid, int pHard) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        probrobilityEasy = pEasy;
        probrobilityMedium = pMid;
        probrobilityHard = pHard;
    }
}
