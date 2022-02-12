package com.mormolis.wordle.api.types;

public abstract class Word {

    protected Letter[] word;

    public Word(String word) {
        parse(word);
    }

    private void parse(String word) {
        this.word = new Letter[word.length()];
        for (int i = 0; i < word.length(); i++) {
            this.word[i] = new Letter(word.charAt(i));
        }
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : word) {
            sb.append(letter);
        }
        return sb.toString();
    }

    public int length() {
        return word.length;
    }
}
