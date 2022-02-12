package com.mormolis.wordle.api;

import com.mormolis.wordle.api.dictionaries.Dictionary;
import com.mormolis.wordle.api.exceptions.GameOverException;
import com.mormolis.wordle.api.exceptions.NotValidWordException;
import com.mormolis.wordle.api.keyboards.Keyboard;
import com.mormolis.wordle.api.types.*;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Wordle {
    private final MasterWord word;
    private final GuessWord[] attempts;
    private final Keyboard keyboard;
    private final Dictionary dictionary;

    private int attemptsCounter;

    public Wordle(MasterWord word, Keyboard keyboard, Dictionary dictionary) {
        this.word = word;
        this.dictionary = dictionary;
        checkWordValidity(this.word);
        this.attempts = new GuessWord[6];
        attemptsCounter = 0;
        this.keyboard = keyboard;
    }


    public boolean guess(GuessWord guessWord) {
        checkWordValidity(guessWord);
        guessWord.setLetterStatusAccordingTo(word);
        keyboard.addAll(guessWord);
        attempts[attemptsCounter] = guessWord;
        attemptsCounter++;
        boolean guessedRight = guessWord.isEqualToMasterWord(word);
        isGameOver(guessedRight);
        return guessedRight;
    }

    private void checkWordValidity(Word word) {
        if (word.length() != 5 || !dictionary.isValidWord(word)) {
            throw new NotValidWordException();
        }
    }

    public GuessWord[] getAttempts() {
        return Arrays.copyOf(attempts, attempts.length);
    }

    public LinkedHashMap<Letter, Status> getKeyboard() {
        return keyboard.getKeysWithStatus();
    }


    private void isGameOver(boolean guessedRight) {
        if (!guessedRight && attemptsCounter == 6) {
            throw new GameOverException();
        }
    }
}
