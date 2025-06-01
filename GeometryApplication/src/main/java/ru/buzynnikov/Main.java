package ru.buzynnikov;

import ru.buzynnikov.geometrylib.Circle;
import ru.buzynnikov.geometrylib.Rectangle;
import ru.buzynnikov.geometrylib.Shape;
import ru.buzynnikov.geometrylib.Triangle;

public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(10);
        System.out.println(circle);
        Shape triangle = new Triangle(10, 10, 10);
        System.out.println(triangle);
        Shape rectangle = new Rectangle(20, 20);
        System.out.println(rectangle);
    }
}