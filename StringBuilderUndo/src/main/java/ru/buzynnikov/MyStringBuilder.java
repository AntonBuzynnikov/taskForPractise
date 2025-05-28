package ru.buzynnikov;

import java.util.Stack;

public class MyStringBuilder{

    private final StringBuilder sb;

    private final Stack<String> history = new Stack<>();

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
        if(!this.history.empty()){
            String previous = this.history.pop();
            return this.sb.replace(0, this.sb.length(), previous);
        }
        return this.sb;
    }

    private void addToHistory(){
        this.history.push(toString());
    }
}
