package test.com.loomsystems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SentencesAnalyzer {
    private static String OUTPUT_PREFIX="The changing word was:";

    public static Logger logger = LogManager.getLogger("SentencesAnalyzer");
    private List<Sentence> sentencesList;
    private int sentenceLength;

    private  List<Map<String,List<Integer>>> hashValueCollectorList =new ArrayList();

    public SentencesAnalyzer(List<Sentence> sentencesList,int sentenceLength){
        this.sentenceLength = sentenceLength;
        this.sentencesList = sentencesList;

        for(int i=0;i<sentenceLength;i++){
            hashValueCollectorList.add(new HashMap<>());
        }
    }

    public void analyze(){

       for (Sentence sentence:sentencesList){
           logger.debug("handling sentence {}",sentence.getLine());
           //traverse the hashValues in the sentence and put them in the  hashValueCollectorList.
           for (int i=0;i<sentenceLength;i++){
               String hashValue=sentence.getHashValueList().get(i);
               //logger.debug("sentence {} position [{}] value {}",sentenceNumber,i,hashValue) ;
               Map<String,List<Integer>> mapOfCollisions=hashValueCollectorList.get(i);
               List<Integer> listOfSentences=mapOfCollisions.get(hashValue);
               if (listOfSentences==null){
                   listOfSentences=new ArrayList<>();
               }
               listOfSentences.add(sentence.getLine());

               mapOfCollisions.put(hashValue,listOfSentences);
               logger.debug("{} hashValueCollectorList {} ,map {}",(listOfSentences.size()>1?"found":""),i,hashValueCollectorList.get(i));
           }
       }

        for (int positionInSentence=0;positionInSentence<sentenceLength;positionInSentence++){
            //logger.info(" ");
            Map<String,List<Integer>> mapOfCollisions=hashValueCollectorList.get(positionInSentence);
            for (List<Integer> list : mapOfCollisions.values()) {
                if (list.size()>1){
                    StringBuilder builder=new StringBuilder();
                    builder.append(OUTPUT_PREFIX);
                    builder.append(" : ");
                    for(Integer sentenceNumber:list){
                        Sentence sentence=sentencesList.get(sentenceNumber);
                        logger.info(sentencesList.get(sentenceNumber).getRaw());
                        builder.append(sentence.getWords().get(positionInSentence)).append(" ");
                    }
                    logger.info("{}",builder.toString());
                }
            }
            //logger.info(" ");
        }


    }


}
