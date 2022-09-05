package Arrows;

import java.awt.*;
import java.util.ArrayList;

public class Arrow {
    protected ArrayList<Point> originPoints;
    protected ArrayList<Point> directedPoints;
    protected Point rotationPoint;
    public Direction direction;
    private Color color;
    protected Point position;
    private int sizeRatio = 2;
    int offset = 0;
    int tick;

    public Arrow(Direction d, Color c, Point p, int t) {
        direction = d;
        color = c;
        position = p;
        tick = t;
        setOriginPoints();
        setDirectedPoints();
    }

    public Arrow(Direction d, Color c, Point p, int t, int o) {
        direction = d;
        color = c;
        position = p;
        tick = t;
        offset = o;
        setOriginPoints();
        setDirectedPoints();
    }

    protected void setOriginPoints() {
        originPoints = setOffset(offset, directedPoints);
    }

    protected ArrayList<Point> setOffset(int offset, ArrayList<Point> points) {
        if (offset == 0)
            return originPoints;
        var offsetPoints = new ArrayList<Point>();
        for (var point : points) {
            var offsetPoint = new Point();
            if (point.x > rotationPoint.x && point.x + offset > rotationPoint.x) {
                offsetPoint.x = point.x + offset;
            } else if (point.x < rotationPoint.x && point.x - offset < rotationPoint.x) {
                offsetPoint.x = point.x - offset;
            } else {
                offsetPoint.x = rotationPoint.x;
            }

            if (point.y > rotationPoint.y && point.y + offset > rotationPoint.y) {
                offsetPoint.y = point.y + offset;
            } else if (point.y < rotationPoint.y && point.y - offset < rotationPoint.y) {
                offsetPoint.y = point.y - offset;
            } else {
                offsetPoint.y = rotationPoint.y;
            }
            offsetPoints.add(offsetPoint);
        }
        return offsetPoints;
    }

    public void setDirectedPoints() {
        var directedPoints = new ArrayList<Point>();
        for (int i = 0; i < originPoints.size(); i++) {
            var p = new Point();
            p.x = (((originPoints.get(i).x - rotationPoint.x) * direction.xofxVector) +
                    ((originPoints.get(i).y - rotationPoint.y) * direction.xofyVector)
                    + rotationPoint.x) * sizeRatio;
            p.y = (((originPoints.get(i).x - rotationPoint.x) * direction.yofxVector) +
                    ((originPoints.get(i).y - rotationPoint.y) * direction.yofyVector)
                    + rotationPoint.y) * sizeRatio;
            directedPoints.add(p);
        }
        rotationPoint.x *= sizeRatio;
        rotationPoint.y *= sizeRatio;

        this.directedPoints = directedPoints;
    }

    public void drawArrow(Graphics graphics, int stroke) {
        if (originPoints == null)
            return;
        graphics.setColor(color);
        var graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(stroke));
        var p = new Polygon();
        for (var point : directedPoints
        ) {
            p.addPoint(point.x + position.x, point.y + position.y);

        }
        graphics2D.drawPolygon(p);
    }

    public void drawArrow(Graphics graphics) {
        drawArrow(graphics, 10);
    }

    public void fillArrow(Graphics graphics) {
        if (originPoints == null)
            return;
        graphics.setColor(color);
        var p = new Polygon();
        for (var point : directedPoints
        ) {
            p.addPoint(point.x + position.x, point.y + position.y);

        }
        graphics.fillPolygon(p);
    }

    public void drawArrowOffset(Graphics graphics, int stroke, int offset, Color color) {
        if (originPoints == null)
            return;
        graphics.setColor(color);
        var graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(stroke));
        var p = new Polygon();
        for (var point : setOffset(offset, directedPoints)
        ) {
            p.addPoint(point.x + position.x, point.y + position.y);

        }
        graphics2D.drawPolygon(p);
    }

    public void drawArrowOffset(Graphics graphics, int stroke, int offset) {
        drawArrowOffset(graphics, stroke, offset,
                new Color(color.getRed() + 20, color.getGreen() + 20, color.getBlue() + 20, 200));
    }


    public void moveArrow(int x, int y) {
        position.translate(x, y);
    }

    public void setArrowPosition(Point point) {
        position = point;
    }

    public Point getPosition() {
        return position;
    }
}
