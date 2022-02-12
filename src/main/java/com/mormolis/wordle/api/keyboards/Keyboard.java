package com.mormolis.wordle.api.keyboards;

import com.mormolis.wordle.api.types.Letter;
import com.mormolis.wordle.api.types.Status;
import com.mormolis.wordle.api.types.GuessWord;

import java.util.LinkedHashMap;

public interface Keyboard {
    void addAll(GuessWord word);

    LinkedHashMap<Letter, Status> getKeysWithStatus();
}
