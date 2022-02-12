package com.mormolis.wordle.api.types;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.mormolis.wordle.api.types.Status.*;

public class GuessWord extends Word {


    private Status[] status;
    private boolean eligibleForStatusSet;

    public GuessWord(String word) {
        super(word);
        initialiseStatus(word.length());
        this.eligibleForStatusSet = true;
    }

    public synchronized void setLetterStatusAccordingTo(MasterWord masterWord) {
        if (eligibleForStatusSet) {
            for (int i = 0; i < word.length; i++) {
                this.status[i] = compareWithMasterWord(this.word[i], masterWord, i);
            }
            eligibleForStatusSet = false;
        }
    }

    public Set<Letter> getAllNonUsedLetter() {
        Set<Letter> nonUsedLetters = new LinkedHashSet<>();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == NOT_USED) {
                nonUsedLetters.add(word[i]);
            }
        }
        return nonUsedLetters;
    }

    public Set<Letter> getAllUsedInTheRightPlace() {
        Set<Letter> usedInTheRightPlace = new LinkedHashSet<>();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == USED_RIGHT_PLACE) {
                usedInTheRightPlace.add(word[i]);
            }
        }
        return usedInTheRightPlace;
    }

    public Set<Letter> getAllUsedInTheWrongPlace() {
        Set<Letter> usedInTheRightPlace = new LinkedHashSet<>();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == USED_WRONG_PLACE) {
                usedInTheRightPlace.add(word[i]);
            }
        }
        return usedInTheRightPlace;
    }

    public boolean isEqualToMasterWord(MasterWord masterWord) {
        for (int i = 0; i < word.length; i++) {
            if (!masterWord.charAt(i).equals(word[i])) {
                return false;
            }
        }
        return true;
    }

    private Status compareWithMasterWord(Letter letter, MasterWord masterWord, int index) {
        if (masterWord.charAt(index).equals(letter)) {
            return Status.USED_RIGHT_PLACE;
        }
        if (masterWord.contains(letter)) {
            return Status.USED_WRONG_PLACE;
        }
        return Status.NOT_USED;
    }

    private void initialiseStatus(int wordLength) {
        this.status = new Status[wordLength];
        for (int i = 0; i < wordLength; i++) {
            this.status[i] = INITIAL_STATE;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length; i++) {
            sb.append(word[i])
                    .append("->")
                    .append(status[i])
                    .append("   ");

        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessWord word1 = (GuessWord) o;
        return Arrays.equals(word, word1.word) && Arrays.equals(status, word1.status);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(word);
        result = 31 * result + Arrays.hashCode(status);
        return result;
    }
}