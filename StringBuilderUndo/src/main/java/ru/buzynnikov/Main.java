package ru.buzynnikov;

public class Main {
    public static void main(String[] args) {
        MyStringBuilder stringBuilder = new MyStringBuilder(new StringBuilder("Hello"));
        stringBuilder.append(" World");
        System.out.println(stringBuilder.toString()); // Hello World
        stringBuilder.undo();
        System.out.println(stringBuilder.toString()); // Hello
        stringBuilder.delete(0, 3);
        System.out.println(stringBuilder.toString()); // lo
        stringBuilder.undo();
        System.out.println(stringBuilder.toString()); // Hello
    }
}