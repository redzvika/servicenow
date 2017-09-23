package test.com.loomsystems;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.com.loomsystems.Exceptions.InvalidWordPositionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheBuilder {


    public static Logger logger =  LogManager.getLogger("CacheBuilder");
    private static String EOL="\r\n";
    @Getter private Map<Word,List<Integer>> wordCache =new HashMap<>();
    @Getter private List<Sentence> sentences=new ArrayList<>();
    @Getter private Map<Integer,Sentence> lineToSentence=new HashMap<>();
    @Getter private final int sentenceLength;

    public CacheBuilder(int sentenceLength){
        this.sentenceLength=sentenceLength;
    }


    public void addWord(Word word,int line) throws InvalidWordPositionException {


        if (word.getPositionInSentence()>= sentenceLength){
            logger.error("Word value {}  position {} can't pass sentence {} length ",word.getValue(),word.getPositionInSentence(),sentenceLength);
            throw new InvalidWordPositionException("Word value "+ word.getValue()+"  position "+word.getPositionInSentence()+" can't pass sentence "+sentenceLength+" length ");
        }

        List<Integer> list= wordCache.get(word);
        if (list==null){
            list=new ArrayList<>();
        }
        list.add(line);
        wordCache.put(word,list);
        logger.debug("cacheBuilder_{}   [{}]  in {} ",sentenceLength,word,list);
    }


    public void addSentence(Sentence sentence){
        sentences.add(sentence);
        lineToSentence.put(sentence.getLine(),sentence);
        logger.debug("cacheBuilder_{} adding {} ",sentenceLength,sentence);
    }


    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append("CacheBuilder(").append(sentenceLength).append(") words occurrence :").append(EOL);
        wordCache.forEach((wordCache,list)->builder.append(wordCache).append("\t\t\t\t\t").append(list).append(EOL));
        return builder.toString();
    }

}
