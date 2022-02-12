package com.mormolis.wordle.api.dictionaries;

import com.mormolis.wordle.api.types.Word;

public interface Dictionary {
    boolean isValidWord(Word word);
}
