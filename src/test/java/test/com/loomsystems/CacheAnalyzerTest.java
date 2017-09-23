package test.com.loomsystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class CacheAnalyzerTest {


    private CacheAnalyzer testSubject;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    private CacheBuilder generateCacheBuilder(int sentenceLength,List<String> lines ) throws  Exception{
        CacheBuilder cacheBuilder=new CacheBuilder(sentenceLength);

        for(int i=0;i<lines.size();i++){
            Sentence sentence=new Sentence(lines.get(i),i);
            sentence.generateWords(2);
            cacheBuilder.addSentence(sentence);
            for (int j = 0; j < sentence.getWords().size(); j++) {
                cacheBuilder.addWord(sentence.getWords().get(j), i);
            }
        }
        return  cacheBuilder;
    }

    /**
     * expect to find change at word 5
     * expect to have 3 lines in with the change
     * @throws Exception
     */
    @Test
    public void checkSingleCacheBuilderWithCommon_5()throws Exception{

        CacheBuilder cacheBuilder=generateCacheBuilder(6,new ArrayList<>(Arrays.asList(
                "01-01-2012 19:45:00 Naomi is getting into the car",
                "01-01-2012 19:45:00 Naomi is getting into the boat",
                "01-01-2012 19:45:00 Naomi is getting into the ship")));


        testSubject=new CacheAnalyzer(new ArrayList<>(Arrays.asList(cacheBuilder)));

        List<Set<Map.Entry<Integer, List<Integer>>>> result=testSubject.analyze();
        Assert.assertEquals(1,result.size());

        Map.Entry mapEntry=result.get(0).iterator().next();

        Assert.assertEquals(5,mapEntry.getKey());
        Assert.assertEquals(3,((List<Integer>)mapEntry.getValue()).size());
        Assert.assertEquals(new ArrayList<>(Arrays.asList(0,1,2)),mapEntry.getValue());

    }



    /**
     * expect to find change at word 5
     * expect to have 3 lines in with the change
     * expect to find change at word 0
     * expect to have 2 lines in with the change
     *
     * @throws Exception
     */
    @Test
    public void checkSingleCacheBuilderWithCommon_2()throws Exception{

        CacheBuilder cacheBuilder=generateCacheBuilder(6,new ArrayList<>(Arrays.asList(
                "01-01-2012 19:45:00 Naomi is getting into the car",
                "01-01-2012 19:45:00 Naomi is getting into the boat",
                "01-01-2012 19:45:00 Naomi is getting into the ship",
                "01-01-2012 19:45:00 Marta is getting into the ship"
        )));

        testSubject=new CacheAnalyzer(new ArrayList<>(Arrays.asList(cacheBuilder)));

        List<Set<Map.Entry<Integer, List<Integer>>>> result=testSubject.analyze();

        Assert.assertEquals(1,result.size());
        result.get(0).forEach(mapEntry -> {
            if (mapEntry.getKey().intValue() == 0) {
                Assert.assertEquals(2, mapEntry.getValue().size());
                Assert.assertEquals(new ArrayList<>(Arrays.asList(2,3)),mapEntry.getValue());
            } else {
                Assert.assertEquals(5, mapEntry.getKey().intValue());
                Assert.assertEquals(3, mapEntry.getValue().size());
                Assert.assertEquals(new ArrayList<>(Arrays.asList(0,1,2)),mapEntry.getValue());
            }
        });
    }



    /**
     * no sentences with common pattern found (one change)
     * @throws Exception
     */
    @Test
    public void checkSingleCacheBuilderWithNoCommon()throws Exception{

        CacheBuilder cacheBuilder=generateCacheBuilder(6,new ArrayList<>(Arrays.asList(
                "01-01-2012 19:45:00 Naomi is getting into the car",
                "01-01-2012 19:45:00 Marta is getting into the ship"
        )));

        testSubject=new CacheAnalyzer(new ArrayList<>(Arrays.asList(cacheBuilder)));

        List<Set<Map.Entry<Integer, List<Integer>>>> result=testSubject.analyze();

        Assert.assertEquals(1,result.size());
        Assert.assertEquals(0,result.get(0).size());
    }






}