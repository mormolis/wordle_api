package com.mormolis.wordle.api.keyboards;

import com.mormolis.wordle.api.types.Letter;
import com.mormolis.wordle.api.types.Status;
import com.mormolis.wordle.api.types.GuessWord;

import java.util.*;

public class LocaleKeyboard implements Keyboard {

    private final Set<Letter> notUsed;
    private final Set<Letter> usedRightPlace;
    private final Set<Letter> usedWrongPlace;
    private final String keyboardValues;

    public LocaleKeyboard(Locale locale) {
        this.keyboardValues = ResourceBundle.getBundle("settings", locale).getString("keyboard");
        this.notUsed = new HashSet<>();
        this.usedRightPlace = new HashSet<>();
        this.usedWrongPlace = new HashSet<>();
    }

    @Override
    public synchronized void addAll(GuessWord word) {
        word.getAllUsedInTheWrongPlace()
                .forEach(this::addToUsedInTheWrongPlace);
        notUsed.addAll(word.getAllNonUsedLetter());
        usedRightPlace.addAll(word.getAllUsedInTheRightPlace());
    }

    private void addToUsedInTheWrongPlace(Letter letter) {
        if (!usedRightPlace.contains(letter)) {
            usedWrongPlace.add(letter);
        }
    }

    @Override
    public synchronized LinkedHashMap<Letter, Status> getKeysWithStatus() {
        return Arrays.stream(keyboardValues.split(""))
                .map(str -> new Letter(str.charAt(0)))
                .collect(LinkedHashMap::new,
                        (hashmap, letter) -> hashmap.put(letter, getStatusOf(letter)),
                        HashMap::putAll);

    }

    private Status getStatusOf(Letter letter) {
        if (usedRightPlace.contains(letter)) {
            return Status.USED_RIGHT_PLACE;
        }

        if (usedWrongPlace.contains(letter)) {
            return Status.USED_WRONG_PLACE;
        }

        if (notUsed.contains(letter)) {
            return Status.NOT_USED;
        }

        return Status.INITIAL_STATE;
    }
}
