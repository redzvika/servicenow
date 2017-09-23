package test.com.loomsystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

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
    public void verifyGenerationOfSingleCacheBuilder() throws Exception{
        lines.add("01-01-2012 19:45:00 A B C D");
        lines.add("01-01-2012 20:12:39 A Z C D");
        testSubject.setLines(lines);
        Assert.assertEquals(testSubject.parseToCacheBuilder().size(),1);
        Assert.assertEquals(testSubject.parseToCacheBuilder().get(0).getSentenceLength(),4);
    }


    @Test
    public void verifyGenerationOfMultipleCacheBuilders() throws Exception{
        lines.add("01-01-2012 19:45:00 A B C D");
        lines.add("01-01-2012 20:12:39 A Z C D E");
        lines.add("01-01-2012 20:12:39 A Z C D");
        lines.add("01-01-2012 20:12:39 A F C D E");
        lines.add("01-01-2012 20:12:39 A F C D E Z Z T");
        testSubject.setLines(lines);
        Assert.assertEquals(testSubject.parseToCacheBuilder().size(),3);
        List<Integer> cacheBuilderLengthList=new ArrayList<>();
        testSubject.parseToCacheBuilder().forEach(cacheBuilder -> cacheBuilderLengthList.add(cacheBuilder.getSentenceLength()));
        Collections.sort(cacheBuilderLengthList);
        Assert.assertEquals(4,cacheBuilderLengthList.get(0).intValue());
        Assert.assertEquals(5,cacheBuilderLengthList.get(1).intValue());
        Assert.assertEquals(8,cacheBuilderLengthList.get(2).intValue());
    }

    @Test
    public void verifyReadFileCorrectly() throws Exception{
        testSubject.setFilePathList(Arrays.asList(Paths.get("src/main/resources/input_6.txt")));
        Assert.assertEquals(testSubject.parseToCacheBuilder().size(),1);
    }


    @Test(expected = java.nio.file.NoSuchFileException.class)
    public void verifyExceptionForNonExistingFile() throws Exception{
        testSubject.setFilePathList(Arrays.asList(Paths.get("src/main/resources/nonExisting.txt")));
        testSubject.parseToCacheBuilder();
    }

}