package test.com.loomsystems;

import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Data
public class Sentence {

     public static Logger logger =  LogManager.getLogger("Sentence");
     @Getter private final String raw;
     @Getter  private final int line;
     private List<Word > words=new ArrayList<>();

     public  int getSentenceLength(){
         return  words.size();
     }


     public Sentence(String raw,int line){
         this.raw=raw;
         this.line=line;
         logger.debug("Sentence line [{}]  '{}' ",line,raw);
     }

     public  void generateWords(int prefixToIgnore) throws  Exception {
         String[]  split= raw.split(" ");
         logger.warn("skipping {} words in sentence",prefixToIgnore);
         for (int i=prefixToIgnore;i<split.length;i++){
             Word word=new Word(split[i],i-prefixToIgnore);
             words.add(word);
             logger.debug("adding  {} to sentence",word);
         }
     }

    @Override
    public String toString(){
        return "line ["+line+"] '"+raw+"'";
    }

}
