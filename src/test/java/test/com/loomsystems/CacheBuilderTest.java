package test.com.loomsystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.com.loomsystems.Exceptions.InvalidWordPositionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CacheBuilderTest {
    private CacheBuilder testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject=new CacheBuilder(6);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkValidWords() throws Exception{


        List<String> lines = new ArrayList<>(Arrays.asList("01-01-2012 19:45:00 Naomi is getting into the car",
                                                           "01-01-2012 19:45:00 Naomi is getting into the boat",
                                                           "01-01-2012 19:45:00 Naomi is getting into the ship"));


        for(int i=0;i<lines.size();i++){
            Sentence sentence=new Sentence(lines.get(i),i);
            sentence.generateWords(2);
            testSubject.addSentence(sentence);
            for (int j = 0; j < sentence.getWords().size(); j++) {
                testSubject.addWord(sentence.getWords().get(j), i);
            }
        }
        Assert.assertEquals(3,testSubject.getSentences().size());
        Assert.assertEquals(8,testSubject.getWordCache().size());

        // verify "Naomi" is found at position 0 ,3 times
        Assert.assertEquals(3,testSubject.getWordCache().get(new Word("Naomi",0)).size());

        // verify "the" is not found at position 0
        Assert.assertEquals(null,testSubject.getWordCache().get(new Word("the",0)));
        // verify "the" is  found at position 4
        Assert.assertNotEquals(null,testSubject.getWordCache().get(new Word("the",4)));
    }





    @Test(expected = InvalidWordPositionException.class)
    public void checkInvalidWordPositionException() throws Exception{
            Sentence sentence=new Sentence("01-01-2012 19:45:00 Naomi is getting into the car",0);
            sentence.generateWords(2);
            testSubject.addSentence(sentence);
            testSubject.addWord(new Word("a",12),0);
    }

}