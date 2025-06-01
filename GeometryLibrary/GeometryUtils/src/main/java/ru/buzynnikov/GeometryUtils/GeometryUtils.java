package ru.buzynnikov.GeometryUtils;

import ru.buzynnikov.shapes.Shape;

public class GeometryUtils {
    public static String compareShapes(Shape shape1, Shape shape2) {
        if (shape1 == null || shape2 == null) {
            return "Ошибка: фигуры не должны быть null";
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
