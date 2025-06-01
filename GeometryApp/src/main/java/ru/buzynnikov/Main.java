package ru.buzynnikov;

import ru.buzynnikov.GeometryUtils.GeometryUtils;
import ru.buzynnikov.shapes.Circle;
import ru.buzynnikov.shapes.Rectangle;
import ru.buzynnikov.shapes.Shape;
import ru.buzynnikov.shapes.Triangle;

public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(10);
        System.out.println(circle);
        Shape triangle = new Triangle(10, 10, 10);
        System.out.println(triangle);
        Shape rectangle = new Rectangle(20, 20);
        System.out.println(rectangle);

        System.out.println(GeometryUtils.compareShapes(circle, rectangle));
    }
}