package Menu;

import Songs.Album;
import Songs.Song;
import Songs.SongDifficulty;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DanceMenu {
    ArrayList<DanceMenuItem> menuList;
    DanceMenuItem currentMenu;
    int index = 0;
    SongMenu songs;
    DifficultyMenu difficulty;
    ArrowSpeedMenu speeds;
    Point position = new Point(300, 250);
    Song selectedSong;

    public DanceMenu() {
        songs = new SongMenu();
        difficulty = new DifficultyMenu();
        speeds = new ArrowSpeedMenu();
        menuList = new ArrayList<DanceMenuItem>(List.of(
                songs, difficulty, speeds
        ));
        currentMenu = menuList.get(index);
    }

    public void finishSettings() {
        selectedSong = songs.getSong();
        selectedSong.createNotes(difficulty.getDifficulty());
    }

    public Song getSong() {
        return songs.getSong();
    }

    public double getSpeed() {
        return speeds.getSpeed();
    }

    public void next() {
        if (index < menuList.size() - 1)
            index++;
        currentMenu = menuList.get(index);
    }

    public void last() {
        if (index > 0)
            index--;
        currentMenu = menuList.get(index);
    }

    public int getIndex() {
        return index;
    }

    public DanceMenuItem getCurrentMenu() {
        return currentMenu;
    }

    public void paint(Graphics graphics) {
        paintCenter(graphics);
        paintLeftRight(graphics);
        paintTopBottom(graphics);
        paintWarning(graphics);
    }

    private void paintCenter(Graphics graphics) {
        var paintMenu = menuList.get(index);
        graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
        graphics.setColor(new Color(100, 200, 250));
        graphics.drawString(paintMenu.infoString,
                position.x + 20 - paintMenu.width / 2, position.y + 20);
        graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
        graphics.setColor(new Color(220, 200, 150));
        var offset = graphics.getFontMetrics().stringWidth(paintMenu.displayString) / 2;
        graphics.drawString(paintMenu.displayString,
                position.x + 20 - offset, position.y + 60);
    }

    private void paintLeftRight(Graphics graphics) {
        var offsetCenter = menuList.get(index).width / 2;
        if (index > 0) {
            var leftPaintMenu = menuList.get(index - 1);
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
            graphics.setColor(new Color(100, 200, 250, 100));
            graphics.drawString(leftPaintMenu.infoString,
                    position.x + 20 - leftPaintMenu.width - offsetCenter, position.y + 20);
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 24));
            graphics.setColor(new Color(220, 200, 150, 100));
            var offset = graphics.getFontMetrics().stringWidth(leftPaintMenu.displayString) / 2;
            graphics.drawString(leftPaintMenu.displayString,
                    position.x + 20 - leftPaintMenu.width/2 - offsetCenter-offset, position.y + 50);
        }
        if (index < menuList.size() - 1) {
            var rightPaintMenu = menuList.get(index + 1);
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
            graphics.setColor(new Color(100, 200, 250, 100));
            graphics.drawString(rightPaintMenu.infoString,
                    position.x + 20 + offsetCenter, position.y + 20);
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 24));
            graphics.setColor(new Color(220, 200, 150, 100));
            var offset = graphics.getFontMetrics().stringWidth(rightPaintMenu.displayString) / 2;
            graphics.drawString(rightPaintMenu.displayString,
                    position.x + 20 + offset+ rightPaintMenu.width/2+offsetCenter, position.y + 50);
        }
    }

    private void paintTopBottom(Graphics graphics) {
        var paintMenu = menuList.get(index);
        if (paintMenu.nextDisplayString != null) {
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 24));
            graphics.setColor(new Color(220, 200, 150, 100));
            var offset = graphics.getFontMetrics().stringWidth(paintMenu.nextDisplayString) / 2;
            graphics.drawString(paintMenu.nextDisplayString,
                    position.x + 20 - offset, position.y + 50 - 100);
        }
        if (paintMenu.lastDisplayString != null) {
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 24));
            graphics.setColor(new Color(220, 200, 150, 100));
            var offset = graphics.getFontMetrics().stringWidth(paintMenu.lastDisplayString) / 2;
            graphics.drawString(paintMenu.lastDisplayString,
                    position.x + 20 - offset, position.y + 50 + 100);
        }
    }

    private void paintWarning(Graphics graphics) {
        if (!songs.songs.get(songs.index).hasCustom() && difficulty.difficultyMap.get(difficulty.index) == SongDifficulty.Custom) {
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 24));
            graphics.setColor(new Color(220, 50, 100));
            var text = "Current song does not have Custom";
            var offset = graphics.getFontMetrics().stringWidth(text) / 2;
            graphics.drawString(text, position.x - offset, position.y + 150);
        }
    }

    public static class DanceMenuItem {
        String infoString;
        String displayString;
        String nextDisplayString;
        String lastDisplayString;
        int width;

        DanceMenuItem() {

        }

        public void next() {

        }

        public void last() {

        }
    }

    public static class SongMenu extends DanceMenuItem {
        int index;
        ArrayList<Song> songs = Album.songs;

        SongMenu() {
            infoString = "Song";
            width = 200;
            updateInfo();
        }

        @Override
        public void next() {
            if (index == songs.size() - 1)
                return;
            index++;
            updateInfo();
        }

        @Override
        public void last() {
            if (index == 0)
                return;
            index--;
            updateInfo();
        }

        public void updateInfo() {
            displayString = songs.get(index).getName();
            if (index == songs.size() - 1)
                nextDisplayString = null;
            else
                nextDisplayString = songs.get(index + 1).getName();
            if (index == 0)
                lastDisplayString = null;
            else
                lastDisplayString = songs.get(index - 1).getName();

        }

        public Song getSong() {
            return songs.get(index);
        }
    }

    public static class DifficultyMenu extends DanceMenuItem {
        int index = 2;
        ArrayList<Song> songs = Album.songs;
        Map<Integer, SongDifficulty> difficultyMap = Map.ofEntries(
                Map.entry(1, SongDifficulty.Custom),
                Map.entry(2, SongDifficulty.Easy),
                Map.entry(3, SongDifficulty.Medium),
                Map.entry(4, SongDifficulty.Hard)
        );

        DifficultyMenu() {
            infoString = "Difficulty";
            width = 200;
            updateInfo();
        }

        @Override
        public void next() {
            if (index == 4)
                return;
            index++;
            updateInfo();
        }

        @Override
        public void last() {
            if (index == 1)
                return;
            index--;
            updateInfo();
        }

        public void updateInfo() {
            displayString = difficultyMap.get(index).toString();
            if (index == 4)
                nextDisplayString = null;
            else
                nextDisplayString = difficultyMap.get(index + 1).toString();
            if (index == 1)
                lastDisplayString = null;
            else
                lastDisplayString = difficultyMap.get(index - 1).toString();

        }

        public SongDifficulty getDifficulty() {
            return difficultyMap.get(index);
        }
    }

    public static class ArrowSpeedMenu extends DanceMenuItem {
        int index = 3;
        ArrayList<Double> speeds = new ArrayList<Double>(List.of(
                0.5d, 0.6d, 0.8d, 1d, 1.5d, 2d, 2.5d, 3d, 4d,5d,10d
        ));

        ArrowSpeedMenu() {
            infoString = "Speed";
            width = 200;
            updateInfo();
        }

        @Override
        public void next() {
            if (index == speeds.size() - 1)
                return;
            index++;
            updateInfo();
        }

        @Override
        public void last() {
            if (index == 0)
                return;
            index--;
            updateInfo();
        }

        public void updateInfo() {
            displayString = speeds.get(index).toString();

            if (index == speeds.size() - 1)
                nextDisplayString = null;
            else
                nextDisplayString = speeds.get(index + 1).toString();
            if (index == 0)
                lastDisplayString = null;
            else
                lastDisplayString = speeds.get(index - 1).toString();

        }

        public double getSpeed() {
            return speeds.get(index);
        }
    }
}
