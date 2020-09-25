package test.com.loomsystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.com.servicenow.DataParser;
import test.com.servicenow.Sentence;

import java.util.*;

public class DataParserTest {

    private DataParser testSubject;
    private List<String> lines=new ArrayList<>();
    @Before
    public void setUp() throws Exception {
        testSubject=new DataParser(2);

    }

    @After
    public void tearDown() throws Exception {
        lines.clear();
    }

    @Test
    public void verifyCorrectParsingOfSentences() throws Exception{
        lines.add("01-01-2012 19:45:00 A B C D");
        lines.add("01-01-2012 20:12:39 A Z C D");
        testSubject.setLines(lines);
        List<Sentence> sentenceList=testSubject.parseToSentences();
        Assert.assertEquals(2,sentenceList.size());
        Assert.assertEquals(4,testSubject.getSentenceLength().intValue());
        Assert.assertEquals(4,sentenceList.get(0).getHashValueList().size());
    }



}