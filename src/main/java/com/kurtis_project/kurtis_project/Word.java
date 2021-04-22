package com.kurtis_project.kurtis_project;

public class Word {
    String word;
    String clue;
    int length;
    int row;
    int col;
    int vertical;
    int number;

    public Word (String word, String clue) {
        try {
        this.word = word;
        this.clue = clue;
        this.length = word.length();
        this.row = Integer.parseInt(null);
        this.col = Integer.parseInt(null);
        this.vertical = Integer.parseInt(null);
        this.number = Integer.parseInt(null);
        } catch (Exception ignored) {
        }
    }

    public String toString() {
        return word;
    }

}
