package com.kurtis_project.kurtis_project;

import java.util.ArrayList;
import java.lang.*;
import java.util.Arrays;
import java.util.Collections;


public class CrosswordCreator {

    public int cols;
    public int rows;
    public String empty;
    public int maxLoops;
    public ArrayList<Word> availableWords;
    public String[][] grid;
    public ArrayList<Word> currentWordList;
    public int debug = 0;
    public int coordListTracker = 0;

    public CrosswordCreator(int cols, int rows, String empty, int maxLoops, ArrayList<Word> availableWords) {
        this.cols = cols;
        this.rows = rows;
        this.empty = empty;
        this.maxLoops = maxLoops;
        ArrayList<Word> randomizedList;
        randomizedList = randomizeWordList(availableWords);
        this.availableWords = randomizedList;
        this.grid = new String[this.rows][this.cols];
        grid = gridClear();
    }

    public String[][] gridClear() {
        String[][] grid = new String[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                grid[i][j] = " ";
            }
        }
        return grid;
    }

    public ArrayList<Word> sortWithStrlength(ArrayList<Word> templist) {
        for(int i=0;i<templist.size();i++)
        {
            for(int j=0;j<templist.size();j++)
            {
                Word temp;
                if(templist.get(i).toString().length()>templist.get(j).toString().length())
                {
                    temp=templist.get(i);
                    templist.set(i, templist.get(j));
                    templist.set(j,temp);
                }
            }
        }
        return templist;
    }


    public ArrayList<Word> randomizeWordList(ArrayList<Word> availableWords) {
        Collections.shuffle(availableWords);
        return sortWithStrlength(availableWords);
    }

    public String[][] computeCrossword() {
        int count = 0;
        CrosswordCreator crossword = new CrosswordCreator(this.cols, this.rows, this.empty, this.maxLoops, this.availableWords);
        while (count == 0) {
            debug += 1;
            crossword.currentWordList = new ArrayList<>();
            crossword.gridClear();
            crossword.availableWords = randomizeWordList(this.availableWords);
            //System.out.println(crossword.availableWords);
            for (Word word : crossword.availableWords) {
                if(!crossword.currentWordList.contains(word)) {
                    crossword.fitAndAdd(word);
                }
            }
            count += 1;
        }

        String[][] trimmedGrid = new String[1][this.cols];
        for (int m = 0; m < this.cols; m++){
            trimmedGrid[0][m] = "";
        }
        boolean start = true;
        int startRows = -1;
        for (int i = 0; i < this.rows; i++) {
            if (start) {
                startRows ++;
                System.out.println(startRows);

            }
            for (int j = 0; j < this.cols; j++) {
                if (!crossword.grid[i][j].equals(" ")) {
                    trimmedGrid = appendString(crossword.grid[i], trimmedGrid);
                    j = this.cols;
                    start = false;
                }
            }
        }

        int maxIndex = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (!crossword.grid[i][j].equals(" ") && !crossword.grid[i][j].equals("")) {
                    if (j > maxIndex){
                        maxIndex = j;
                        //System.out.println("j= " + maxIndex);
                    }
                }
            }
        }
        //System.out.println(trimmedGrid.length);
        String[][] trimmedGrid2 = new String[trimmedGrid.length][maxIndex + 1];
        for (int i = 0; i < trimmedGrid.length; i++){
            for (int j = 0; j <= maxIndex; j++){
                trimmedGrid2[i][j] = trimmedGrid[i][j];
            }
        }

        for (Word word : crossword.currentWordList) {
            System.out.println(word.row);
            word.row = word.row - startRows;
            System.out.println(word.row);

        }

        crossword.numberWords(crossword.currentWordList);
//        String[] hack = new String[1];
//        hack[0] = "";
//        trimmedGrid[0] = hack;
        return trimmedGrid2;
    }

    public void fitAndAdd(Word word) {
        boolean fit = false;
        int count = 0;
        while (!fit && count < maxLoops) {
            if (currentWordList.size() == 0) {
                int col = 4; //this is what i changed
                int row = this.rows / 2;
                int vertical = 0;
                fit = true;
                setWord(col, row, vertical, word, true);

            }else {
                //System.out.println(word);
                Integer[][] coordlist;
                coordlist = suggestCoord(word);
                int col = coordlist[0][0];
                int row = coordlist[0][1];
                int vertical = coordlist[0][2];
                setWord(col, row, vertical, word, true);
                fit = true;
            }
        }
    }

    public Integer[][] suggestCoord(Word word) {
        //int count = 0;
        coordListTracker = 0;
        Integer[][] coordlist = new Integer[0][5];
        int glc = -1;

        for (int i = 0; i < word.word.length(); i++) {
            char givenLetter = word.word.charAt(i);
            glc += 1;
            int rowc = 0;
            for (int j = 0; j <= this.rows - 1; j++) {
                rowc += 1;
                int colc = 0;
                for (int k = 0; k <= this.cols - 1; k++) {
                    colc += 1;
                    if (!grid[j][k].equals(" ")) {
                        char gridLetter = grid[j][k].charAt(0);
                        if (givenLetter == gridLetter) {
                            if (rowc - glc > 0) {
                                if (((rowc - glc) + word.length) <= this.rows) {
                                    Integer[][] tempcoordlist = new Integer[1][5];
                                    tempcoordlist[0][0] = colc;
                                    tempcoordlist[0][1] = rowc - glc;
                                    tempcoordlist[0][2] = 1;
                                    tempcoordlist[0][3] = colc + (rowc - glc);
                                    tempcoordlist[0][4] = 0;
                                    coordlist = append(coordlist, tempcoordlist);
                                }
                            }
                            if (colc - glc > 0) {
                                if (((colc - glc) + word.length) <= this.cols) {
                                    Integer[][] tempcoordlist = new Integer[1][5];
                                    tempcoordlist[0][0] = colc - glc;
                                    tempcoordlist[0][1] = rowc;
                                    tempcoordlist[0][2] = 0;
                                    tempcoordlist[0][3] = rowc + (colc - glc);
                                    tempcoordlist[0][4] = 0;
                                    coordlist = append(coordlist, tempcoordlist);
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(Arrays.deepToString(coordlist));
        return sortCoordList(coordlist, word, coordlist.length);
    }

    public Integer[][] sortCoordList(Integer[][] coordlist, Word word, int length) {
        Integer[][] newCoordList = new Integer[0][5];
        try {
            for (int i = 0; i <= length - 1; i++) {
                Integer[][] tempCoordList = new Integer[1][5];
                int col = coordlist[i][0];
                int row = coordlist[i][1];
                int vertical = coordlist[i][2];
                coordlist[i][4] = checkFitScore(col, row, vertical, word);
                if (coordlist[i][4] != 0) {
                    tempCoordList[0][0] = col;
                    tempCoordList[0][1] = row;
                    tempCoordList[0][2] = vertical;
                    tempCoordList[0][3] = 0;
                    tempCoordList[0][4] = coordlist[i][4];
                    newCoordList = append(newCoordList, tempCoordList);
                }
            }
        } catch (Exception ignored) {
        }
        int current;
        int maxTotal = 0;
        int index = 0;
        for (int i = 0; i < newCoordList.length; i++) {
            current = newCoordList[i][4];
            if (current > maxTotal) {
                maxTotal = current;
                index = i;
            }
        }
        newCoordList[0][0] = newCoordList[index][0];
        newCoordList[0][1] = newCoordList[index][1];
        newCoordList[0][2] = newCoordList[index][2];
        newCoordList[0][3] = newCoordList[index][3];
        newCoordList[0][4] = newCoordList[index][4];
        //System.out.println("newCoordlist: " + Arrays.deepToString(newCoordList));
        return newCoordList;
    }

    public int checkFitScore(int col, int row, int vertical, Word word) {
        if (col < 1 || row < 1) {
            return 0;
        }

        int count = 1;
        int score = 1;

        for (int i = 0; i < word.word.length(); i++) {
            String letter = String.valueOf(word.word.charAt(i));
            try{
                String activeCell = getCell(col, row);

                if (activeCell.equals(" ") || activeCell.equals(letter)){
                } else {
                    return 0;
                }
                if (activeCell.equals(letter)) {
                    score += 1;
                }

                if (vertical == 1) {
                    if (activeCell.equals(letter)) {
                        if (!checkIfCellClear(col, row - 1)) {
                            return 0;
                        }
                        if (count == word.word.length()) {
                            if (!checkIfCellClear(col, row + 1)) {
                                return 0;
                            }
                        }
                    }
                    if (!activeCell.equals(letter)) {
                        if (!checkIfCellClear(col + 1, row)) {
                            return 0;
                        }
                        if (!checkIfCellClear(col - 1, row)) {
                            return 0;
                        }
                        if (count == 1) {
                            if (!checkIfCellClear(col, row)) {
                                return 0;
                            }
                        }
                        if (count == 1) {
                            if (!checkIfCellClear(col, (row - 1))) {
                                return 0;
                            }
                        }
                        if (count == word.word.length()) {
                            if (!checkIfCellClear(col, row + 1)) {
                                return 0;
                            }
                        }
                    }
                }else {
                    if (activeCell.equals(letter)) {
                        if (!checkIfCellClear(col - 1, row)) {
                            return 0;
                        }
                        if (count == word.word.length()) {
                            if (!checkIfCellClear(col + 1, row)) {
                                return 0;
                            }
                        }
                    }
                    if (!activeCell.equals(letter)) {
                        if (!checkIfCellClear(col, row - 1)) {
                            return 0;
                        }
                        if (!checkIfCellClear(col, row + 1)) {
                            return 0;
                        }
                        if (count == 1) {
                            if (!checkIfCellClear(col, row)) {
                                return 0;
                            }
                        }
                        if (count == 1) {
                            if (!checkIfCellClear(col - 1, row)) {
                                return 0;
                            }
                        }
                        if (count == word.word.length()) {
                            if (!checkIfCellClear(col + 1, row)) {
                                return 0;
                            }
                        }
                    }
                }
                if (vertical == 1) {
                    row += 1;
                } else {
                    col += 1;
                }
                
                count = count + 1;

            } catch (Exception IndexOutOfBoundsException){
                return 0;}
        }return score;
    }

    public String getCell(int col, int row) {
        return grid[row-1][col-1];
    }
    
    public Boolean checkIfCellClear(int col, int row) {
        Boolean value = null;
        try {
            String cell = getCell(col, row);
            if (cell.equals(" ")) {
                value = true;
            }
        } catch (Exception IndexOutOfBoundsException){
            value = false;
            }
        return value;
    }

    public void setWord(int col, int row, int vertical, Word word, Boolean force) {
        if (force) {
            word.col = col;
            word.row = row;
            word.vertical = vertical;
            currentWordList.add(word);
            //System.out.println(col + " " + row + " " + vertical + " " + word.toString());
            for (int i = 0; i < word.word.length(); i++) {
                String letter = String.valueOf(word.word.charAt(i));
                setCell(col, row, letter);
                    if (vertical == 1) {
                        row += 1;
                    }else {
                        col += 1;
                    }
            }
        }
    }

    private void setCell(int col, int row, String value) {
        grid[row-1][col-1] = value;
    }

    public void display(String[][] grid) {
        //System.out.println(Arrays.deepToString(grid));
        String outStr = "";
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[1].length; c++) {
                outStr += grid[r][c] + " ";
            }
            outStr += "\n";
        }
        System.out.println(outStr);
    }

    public void numberWords(ArrayList<Word> currentWordList){
        ArrayList<Word> orderlist = new ArrayList<>();
        ArrayList<Word> copylist;
        //System.out.println(currentWordList.size());
        copylist = currentWordList;
        while(!copylist.isEmpty()){
            int col = 100;
            int row = 100;
            int minTotal = col + row;
            int currentCol;
            int currentRow;
            int currentTotal;
            Word min = null;
            for (Word word : copylist) {
                currentCol = word.col;
                currentRow = word.col;
                currentTotal = currentCol + currentRow;
                if (currentTotal < minTotal) {
                    minTotal = currentTotal;
                    min = word;
                }
            }
            orderlist.add(min);
            copylist.remove(min);
        }
        //System.out.println(orderlist.toString());
        for (Word word : orderlist){
            word.number = (orderlist.indexOf(word) + 1);
            System.out.println(word.toString() + " has number " + word.number);
        }
    }

    public static Integer[][] append(Integer[][] a, Integer[][] b) {
        Integer[][] result = new Integer[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        //System.out.println("result: " + Arrays.deepToString(result));
        return result;
    }

    public static String[][] appendString(String[] a, String[][] b) {
        String[][] temp = new String[1][];
        temp[0] = a;
        String[][] result = new String[1 + b.length][];
        System.arraycopy(b, 0, result, 0, b.length);
        System.arraycopy(temp, 0, result, b.length, temp.length);
        return result;
    }


    public static void main(String[] args) {
        ArrayList<Word> list = new ArrayList<>();
        list.add(new Word("basketballgoal", "1"));
        list.add(new Word("football", "2"));
        list.add(new Word("incredible", "3"));
        list.add(new Word("baseballbat", "4"));
        list.add(new Word("glove", "5"));
        list.add(new Word("those", "6"));
        list.add(new Word("kid", "7"));
        list.add(new Word("ball", "8"));
//        list.add(new Word("lie", "9"));
//        list.add(new Word("goat", "10"));
//        list.add(new Word("kurtis", "11"));
//        list.add(new Word("golf", "12"));
//        list.add(new Word("dad", "13"));
//        list.add(new Word("money", "14"));
//        list.add(new Word("mom", "15"));
//        list.add(new Word("hello", "16"));
//        list.add(new Word("bedtime", "17"));
//        list.add(new Word("numbers", "18"));
//        list.add(new Word("words", "19"));
//        list.add(new Word("baseball", "20"));
//        list.add(new Word("ballboy", "21"));
//        list.add(new Word("softball", "22"));
//        list.add(new Word("account", "23"));
//        list.add(new Word("october", "24"));
//        list.add(new Word("company", "25"));
//        list.add(new Word("horse", "26"));
//        list.add(new Word("company", "27"));
//        list.add(new Word("teacher", "28"));
//        list.add(new Word("sammy", "29"));
//        list.add(new Word("tatkenhorst", "30"));


        CrosswordCreator puzzle1 = new CrosswordCreator(40, 40, "empty", 2000, list);
//        int spins = 8;
//        int x = 1;
//        boolean complete = false;
//        while (x < spins && !complete){
            //try {
                String[][] puzzle = puzzle1.computeCrossword();
                puzzle1.display(puzzle);
//                System.out.println("Number of Spins: " + x)
//                complete = true;
//            } catch (Exception NullPointerException){
//                x ++;
//                System.out.println("nowhere to put word");
//                if (x == spins) {
//                    System.out.println("Error: reached spins");
//                }
//            }
 //       }
    }
}
