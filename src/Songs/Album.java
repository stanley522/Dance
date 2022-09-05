package Songs;

import java.util.ArrayList;

public class Album {
    static boolean isDebug = true;
    public static ArrayList<Song> songs = new ArrayList<>();

    public Album() {
        addSongs();
    }

    public static void addSongs() {
        songs = new ArrayList<Song>();
        //add_Baby();
        add_MayDay_Embrace();
    }

    //https://smusic110.pixnet.net/blog/post/73469748-%E3%80%90%E9%8B%BC%E7%90%B4%E8%AD%9C%E3%80%91%E6%93%81%E6%8A%B1_%E4%BA%94%E6%9C%88%E5%A4%A9
    private static void add_MayDay_Embrace() {
        var path = "music/Mayday_Embrace.wav";
        if (isDebug) {
            path = "music/Mayday_Embrace.wav";
        }
        var song = new Song(305, 124.07, 18, 24, path);

        song.notes = song.createNotes(
                //"r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0rrrrrrrrrrrrrrr"+
                "rrrrr.rr.00 r.rrr0.r.rr.00 rrrrr.rr.00" +
                        "r.rrrr.r.rr.00 rrrrr.rr.00 r.rr0r.r.rr.00" +
                        "rrrrr.rr.00 r.rrrr.r.rr.00 0.rr.r.r.00.rr.r.r.0" +
                        "rrrrr.rr.00 0.rr.r.r.00.rr.r0" +
                        "rrrrr.rr.00 0.rr.r.r.r.r.r.rr.00" +
                        "r.0r.0rr.rr00. 0.r.r.r.r00000 00000.rr0" +
                        "0.r.r.r.r00000 00000.r0.r0 0.rr.r00.r.0r.0r." +
                        "r0000r.r.00 r0r0r000 r0r0r000" +
                        "r0r0r000 rrr.r.r" +
                        //mid
                        "r000r000r00r.r.r.r." +
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

    //https://m.hqgq.xiyogo.com/qupu/27230.html
    private static void add_Baby() {
        var path = "music/Baby.wav";
        if (isDebug) {
            path = "music/Baby.wav";
        }
        var song = new Song(150, 124.1, 142, 3, path);

        song.notes = song.createNotes(
                //"r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0r0rrrrrrrrrrrrrrr"+

                "0000 0000 rrrr r.r.r.r.rr " +
                        "rrrr rrrr.r. rrrr r.r.r.r.rr rrrr " +
                        "rrrr.r. rrrr r.r.rrr.r. rrrr.r. 00.r.0r.r. " +
                        "rrrr r.r.rrr.r. rrrr r000 000r.r. rrrr" +
                        "r.r.r.r.rr rrrr rrrr.r. rrrr" +
                        "r.r.r.r.rr rrrr r.rrr.r.r. rrrr r.r.rrr.r." +
                        "rrrr.r. 00.r.0r.r. rrrr r.r.rrr.r. rrrr" +
                        "r000 000r.r. rrrr r.r.rrr.r. rrrr.r." +
                        "0r.r.0r.r. rrrr r.r.rrr.r. rrrr r000"
        );
        songs.add(song);
    }


}
