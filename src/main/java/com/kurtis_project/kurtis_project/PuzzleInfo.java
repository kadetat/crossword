package com.kurtis_project.kurtis_project;

import java.util.ArrayList;

public class PuzzleInfo {

    private ArrayList<String> clues;
    private ArrayList<String> words;
    private String OneClue;
    private String OneWord;
    private String id;


    public ArrayList<String> getclues() {return clues; }
    public void setClues(ArrayList<String> clues) { this.clues = clues; }

    public ArrayList<String> getwords() { return words; }
    public void setWords(ArrayList<String> words) { this.words = words; }

    public String getOneClue() {return OneClue; }
    public void setOneClue(String OneClue) { this.OneClue = OneClue; }

    public String getOneWord() {return OneWord; }
    public void setOneWord(String OneWord) { this.OneWord = OneWord; }

    public String getId() {return id; }
    public void setId(String id) { this.id = id; }

}
