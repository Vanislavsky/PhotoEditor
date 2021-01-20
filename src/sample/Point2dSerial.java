package sample;

import javafx.geometry.Point2D;

import java.io.Serializable;

class Point2dSerial extends Point2D implements Serializable {

    public Point2dSerial(double x, double y) {
        super(x, y);
    }
}