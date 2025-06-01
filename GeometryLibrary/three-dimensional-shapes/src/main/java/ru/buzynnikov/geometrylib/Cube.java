package ru.buzynnikov.geometrylib;

public class Cube implements ThreeDimensionalShape {
    private final double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double calculateVolume() {
        return side * side * side;
    }

    @Override
    public double calculateSurfaceArea() {
        return 6 * side * side;
    }

    @Override
    public String toString() {
        return String.format("Куб [сторона=%.2f, объем=%.2f, площадь поверхности=%.2f]",
                side, calculateVolume(), calculateSurfaceArea());
    }
}
