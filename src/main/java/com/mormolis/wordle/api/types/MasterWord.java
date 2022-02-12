package com.mormolis.wordle.api.types;

import java.util.Arrays;

public class MasterWord extends Word {

    public MasterWord(String word) {
        super(word.toLowerCase());
    }

    public Letter charAt(int index) {
        return word[index];
    }

    public boolean contains(Letter letter) {
        for (int i = 0; i < word.length; i++) {
            if (word[i].equals(letter)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterWord that = (MasterWord) o;
        return Arrays.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(word);
    }
}
