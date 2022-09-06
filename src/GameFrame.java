import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame() {
        var p = new GamePanel();
        this.add(p);
        this.setTitle("Dance");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                p.updateHighScore();
            }
        });
    }
}
