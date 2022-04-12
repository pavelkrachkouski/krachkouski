package com.krachkouski.krachkouski;

public record Word(int id, String word, String definition) {

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
