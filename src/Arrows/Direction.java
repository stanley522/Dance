package Arrows;

public enum Direction {
    Up(-1,0,0,-1),
    Down(1,0,0,1),
    Left(0,-1,1,0),
    Right(0,1,-1,0);
    final int xofxVector;
    final int xofyVector;
    final int yofxVector;
    final int yofyVector;
    Direction(int xx,int xy,int yx,int yy){
        xofxVector = xx;
        xofyVector = xy;
        yofxVector = yx;
        yofyVector = yy;
    }

}
