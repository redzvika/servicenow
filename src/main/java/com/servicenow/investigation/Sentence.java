package com.servicenow.investigation;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class Sentence {

    public static Logger logger = LogManager.getLogger("Sentence");
    @Getter
    private final String raw;
    @Getter
    private final int line;
    @Getter
    private List<String> words = new ArrayList<>();

    @Getter
    private List<String> hashValueList = new ArrayList<>();

    public Sentence(String raw, int line) {
        this.raw = raw;
        this.line = line;
        logger.debug("Sentence line [{}]  '{}' ", line, raw);
    }


    /**
     * Fill the hashValueList of the sentence with hash values calculated for each "word" in the sentence.
     * we traverse the workingArray by order
     * use generateHashValuesList to get list of hash values of "missing" words.
     * insert this value into hashValueList in the same order.
     *
     * @param prefixToIgnore
     */
    public void generateHashValues(int prefixToIgnore) {
        String[] split = raw.split(" ");
        logger.warn("skipping {} words in sentence", prefixToIgnore);
        String[] workingArray = Arrays.copyOfRange(split, prefixToIgnore, split.length);
        // skip date and time.
        hashValueList.addAll(generateHashValuesList(workingArray));
        Collections.addAll(words, workingArray);
    }

    /**
     * calculate hash values for each "missing" word in the sentence
     * @param words
     * @return
     */
    private List<String> generateHashValuesList(String  [] words){
        Integer [] position=new Integer[words.length];

        List<String> list=new ArrayList<>();
        StringBuilder builder=new StringBuilder();

        int counter=0;
        int startPosition=0;
        for (String word : words) {
            position[counter]=startPosition;
            builder.append(word).append(" ");
            startPosition=builder.toString().length();
            counter++;
        }
        String baseString=builder.toString();

        for (counter=0;counter<words.length;counter++) {
            String blanked= removeFromStartToFinish(baseString,position[counter],position[counter]+words[counter].length()).trim();
            logger.debug("missingWordPosition[{}] ,wordsArray {}, missingString:{}", words[counter], words, blanked);
            list.add(DigestUtils.md5Hex(blanked).toUpperCase());
        }
        return list;
    }

    private String removeFromStartToFinish(String input, int start, int finish){
        return input.substring(0,start)+input.substring(finish);
    }


    @Override
    public String toString() {
        return "line [" + line + "] '" + raw + "'";
    }

}
