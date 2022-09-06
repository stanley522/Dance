import Arrows.Arrow;
import Arrows.Direction;
import Arrows.FilledArrow;
import Songs.Album;
import Songs.Song;
import Menu.DanceMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.*;

public class GamePanel extends JPanel implements ActionListener {

    int screenWidth = 600;
    int screenHeight = 600;
    Timer timer;
    int refreshRate = 20;
    DanceMenu menu;

    String highScorePath = "Files/HighScores.txt";
    boolean saveHighScore;
    int highScore;
    Arrow upArrow;
    Arrow downArrow;
    Arrow leftArrow;
    Arrow rightArrow;
    ArrayList<Arrow> fixedArrows;
    ArrayList<Arrow> movingArrows = new ArrayList<>();
    int fixedArrowY = 100;
    Map<Direction, Integer> directionX = Map.ofEntries(
            Map.entry(Direction.Up, 300),
            Map.entry(Direction.Down, 200),
            Map.entry(Direction.Left, 100),
            Map.entry(Direction.Right, 400)
    );
    int arrowMoveTicks = 200;
    int arrowMoveSpeed;
    double arrowSpeedRatio = 1;
    int movingArrowY = 0;
    Song song;
    boolean songPlaying;
    int ticks = 0;
    int score = 0;
    int combo = 0;
    int combo1 = 10;
    int combo2 = 30;
    int combo3 = 60;
    int combo4 = 100;
    int showActionLinger = 8;
    int showActionTicks = 0;
    boolean showMiss;
    boolean showPerfect;
    boolean showGreat;
    boolean showGood;
    int missCount = 0;
    int perfectCount = 0;
    int greatCount = 0;
    int goodCount = 0;
    int perfectMargin = 20;
    int greatMargin = 35;
    int goodMargin = 60;
    int showDirectionLinger = 4;
    boolean showUp;
    int showUpTicks = 0;
    boolean showDown;
    int showDownTicks = 0;
    boolean showLeft;
    int showLeftTicks = 0;
    boolean showRight;
    int showRightTicks = 0;
    boolean showMenu = true;


    GamePanel() {

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setFocusable(true);

        // KeyInput
        addKeyListener(new TetrisKeyAdapter());

        timer = new Timer(refreshRate, this);
        timer.start();
        Album.addSongs();
        menu = new DanceMenu();

        setFixedArrows();

        try {
            var highScoreFile = new File(highScorePath);
            highScoreFile.createNewFile();
            saveHighScore = true;
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            saveHighScore = false;
        }
    }

    private void menuSelected() {
        menu.finishSettings();
        score = 0;
        showMenu = false;
        setSong();
        playSong();
        setHighScoreObject();
    }

    private void setHighScoreObject() {
        try {
            var highScoreJson = String.join(" ", Files.readAllLines(Paths.get(highScorePath)));
            JSONObject highScores;
            if (highScoreJson.isEmpty())
                highScores = new JSONObject();
            else
                highScores = new JSONObject(highScoreJson);


            if (!highScores.has(song.getId()))
                highScores.put(song.getId(), new JSONObject());
            var songScores = highScores.getJSONObject(song.getId());
            if (!songScores.has(menu.getDifficulty().toString()))
                songScores.put(menu.getDifficulty().toString(), 0);

            highScore = highScores.getJSONObject(song.getId()).getInt(menu.getDifficulty().toString());

            FileWriter myWriter = new FileWriter(highScorePath);
            myWriter.write(highScores.toString());
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void endGame() {
        updateHighScore();
        movingArrows.clear();
        ticks = 0;
        score = 0;
        missCount = 0;
        perfectCount = 0;
        greatCount = 0;
        goodCount = 0;
        stopSong();
    }

    public void updateHighScore() {
        if (!saveHighScore)
            return;
        try {
            var highScoreJson = String.join(" ", Files.readAllLines(Paths.get(highScorePath)));

            JSONObject highScores = new JSONObject(highScoreJson);
            var songScores = highScores.getJSONObject(song.getId());
            var songDifficultyScore = songScores.getInt(menu.getDifficulty().toString());

            if (score > songDifficultyScore) {
                songScores.put(menu.getDifficulty().toString(), score);
            }

            FileWriter myWriter = new FileWriter(highScorePath);
            myWriter.write(highScores.toString());
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void setFixedArrows() {
        var upColor = new Color(100, 100, 220);
        upArrow = new FilledArrow(Direction.Up, upColor, new Point(directionX.get(Direction.Up), fixedArrowY), 0);
        var downColor = new Color(220, 220, 100);
        downArrow = new FilledArrow(Direction.Down, downColor, new Point(directionX.get(Direction.Down), fixedArrowY), 0);
        var leftColor = new Color(100, 220, 100);
        leftArrow = new FilledArrow(Direction.Left, leftColor, new Point(directionX.get(Direction.Left), fixedArrowY), 0);
        var rightColor = new Color(220, 60, 100);
        rightArrow = new FilledArrow(Direction.Right, rightColor, new Point(directionX.get(Direction.Right), fixedArrowY), 0);
        fixedArrows = new ArrayList<Arrow>(List.of(
                upArrow, downArrow, leftArrow, rightArrow
        ));
    }

    private void moveArrows() {
        if (movingArrows.size() == 0)
            return;
        movingArrows.forEach(arrow -> arrow.moveArrow(0, (int) -arrowMoveSpeed));
        var removeArrows = new ArrayList<Arrow>();
        for (var arrow : movingArrows) {
            if (arrow.getPosition().y < fixedArrowY - goodMargin) {
                miss();
                removeArrows.add(arrow);
            }
        }
        removeArrows.forEach(arrow -> movingArrows.remove(arrow));
    }

    private void generateMovingArrows() {
        var onBeatNotes = song.getOnBeatNote(ticks);
        int hitTick = ticks + arrowMoveTicks;
        for (var onBeatNote : onBeatNotes
        ) {
            if (onBeatNote == null)
                return;
            if (onBeatNote.up)
                generateMovingArrow(Direction.Up, hitTick);
            if (onBeatNote.down)
                generateMovingArrow(Direction.Down, hitTick);
            if (onBeatNote.left)
                generateMovingArrow(Direction.Left, hitTick);
            if (onBeatNote.right)
                generateMovingArrow(Direction.Right, hitTick);
        }
    }

    private void generateMovingArrow(Direction direction, int arrowTick) {
        movingArrows.add(new FilledArrow(direction, new Color(250, 250, 250),
                new Point(directionX.get(direction), movingArrowY), arrowTick));
    }

    private void setSong() {
        song = menu.getSong();
        arrowSpeedRatio = menu.getSpeed();
        arrowMoveSpeed = (int) (song.getTempo() / 16 * arrowSpeedRatio);
        movingArrowY = fixedArrowY + arrowMoveTicks * arrowMoveSpeed;
        perfectMargin = (int) (3 * Math.pow(arrowMoveSpeed, 0.75));
        greatMargin = (int) (6 * Math.pow(arrowMoveSpeed, 0.75));
        goodMargin = (int) (10 * Math.pow(arrowMoveSpeed, 0.75));
    }

    private void playSong() {
        songPlaying = true;
        song.playMusic(menu.getVolume());
    }

    private void stopSong() {
        songPlaying = false;
        song.stopMusic();
    }

    private void checkArrows(Direction direction) {
        var checkArrows = movingArrows.stream().filter(arrow -> arrow.direction == direction);
        var fitArrow = checkArrows.filter(arrow -> arrow.getPosition().y < fixedArrowY + goodMargin).findFirst();
        if (fitArrow.isEmpty()) {
            miss();
            return;
        }
        var arrow = fitArrow.get();
        var y = Math.abs(arrow.getPosition().y - fixedArrowY);
        if (y < perfectMargin)
            perfect();
        else if (y < greatMargin)
            great();
        else
            good();
        movingArrows.remove(arrow);
    }

    private void miss() {
        combo = 0;
        missCount++;
        clearShowAction();
        showMiss = true;
    }

    private void perfect() {
        combo++;
        perfectCount++;
        clearShowAction();
        showPerfect = true;
        addScore(ArrowPrecision.Perfect);
    }

    private void great() {
        combo++;
        greatCount++;
        clearShowAction();
        showGreat = true;
        addScore(ArrowPrecision.Great);
    }

    private void good() {
        combo++;
        goodCount++;
        clearShowAction();
        showGood = true;
        addScore(ArrowPrecision.Good);
    }

    private void clearShowAction() {
        showActionTicks = 0;
        showMiss = false;
        showPerfect = false;
        showGreat = false;
        showGood = false;
    }

    private void addScore(ArrowPrecision precition) {
        score += precition.multiplier * 100;
        if (combo >= combo4)
            score += 500;
        else if (combo >= combo3)
            score += 200;
        else if (combo >= combo2)
            score += 100;
        else if (combo >= combo1)
            score += 50;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (showMenu) {
            repaint();
        }
        if (songPlaying) {
            tick();
            generateMovingArrows();
            moveArrows();
            repaint();
        }
        if (song == null)
            return;
        if (ticks == song.getLength() * 1000)
            endGame();
    }

    private void tick() {
        ticks++;
        if (showMiss || showPerfect || showGreat || showGood)
            showActionTicks++;
        if (showUp)
            showUpTicks++;
        if (showDown)
            showDownTicks++;
        if (showLeft)
            showLeftTicks++;
        if (showRight)
            showRightTicks++;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (showMenu) {
            showMenu(graphics);
        } else if (songPlaying) {
            paintFixedArrows(graphics);
            paintMovingArrows(graphics);
            showGameInfo(graphics);
        }
    }

    private void showMenu(Graphics graphics) {
        menu.paint(graphics);
    }

    private void paintFixedArrows(Graphics graphics) {
        fixedArrows.forEach(arrow -> arrow.drawArrow(graphics, 4, null));
    }

    private void paintMovingArrows(Graphics graphics) {
        for (int i = movingArrows.size() - 1; i >= 0; i--) {
            var arrow = movingArrows.get(i);
            arrow.fillArrow(graphics);
            arrow.drawArrowOffset(graphics, 2, 1, new Color(0, 0, 0));
        }
    }

    private void showGameInfo(Graphics graphics) {
        showKeyPressed(graphics);
        showGameState(graphics);
        showGameAction(graphics);
        showCombo(graphics);
    }

    private void showKeyPressed(Graphics graphics) {
        if (showUp && showUpTicks != 1)
            upArrow.drawArrowOffset(graphics, 10, 5);
        if (showDown && showDownTicks != 1)
            downArrow.drawArrowOffset(graphics, 10, 5);
        if (showLeft && showLeftTicks != 1)
            leftArrow.drawArrowOffset(graphics, 10, 5);
        if (showRight && showRightTicks != 1)
            rightArrow.drawArrowOffset(graphics, 10, 5);
        if (showUpTicks > showDirectionLinger)
            showUp = false;
        if (showDownTicks > showDirectionLinger)
            showDown = false;
        if (showLeftTicks > showDirectionLinger)
            showLeft = false;
        if (showRightTicks > showDirectionLinger)
            showRight = false;
    }

    private void showGameState(Graphics graphics) {
        showArrowCount(graphics);
        showScore(graphics);
        showHighScore(graphics);
    }

    private void showArrowCount(Graphics graphics) {
        var x = 440;
        var y = 20;

        graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
        graphics.setColor(new Color(200, 200, 60));
        var metrics = graphics.getFontMetrics();
        var text = "Perfect: " + perfectCount;
        var xOffset = metrics.stringWidth(text) / 2;
        x -= xOffset;
        var yOffset = graphics.getFont().getSize() / 2;
        y += yOffset;
        graphics.drawString(text, x, y);

        y += 20;
        graphics.setColor(new Color(60, 200, 120));
        text = "Great: " + greatCount;
        graphics.drawString(text, x, y);

        y += 20;
        graphics.setColor(new Color(120, 120, 200));
        text = "Good: " + goodCount;
        graphics.drawString(text, x, y);

        y += 20;
        graphics.setColor(new Color(200, 60, 60));
        text = "Miss: " + missCount;
        graphics.drawString(text, x, y);

        y -= 60;
        x += 120;
        double total = perfectCount+greatCount+goodCount+ missCount;

        graphics.setColor(new Color(120, 120, 120));
        text = String.format("%.1f",perfectCount*100/total)+"%";
        graphics.drawString(text, x, y);
        y += 20;
        graphics.setColor(new Color(120, 120, 120));
        text = String.format("%.1f",greatCount*100/total)+"%";
        graphics.drawString(text, x, y);
        y += 20;
        graphics.setColor(new Color(120, 120, 120));
        text = String.format("%.1f",goodCount*100/total)+"%";
        graphics.drawString(text, x, y);
        y += 20;
        graphics.setColor(new Color(120, 120, 120));
        text = String.format("%.1f",missCount*100/total)+"%";
        graphics.drawString(text, x, y);
    }

    private void showScore(Graphics graphics) {
        var x = 20;
        var y = 40;
        graphics.setColor(new Color(220, 150, 60));
        graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
        var metrics = graphics.getFontMetrics();
        var text = "Score: " + score;
        var xOffset = 0;
        var yOffset = graphics.getFont().getSize() / 2;
        graphics.drawString(text, x - xOffset, y + yOffset);
    }

    private void showHighScore(Graphics graphics) {
        var x = 20;
        var y = 75;
        graphics.setColor(new Color(20, 150, 60));
        graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
        var metrics = graphics.getFontMetrics();
        var text = "HighScore: " + highScore;
        var xOffset = 0;
        var yOffset = graphics.getFont().getSize() / 2;
        graphics.drawString(text, x - xOffset, y + yOffset);
    }

    private void showGameAction(Graphics graphics) {
        var x = 290;
        var y = 400;
        if (showMiss) {
            graphics.setColor(new Color(200, 60, 60));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
            var metrics = graphics.getFontMetrics();
            var text = "-MISS-";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        }
        if (showPerfect) {
            graphics.setColor(new Color(200, 200, 60));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 40));
            var metrics = graphics.getFontMetrics();
            var text = "PERFECT!";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        }
        if (showGreat) {
            graphics.setColor(new Color(60, 200, 120));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
            var metrics = graphics.getFontMetrics();
            var text = "Great";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        }
        if (showGood) {
            graphics.setColor(new Color(120, 120, 120));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
            var metrics = graphics.getFontMetrics();
            var text = "Good";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        }
        if (showActionTicks > showActionLinger) {
            clearShowAction();
        }
    }

    private void showCombo(Graphics graphics) {
        var x = 290;
        var y = 460;
        if (combo >= combo4) {
            graphics.setColor(new Color(240, 180, 80));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 46));
            var metrics = graphics.getFontMetrics();
            var text = combo + " COMBO!!!!";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        } else if (combo >= combo3) {
            graphics.setColor(new Color(220, 150, 60));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 40));
            var metrics = graphics.getFontMetrics();
            var text = combo + " Combo!!";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        } else if (combo >= combo2) {
            graphics.setColor(new Color(200, 120, 40));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
            var metrics = graphics.getFontMetrics();
            var text = combo + " Combo!";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        } else if (combo >= combo1) {
            graphics.setColor(new Color(180, 90, 20));
            graphics.setFont(new Font("TimesNewRoman", Font.PLAIN, 36));
            var metrics = graphics.getFontMetrics();
            var text = combo + " Combo";
            var xOffset = metrics.stringWidth(text) / 2;
            var yOffset = graphics.getFont().getSize() / 2;
            graphics.drawString(text, x - xOffset, y + yOffset);
        }
    }

    public class TetrisKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            var key = e.getKeyCode();
            if (showMenu) {
                switch (key) {
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                        menu.getCurrentMenu().next();
                    }
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                        menu.getCurrentMenu().last();
                    }
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                        menu.last();
                    }
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                        menu.next();
                    }
                    case KeyEvent.VK_ENTER -> {
                        menuSelected();
                    }
                }
                return;
            }
            switch (key) {
                case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                    checkArrows(Direction.Up);
                    showUp = true;
                    showUpTicks = 0;
                }
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                    checkArrows(Direction.Down);
                    showDown = true;
                    showDownTicks = 0;
                }
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                    checkArrows(Direction.Left);
                    showLeft = true;
                    showLeftTicks = 0;
                }
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                    checkArrows(Direction.Right);
                    showRight = true;
                    showRightTicks = 0;
                }
                case KeyEvent.VK_ESCAPE -> {
                    endGame();
                    showMenu = true;
                }
            }
        }
    }

}
