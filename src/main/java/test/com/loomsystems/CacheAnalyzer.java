package test.com.loomsystems;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CacheAnalyzer {

    public static Logger logger =  LogManager.getLogger("CacheAnalyzer");
    private  final  List<CacheBuilder> cacheBuilders;
    private static String CHANGE_LINE="The changing word was:";

    public CacheAnalyzer( List<CacheBuilder> cacheBuilders){
            this.cacheBuilders=cacheBuilders;
    }



//    private void printChange(Set<Map.Entry<Integer, List<Integer>>> set,CacheBuilder cacheBuilder){
//
//        set.forEach(mapEntry->{
//            int changePosition=mapEntry.getKey();
//            List<Integer> list=mapEntry.getValue();
//            StringBuilder builder=new StringBuilder();
//            list.forEach(line->{
//                System.out.println(cacheBuilder.getLineToSentence().get(line));
//                builder.append(cacheBuilder.getLineToSentence().get(line).getWords().get(changePosition).getValue()).append(",");
//            });
//            if (builder.length()>2) {
//                builder.delete(builder.length() - 1, builder.length() );
//            }
//            System.out.println(CHANGE_LINE+builder.toString());
//
//        });
//    }

//    public List<Set<Map.Entry<Integer, List<Integer>>>> analyze(){
//        List<Set<Map.Entry<Integer, List<Integer>>>> list=new ArrayList<>();
//        cacheBuilders.forEach(cacheBuilder -> {
//            Set<Map.Entry<Integer, List<Integer>>>  result=analyzeSingleCache(cacheBuilder);
//            list.add(result);
//            printChange(result,cacheBuilder);
//        });
//        return list;
//    }

    /**
     * Analyze a chacheBuilder ,
     * first for each line find the WorkingArray
     *   for each word in the sentence find list of sentences that contain the same word (text and position)
     *    this is done using the WordCache that contains for each word a list of sentence it appears in them (test and position).
     *    remove the sentence number from the list and put the list in the array in word position of the sentence .
     *
     *   call @handleDataFromSingleSentence
     * @param cacheBuilder
     * @return
     */
//    private Set<Map.Entry<Integer, List<Integer>>>  analyzeSingleCache(CacheBuilder cacheBuilder){
//        logger.info("\r\n\r\n");
//        logger.info("analyzeSingleCache  CacheBuilder({})",cacheBuilder.getSentenceLength());
//        logger.info(cacheBuilder);
//        Set<Map.Entry<Integer, List<Integer>>> set=new HashSet<>();
//
//
//
//        for(int i=0;i<cacheBuilder.getSentences().size();i++){
//                List<Integer>[] workingArray = new ArrayList[cacheBuilder.getSentenceLength()];
//                Sentence sentence=cacheBuilder.getSentences().get(i);
//                Integer sentencePosition=new Integer(sentence.getLine());
//                logger.info("----------------  sentence "+sentence.getLine() +"-----------------------");
//
//                for (int j=0;j<sentence.getSentenceLength();j++){
//                    Word word=sentence.getWords().get(j);
//                    List<Integer > list=new ArrayList<>(cacheBuilder.getWordCache().get(word));
//                    //todo :replace the list with map
//                    list.remove(sentencePosition);
//                    workingArray[j]=list;
//                    logger.info("word " +word +" found at "+workingArray[j]);
//                }
//                Map<Integer,List<Integer>> mapResult= handleDataFromSingleSentence(workingArray,sentencePosition);
//                mapResult.entrySet().forEach(entry->{
//                    if (!set.contains(entry)){
//                        set.add(entry);
//                    }
//                });
//        }
//
//        logger.info("Final result"+set);
//        return set;
//    }

    /**
     * N =sentence word count;
     *  For each sentence received , the program search in the working array for sentences that appear exactly N-1 times
     *  if so the array position that is 'missing' is the change position.
     *  it returns a map of missing position and list of sentences participating.
     *
     * @param workingArray
     * @param sentencePosition
     * @return
     */
    private Map<Integer,List<Integer>>  handleDataFromSingleSentence(List<Integer>[] workingArray, Integer sentencePosition){

        logger.info("handleDataFromSingleSentence ({}) array={}",sentencePosition,workingArray);
        Map<Integer,List<Integer>> map=new HashMap<>();

        int missingPos=-1;
        while (matchPossible(workingArray)){

            logger.debug("Working array :{}",getWorkingArray(workingArray));
            List<Integer> list=findMinLengthList(workingArray);
            logger.debug("Min Length List :{}",list);
            Integer matchCandidate=list.get(0);
            logger.debug("using matchCandidate {} ",matchCandidate);

            int count=workingArray.length;
            for (int i=0;i<workingArray.length;i++){
                if (!workingArray[i].contains(matchCandidate)){
                  count--;
                  missingPos=i;
                }
                if (workingArray.length-count>1){
                    break;
                }
                //todo : use map instead of list
                workingArray[i].remove(matchCandidate);
                logger.debug("removing matchCandidate {} from  workingArray[{}] {}",matchCandidate,i,workingArray[i]);
            }


            if ((count+1)==workingArray.length && missingPos>-1){
                logger.debug("found valid missingPos {}",missingPos);

                List<Integer > compatibleList=map.get(missingPos);

                if (compatibleList==null){
                    compatibleList=new ArrayList<>();
                    compatibleList.add(sentencePosition);
                }

                compatibleList.add(matchCandidate);
                Collections.sort(compatibleList);
                logger.debug("map  missingPos {}, sentences{} ",missingPos,compatibleList);
                map.put(missingPos,compatibleList);
            }
        }
        logger.info("sentencePosition {}  map {}",sentencePosition,map);
        return map;

    }

    private List<Integer> findMinLengthList(List<Integer>[] workingArray){
        int minLength=Integer.MAX_VALUE;
        List<Integer> output=null;

        for (int i=0;i<workingArray.length;i++){
            if (workingArray[i].isEmpty()) {
                continue;
            }
            if (workingArray[i].size()<minLength) {
                minLength=workingArray[i].size();
                output=workingArray[i];
            }
        }
        return  output;
    }


    private boolean matchPossible(List<Integer>[] workingArray){
        int count=0;
        for (int i=0;i<workingArray.length;i++){
            List<Integer > list=workingArray[i];
            if (list.isEmpty()){
                continue;
            }
            count++;
        }
        if ((count+1)>=workingArray.length){
            logger.debug("Match possible for :{}",getWorkingArray(workingArray));
            return  true;

        }
        logger.debug("No match possible for  :{}",getWorkingArray(workingArray));
        return  false;
    }

    private static String getWorkingArray(List<Integer>[] workingArray){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<workingArray.length;i++){

            builder.append(i).append("{");
            if (workingArray[i].size()>0) {
                    builder.append(workingArray[i]);
            }
            builder.append("} ");
        }
        return  builder.toString();
    }
}
