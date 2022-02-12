package com.mormolis.wordle.api.keyboards;

import com.mormolis.wordle.api.types.Letter;
import com.mormolis.wordle.api.types.MasterWord;
import com.mormolis.wordle.api.types.Status;
import com.mormolis.wordle.api.types.GuessWord;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocaleKeyboardTest {

    private LocaleKeyboard localeKeyboard;

    @Test
    void keyboardReturnLetterStatusForUkLocale() {
        localeKeyboard = new LocaleKeyboard(Locale.UK);

        GuessWord word = new GuessWord("water");
        MasterWord masterWord = new MasterWord("warms");
        word.setLetterStatusAccordingTo(masterWord);
        localeKeyboard.addAll(word);

        LinkedHashMap<Letter, Status> keysWithStatus = localeKeyboard.getKeysWithStatus();

        assertEquals(keysWithStatus.get(new Letter('w')), Status.USED_RIGHT_PLACE);
        assertEquals(keysWithStatus.get(new Letter('a')), Status.USED_RIGHT_PLACE);
        assertEquals(keysWithStatus.get(new Letter('r')), Status.USED_WRONG_PLACE);
        assertEquals(keysWithStatus.get(new Letter('e')), Status.NOT_USED);
        assertEquals(keysWithStatus.get(new Letter('t')), Status.NOT_USED);
        assertEquals(keysWithStatus.get(new Letter('q')), Status.INITIAL_STATE);

    }
}