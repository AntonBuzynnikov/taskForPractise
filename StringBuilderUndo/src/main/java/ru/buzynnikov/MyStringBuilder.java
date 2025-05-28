package ru.buzynnikov;

import java.util.Deque;
import java.util.LinkedList;
/**
 * Класс {@code MyStringBuilder} представляет собой расширенную версию стандартного класса {@link StringBuilder},
 * дополненного возможностью отмены последних изменений (undo).
 */
public class MyStringBuilder{


    private final StringBuilder sb;


    private final Deque<String> history = new LinkedList<>();

    public MyStringBuilder(StringBuilder sb) {
        this.sb = sb;
    }


    public StringBuilder append(String str){
        this.addToHistory();
        return this.sb.append(str);
    }


    public String toString(){
        return this.sb.toString();
    }


    public StringBuilder delete(int start, int end){
        this.addToHistory();
        return this.sb.delete(start, end);
    }


    public StringBuilder undo(){
        if(!this.history.isEmpty()){
            String previous = this.history.getLast();
            this.history.removeFirst();
            return this.sb.replace(0, this.sb.length(), previous);
        }
        return this.sb;
    }
    private void addToHistory(){
        this.history.push(this.toString());
    }
}
