package test.com.loomsystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SentenceTest {


    private Sentence testSubject;
    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public  void checkGetters() {
        String raw="02-01-2012 09:13:15 A Z G D";
        int line=4;
        testSubject=new Sentence(raw,line);
        Assert.assertEquals(testSubject.getLine(),line);
        Assert.assertEquals(testSubject.getRaw(),raw);
    }


    @Test
    public  void checkSixWordSentenceWithValidPrefix(){
        testSubject=new Sentence("02-01-2012 09:13:15 A Z G D",4);
        try {
            testSubject.generateWords(2);

        }catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertEquals(testSubject.getWords().size(),4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public  void checkSixWordSentenceWithInValidPrefix() throws Exception{
        testSubject=new Sentence("02-01-2012 09:13:15 A Z G D",4);
        testSubject.generateWords(-1);
    }

    @Test
    public  void checkFourWordSentenceWithValidPrefix() throws Exception{
        testSubject=new Sentence("02-01-2012 09:13:15 A Z",4);
        testSubject.generateWords(2);
        Assert.assertEquals(testSubject.getWords().size(),2);
    }


    @Test
    public  void checkParse() throws Exception{
        testSubject=new Sentence("xasdadaa,,,sdasdasd",1);
        testSubject.generateWords(0);
        Assert.assertEquals(testSubject.getWords().size(),1);


        testSubject=new Sentence("xasdadaa\r\nsdasdasd",1);
        testSubject.generateWords(0);
        Assert.assertEquals(testSubject.getWords().size(),1);


        testSubject=new Sentence("xasdadaa\t\tsdasdasd",1);
        testSubject.generateWords(0);
        Assert.assertEquals(testSubject.getWords().size(),1);


        testSubject=new Sentence("xasdadaa::sdasdasd",1);
        testSubject.generateWords(0);
        Assert.assertEquals(testSubject.getWords().size(),1);
    }





}