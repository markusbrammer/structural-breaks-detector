package fitness;

import javafx.scene.Node;

public abstract class FitnessNode {

    // Placement of node.
    private double x;
    private double y;
    private double height;
    private double width;

    public abstract Node getVisual();

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    protected void setHeight(double height) {
        this.height = height;
    }

    protected void setWidth(double width) {
        this.width = width;
    }
}
