package test.com.loomsystems;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataParser {
    private Map<Integer, CacheBuilder> sentenceLengthToCache = new HashMap<>();
    public static Logger logger = LogManager.getLogger("FilesParser");
    @Setter private List<Path> filePathList;
    @Setter private List<String> lines;
    private final int prefixToIgnore;

    public DataParser(int prefixToIgnore) {
        this.prefixToIgnore = prefixToIgnore;
    }

    public List<CacheBuilder> parseToCacheBuilder() throws Exception {
        if (filePathList != null) {
            for (Path path : filePathList) {
                parseFile(path);
            }
        } else if (lines != null) {
            parseLines(lines);
        }
        return sentenceLengthToCache.values().stream().collect(Collectors.toList());
    }

    private void parseFile(Path path) throws Exception {
        String currentLine = null;
        List<String> l = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
        while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
            if (!currentLine.startsWith(";")) {
                l.add(currentLine);
            }
        }
        System.out.println("start:{}" + System.currentTimeMillis());
        parseLines(l);
    }

    private void parseLines(List<String> lines) throws Exception {
        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
            Sentence sentence = new Sentence(lines.get(lineNumber), lineNumber);
            sentence.generateWords(prefixToIgnore);
            CacheBuilder cacheBuilder = sentenceLengthToCache.get(sentence.getSentenceLength());
            if (cacheBuilder == null) {
                cacheBuilder = new CacheBuilder(sentence.getSentenceLength());
                sentenceLengthToCache.put(sentence.getSentenceLength(), cacheBuilder);
            }
            cacheBuilder.addSentence(sentence);
            for (int i = 0; i < sentence.getWords().size(); i++) {
                cacheBuilder.addWord(sentence.getWords().get(i), lineNumber);
            }
        }
    }
}


