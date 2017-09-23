package test.com.loomsystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

    private Word  testSubject;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public  void checkGetters() {
        String raw="Home";
        int position=4;
        testSubject=new Word(raw,position);
        Assert.assertEquals(testSubject.getValue(),raw);
        Assert.assertEquals(testSubject.getPositionInSentence(),4);
    }
}