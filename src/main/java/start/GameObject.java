package start;

public abstract class GameObject {

    private int xPosition;
    private int yPosition;

    public void setX(int x) {
        this.xPosition = x;
    }

    public double getX() {
        return xPosition;
    }

    public void setY(int y) {
        this.yPosition = y;
    }

    public double getY() {
        return yPosition;
    }
}
