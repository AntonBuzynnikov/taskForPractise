package ru.buzynnikov.geometrylib;

public class GeometryUtils {
    public static String compareShapes(Shape shape1, Shape shape2) {
        if (shape1 == null || shape2 == null) {
            return "Невозможно сравнить фигуры, так как одна или обе фигуры равны null";
        }
        double area1 = shape1.calculateArea();
        double area2 = shape2.calculateArea();

        if (area1 > area2) {
            return "Первая фигура больше";
        } else if (area1 < area2) {
            return "Вторая фигура больше";
        } else {
            return "Фигуры равны по площади";
        }
    }
}
