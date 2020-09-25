package com.servicenow.investigation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class OneWordChangeSentence {
    public static String EOL = "\r\n";
    private static final String OUTPUT_PREFIX = "The changing word was:";
    @Getter
    private final List<String> rawSentenceList = new ArrayList<>();
    @Getter
    private final List<String> changingWords = new ArrayList<>();


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(EOL);
        for (String rawSentence : rawSentenceList) {
            builder.append(rawSentence);
            builder.append(EOL);
        }
        builder.append("=============================================================================================");
        builder.append(EOL);
        builder.append(OUTPUT_PREFIX);
        for (String word : changingWords) {
            builder.append(word);
            builder.append(" ");
        }
        return builder.toString();

    }
}
