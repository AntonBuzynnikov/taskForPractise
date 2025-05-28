package ru.buzynnikov;

import java.util.Stack;

/**
 * Класс {@code MyStringBuilder} представляет собой расширенную версию стандартного класса {@link StringBuilder},
 * дополненного возможностью отмены последних изменений (undo).
 */
public class MyStringBuilder{

    /**
     * Основной объект типа {@link StringBuilder}, используемый для построения строковых значений.
     */
    private final StringBuilder sb;

    /**
     * Стек, хранящий историю предыдущих состояний строки, используемых для реализации операции отмены.
     */
    private final Stack<String> history = new Stack<>();

    public MyStringBuilder(StringBuilder sb) {
        this.sb = sb;
    }

    /**
     * Добавляет строку в конец текущего содержимого и сохраняет предыдущее состояние перед изменением.
     *
     * @param str добавляемая строка
     * @return ссылка на этот же объект {@code MyStringBuilder} для цепочки методов
     */
    public StringBuilder append(String str){
        this.addToHistory();
        return this.sb.append(str);
    }

    /**
     * Преобразует содержимое текущего экземпляра в строку.
     *
     * @return представление строки в виде обычного Java String
     */
    public String toString(){
        return this.sb.toString();
    }

    /**
     * Удаляет символы между указанными индексами (включительно начало, исключая конец)
     * и сохраняет предыдущее состояние перед удалением.
     *
     * @param start начальная позиция удаления включительно
     * @param end   конечная позиция удаления исключительно
     * @return ссылка на этот же объект {@code MyStringBuilder} для цепочки методов
     */
    public StringBuilder delete(int start, int end){
        this.addToHistory();
        return this.sb.delete(start, end);
    }

    /**
     * Отменяет последнее изменение, восстанавливая предыдущую версию строки.
     *
     * @return ссылку на этот же объект {@code MyStringBuilder} или исходную строку,
     * если история пуста
     */
    public StringBuilder undo(){
        if(!this.history.empty()){
            String previous = this.history.pop();
            return this.sb.replace(0, this.sb.length(), previous);
        }
        return this.sb;
    }
    /**
     * Внутренний метод, записывающий текущее состояние строки в стек истории.
     */
    private void addToHistory(){
        this.history.push(toString());
    }
}
