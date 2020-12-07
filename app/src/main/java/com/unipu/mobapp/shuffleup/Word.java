package com.unipu.mobapp.shuffleup;

public class Word {


    private String answer;
    private String[] letters;
    private int count;

    public Word(String answer, String[] letters, int count) {
        this.answer = answer;
        this.letters = letters;
        this.count = count;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getLetters() {
        return letters;
    }

    public void setLetters(String[] letters) {
        this.letters = letters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
