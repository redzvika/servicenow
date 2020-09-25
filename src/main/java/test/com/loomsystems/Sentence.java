package test.com.loomsystems;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
public class Sentence {

     public static Logger logger =  LogManager.getLogger("Sentence");
     @Getter  private final String raw;
     @Getter  private final int line;
     @Getter  private List<String > words=new ArrayList<>();

     @Getter
     private List<String> hashValueList=new ArrayList<>();

     public  int getSentenceLength(){
         return  words.size();
     }


     public Sentence(String raw,int line){
         this.raw=raw;
         this.line=line;
         logger.debug("Sentence line [{}]  '{}' ",line,raw);
     }


     private String calculateHashForMissingWord(int missingWord,String[] wordsArray){
         StringBuilder builder=new StringBuilder();
         for (int wordCounter=0;wordCounter<wordsArray.length;wordCounter++) {
             if (wordCounter != missingWord) {
                 builder.append(wordsArray[wordCounter]);
                 if (wordCounter!=wordsArray.length-1){
                     builder.append(" ");
                 }
             }
         }
         logger.debug("missingWordPosition[{}] ,wordsArray {}, missingString:{}",missingWord,wordsArray,builder.toString());
         return DigestUtils.md5Hex(builder.toString()).toUpperCase();
     }


     public void generateHashValues(int prefixToIgnore){
         String[]  split= raw.split(" ");
         logger.warn("skipping {} words in sentence",prefixToIgnore);
         String[] workingArray=Arrays.copyOfRange(split,prefixToIgnore , split.length);
         // skip date and time.

         for (int position=0;position<workingArray.length;position++){
             words.add(workingArray[position]);
             hashValueList.add(calculateHashForMissingWord(position,workingArray));
         }
     }

//     public  void generateWords(int prefixToIgnore) throws  Exception {
//         String[]  split= raw.split(" ");
//         logger.warn("skipping {} words in sentence",prefixToIgnore);
//         String[] workingArray=Arrays.copyOfRange(split,prefixToIgnore , split.length);
//
//
//         for (int i=0;i<workingArray.length;i++){
//             Word word=new Word(split[i],i-prefixToIgnore);
//             words.add(word);
//             logger.debug("adding  {} to sentence",word);
//         }
//     }

    @Override
    public String toString(){
        return "line ["+line+"] '"+raw+"'";
    }

}
