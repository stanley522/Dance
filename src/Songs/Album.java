package Songs;

import java.util.ArrayList;

public class Album {
    static boolean isDebug = false;
    public static ArrayList<Song> songs = new ArrayList<>();

    public Album() {
        addSongs();
    }

    public static void addSongs() {
        songs = new ArrayList<Song>();
        //add_Baby();
        add_MayDay_Embrace();
    }

    private static void add_MayDay_Embrace() {
        var path = "music/Mayday_Embrace.wav";
        if (isDebug) {
            path = "src/music/Mayday_Embrace.wav";
        }
        var song = new Song(305, 186/*-0.5*/, 37, 25, path);

        song.notes = song.createNotes(
                //"r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0rrrrrrrrrrrrrrr"+
                "rrrrr.rr.00 r.rrr0.r.rr.00 rrrrr.rr.00" +
                        "r.rrrr.r.rr.00 rrrrr.rr.00 r.rr0r.r.rr.00" +
                        "rrrrr.rr.00 r.rrrr.r.rr.00 0.rr.r.r.00.rr.r.r.0" +
                        "rrrrr.rr.00 0.rr.r.r.00.rr.r0" +
                        "rrrrr.rr.00 0.rr.r.r.r.r.r.rr.00" +
                        "r.0r.0rr.rr00. 0.r.r.r.r00000 00000.rr0" +
                        "0.r.r.r.r00000 00000.r0.r0 0.rr.r00.r.0r.0r." +
                        "r0000r.r.00 00000000 00000000" +
                        "00000000 0000"+
                        //mid
                        "0000000000000."+
                        //repeat
                        "rrrrr.rr.00 r.rrr0.r.rr.00 rrrrr.rr.00" +
                        "r.rrrr.r.rr.00 rrrrr.rr.00 r.rr0r.r.rr.00" +
                        "rrrrr.rr.00 r.rrrr.r.rr.00 0.rr.r.r.00.rr.r.r.0" +
                        "rrrrr.rr.00 0.rr.r.r.00.rr.r0" +
                        "rrrrr.rr.00 0.rr.r.r.r.r.r.rr.00" +
                        "r.0r.0rr.rr00. 0.r.r.r.r00000 00000.rr0" +
                        "0.r.r.r.r00000 00000.r0.r0 0.rr.r00.r.0r.0r." +
                        "r0000r.r.00 00000000 00000000"
        );
        songs.add(song);
    }

    private static void add_Baby() {
        var path = "music/Baby.wav";
        if (isDebug) {
            path = "src/music/Baby.wav";
        }
        var song = new Song(150, 124, -3,10, path);

        song.notes = song.createNotes(
                "0000000000000000000000000" +
                        "0000000r.r.rrrrr.r.r.r.rrrrrr" +
                        "rrrr.r.rrrrr.r.r.r.rrrrrrrrrr.r." +
                        "rrrrr.r.rrr.r.rrrr.r.00.r.0r.r.rrrr" +
                        "r.r.rrr.r.rrrrr000000r.r." +
                        "rrrrr.r.r.r.rrrrrr" +
                        "rrrr.r.rrrrr.r.r.r.rrrrrrrrrr.r." +
                        "rrrrr.r.rrr.r.rrrr.r.00.r.0r.r.rrrr" +
                        "r.r.rrr.r.rrrrr000000r.r.000r.r." +
                        "rrrrr.r.rrr.r.rrrr.r.0r.r.0r.r.rrrr" +
                        "r.r.rrr.r.rrrrr000000r.r.r0.r.0rr0.r.0r" +
                        "0.r.000rr.r.0r.r.0000000.r.r.r.0000000r.r." +
                        "rrrrr.r.rrr.r.rrrr.r.00.r.0r.r.rrrr" +
                        "r.r.rrr.r.rrrrr000000r.r.rrrrr000" +
                        ""
        );
        songs.add(song);
    }


}
