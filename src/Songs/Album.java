package Songs;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Album {
    static boolean isDebug = true;
    public static ArrayList<Song> songs = new ArrayList<>();

    public Album() {
        addSongs();
    }

    public static void addSongs() {
        songs = new ArrayList<Song>();
        add_Baby();
        add_MayDay_Embrace();
    }

    //https://smusic110.pixnet.net/blog/post/73469748-%E3%80%90%E9%8B%BC%E7%90%B4%E8%AD%9C%E3%80%91%E6%93%81%E6%8A%B1_%E4%BA%94%E6%9C%88%E5%A4%A9
    private static void add_MayDay_Embrace() {
        var path = "Music/Mayday_Embrace.wav";
        var scriptPath = "Scripts/Mayday_Embrace.txt";
        var customScriptPath = "Scripts/Mayday_Embrace_Custom.txt";

        var song = new Song(305, 124, 19, 24, path, "擁抱", "Embrace");

        if (!scriptPath.isEmpty()) {
            try {
                var text = Files.readAllLines(Paths.get(scriptPath));
                var script =String.join(" ", text);
                song.setScript(script);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!customScriptPath.isEmpty()){
            try{
                var file = Files.readAllLines(Paths.get(customScriptPath));
                var script =String.join(" ", file);
                song.setCustomScript(script);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        songs.add(song);
    }

    //https://m.hqgq.xiyogo.com/qupu/27230.html
    private static void add_Baby() {
        var musicPath = "Music/Baby.wav";
        var scriptPath = "Scripts/Baby.txt";
        var customScriptPath = "Scripts/Baby_Custom.txt";

        var song = new Song(150, 123.0, 144, 3, musicPath, "寶貝", "Baby");

        if (!scriptPath.isEmpty()) {
            try {
                var file = Files.readAllLines(Paths.get(scriptPath));
                var script =String.join(" ", file);
                song.setScript(script);
            }
            catch (Exception exception) {
            }
        }
        if (!customScriptPath.isEmpty()){
            try{
                var file = Files.readAllLines(Paths.get(customScriptPath));
                var script =String.join(" ", file);
                song.setCustomScript(script);

            }
            catch (Exception exception){

            }
        }
        songs.add(song);
    }


}
