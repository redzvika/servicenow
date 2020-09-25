package test.com.servicenow;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Investigator {


    private final static int PREFIX_TO_IGNORE = 2;

    public static Logger logger = LogManager.getLogger("Investigator");

    public static void main(String[] args) {

        Investigator investigator = new Investigator();
        investigator.investigateSentences(Paths.get("src/main/resources/input_6.txt"), Paths.get("c:\\temp\\output.txt"));
    }


    public void investigateSentences(Path fileSource, Path fileOutput) {
        try {

            DataParser dataParser = new DataParser(PREFIX_TO_IGNORE);
            dataParser.setFilePathList(Arrays.asList(fileSource));

            SentencesAnalyzer sentencesAnalyzer = new SentencesAnalyzer(dataParser.parseToSentences(), dataParser.getSentenceLength());
            List<OneWordChangeSentence> oneWordChangeSentenceList = sentencesAnalyzer.analyzeGenerateData();
            logger.debug("finish:" + System.currentTimeMillis());

            StringBuilder builder = new StringBuilder();
            for (OneWordChangeSentence oneWordChangeSentence : oneWordChangeSentenceList) {
                builder.append(oneWordChangeSentence.toString());
                builder.append(OneWordChangeSentence.EOL);
            }

            Files.write(fileOutput, builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
