package ru.buzynnikov;

public interface Filter<T> {
    T apply(T t);
}
