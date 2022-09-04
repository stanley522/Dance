package Songs;

public enum BeatType {
    Single(1),
    Double(2),
    Triple(3);
    final  int count;

    BeatType(int c) {
        count = c;
    }
}
