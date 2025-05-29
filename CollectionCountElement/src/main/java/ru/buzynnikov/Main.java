package ru.buzynnikov;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Integer[] arr = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6,6,6,6,6,6,7,7,7,7,7,7,7};
        System.out.println(getCountElement(arr));
    }



    private static <T> Map<T, Long> getCountElement(T[] arr) {
        return Arrays.stream(arr)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

}