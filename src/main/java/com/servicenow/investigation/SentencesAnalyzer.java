package com.servicenow.investigation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentencesAnalyzer {


    public static Logger logger = LogManager.getLogger("SentencesAnalyzer");
    private final List<Sentence> sentencesList;
    private final int sentenceLength;


    private final List<Map<String, List<Integer>>> hashValueCollectorList;

    /**
     * Create a list with sentenceLength size elements.
     * for each element in the list insert a map with key hashvalue ,and value list of integers.
     * thus for each position in the sentences we have a map.
     *
     * @param sentencesList
     * @param sentenceLength
     */
    public SentencesAnalyzer(List<Sentence> sentencesList, int sentenceLength) {
        this.sentenceLength = sentenceLength;
        this.sentencesList = sentencesList;
        hashValueCollectorList = new ArrayList(sentenceLength);

        for (int i = 0; i < sentenceLength; i++) {
            hashValueCollectorList.add(new HashMap<>());
        }
    }


    /**
     * for each sentence we traverse the list of HashValueList it has
     * we search the hash value in the map correlating to the position in the sentence .
     * get the list of sentences  line numbers
     * if it's null create a new list
     * append the sentence line number to the list.
     * put the list int the map with  hash value as the key.
     */
    private void fillHashValueCollector() {
        for (Sentence sentence : sentencesList) {
            logger.debug("handling sentence {}", sentence.getLine());
            //traverse the hashValues in the sentence and put them in the  hashValueCollectorList.
            for (int i = 0; i < sentenceLength; i++) {
                String hashValue = sentence.getHashValueList().get(i);
                //logger.debug("sentence {} position [{}] value {}",sentenceNumber,i,hashValue) ;
                Map<String, List<Integer>> mapOfCollisions = hashValueCollectorList.get(i);
                List<Integer> listOfSentences = mapOfCollisions.get(hashValue);
                if (listOfSentences == null) {
                    listOfSentences = new ArrayList<>();
                }
                listOfSentences.add(sentence.getLine());

                mapOfCollisions.put(hashValue, listOfSentences);
                logger.debug("{} hashValueCollectorList {} ,map {}", (listOfSentences.size() > 1 ? "found" : ""), i, hashValueCollectorList.get(i));
            }
        }
    }


    /**
     * Search in each map in the hashValueCollectorList if there is a list that contains more than one element
     * such a list contains sentences that have identical words except one word (the position in the sentence)
     * extract this sentences
     * append them to the out list
     * append the changing word
     *
     * @return
     */
    private List<OneWordChangeSentence> extractInfo() {
        List<OneWordChangeSentence> output = new ArrayList<>();

        for (int positionInSentence = 0; positionInSentence < sentenceLength; positionInSentence++) {
            Map<String, List<Integer>> mapOfCollisions = hashValueCollectorList.get(positionInSentence);

            for (List<Integer> list : mapOfCollisions.values()) {
                OneWordChangeSentence oneWordChangeSentence = null;
                if (list.size() > 1) {
                    oneWordChangeSentence = new OneWordChangeSentence();
                    for (Integer sentenceNumber : list) {
                        Sentence sentence = sentencesList.get(sentenceNumber);
                        oneWordChangeSentence.getRawSentenceList().add(sentencesList.get(sentenceNumber).getRaw());

                        logger.debug(sentencesList.get(sentenceNumber).getRaw());
                        oneWordChangeSentence.getChangingWords().add(sentence.getWords().get(positionInSentence));
                    }
                }
                if (oneWordChangeSentence != null) {
                    output.add(oneWordChangeSentence);
                    logger.debug("{}", oneWordChangeSentence.toString());
                }
            }
        }
        return output;
    }


    public List<OneWordChangeSentence> analyzeGenerateData() {
        fillHashValueCollector();
        return extractInfo();
    }


}
