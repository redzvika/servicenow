package com.servicenow.investigation;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataParser {

    public static Logger logger = LogManager.getLogger("FilesParser");
    private final int prefixToIgnore;
    @Setter
    private List<Path> filePathList;
    @Setter
    private List<String> lines;
    @Getter
    private Integer sentenceLength;

    private final List<Sentence> sentencesList = new ArrayList<>();

    public DataParser(int prefixToIgnore) {
        this.prefixToIgnore = prefixToIgnore;
    }

    public List<Sentence> parseToSentences() throws Exception {
        sentencesList.clear();
        if (filePathList != null) {
            for (Path path : filePathList) {
                parseFile(path);
            }
        } else if (lines != null) {
            parseLines(lines);
        }
        return sentencesList;
    }

    private void parseFile(Path path) throws Exception {
        String currentLine = null;
        List<String> lines = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
            if (!currentLine.startsWith(";")) {
                lines.add(currentLine);
            }
        }
        logger.debug("start:{}", System.currentTimeMillis());
        parseLines(lines);
    }

    private void parseLines(List<String> lines) throws Exception {
        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
            Sentence sentence = new Sentence(lines.get(lineNumber), lineNumber);
            sentence.generateHashValues(prefixToIgnore);
            sentencesList.add(sentence);
            if (sentenceLength == null) {
                sentenceLength = sentence.getHashValueList().size();
            }
        }
    }
}


