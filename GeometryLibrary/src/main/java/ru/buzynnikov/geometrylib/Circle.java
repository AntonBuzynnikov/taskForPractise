package ru.buzynnikov.geometrylib;

public class Circle implements Shape{

    private final double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть больше нуля");
        }
        this.radius = radius;
    }


    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double calculatePerimeter() {
        return Math.PI * 2 * radius;
    }

    @Override
    public String toString() {
        return String.format("Круг [радиус=%.2f, площадь=%.2f, периметр=%.2f]",
                radius, calculateArea(), calculatePerimeter());
    }
}
