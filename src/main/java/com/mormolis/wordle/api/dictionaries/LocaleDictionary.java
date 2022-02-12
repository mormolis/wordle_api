package com.mormolis.wordle.api.dictionaries;

import com.mormolis.wordle.api.types.GuessWord;
import com.mormolis.wordle.api.types.Word;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class LocaleDictionary implements Dictionary {

    private final TreeSet<String> validWords;

    public LocaleDictionary(Locale locale) {
        this.validWords = retrieveDictionary(locale);
    }

    private TreeSet<String> retrieveDictionary(Locale locale) {
        try {
            ResourceBundle settings = ResourceBundle.getBundle("settings", locale);
            Path dictionaryPath = Paths.get(settings.getString("dictionary"));
            URL dictionaries = this.getClass().getClassLoader().getResource("dictionaries");
            Path dictionaryAbsolutePath = Path.of(dictionaries.toURI()).resolve(dictionaryPath);
            return Files.lines(dictionaryAbsolutePath).collect(() -> new TreeSet(Comparator.naturalOrder()), TreeSet::add, TreeSet::addAll);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Could not retrieve dictionary for: " + locale.getLanguage(), e);
        }
    }

    @Override
    public boolean isValidWord(Word word) {
        return validWords.contains(word.asString());
    }
}
