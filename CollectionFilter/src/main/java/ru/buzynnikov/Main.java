package ru.buzynnikov;


import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Filter<Integer> filter = (t) -> t % 2 == 0 ? t : null;
        System.out.println(Arrays.toString(filter(array, filter)));

        String[] strings = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        Filter<String> filter1 = (t) -> t.length() > 3 ? t : null;
        System.out.println(Arrays.toString(filter(strings, filter1)));

    }


    private static <T> T[] filter(T[] array, Filter<T> filter) {
        ArrayList<T> result = new ArrayList<>();
        for (T t : array) {
            T temp = filter.apply(t);
            if (temp == null) continue;
            result.add(filter.apply(t));
        }
        return (T[]) result.stream().toArray(Object[]::new);
    }
}