package Arrows;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilledArrow extends Arrow {
    public FilledArrow(Direction d, Color c, Point p, int tick) {
        super(d, c, p,tick);
    }

    @Override
    public void setOriginPoints() {
        rotationPoint = new Point(20,20);
        originPoints = new ArrayList<>(List.of(
                new Point(12, 4),
                new Point(12, 16),
                new Point(4, 16),
                new Point(0, 20),
                new Point(20, 40),
                new Point(40, 20),
                new Point(36, 16),
                new Point(28, 16),
                new Point(28, 4)
        ));
        super.setOriginPoints();
    }
}

