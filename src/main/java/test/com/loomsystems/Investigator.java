package test.com.loomsystems;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.Arrays;

public class Investigator {


    private final static int PREFIX_TO_IGNORE=2;

    public static Logger logger =  LogManager.getLogger("Investigator");
    public  static void main (String []args){
        try {

                DataParser dataParser=new DataParser(PREFIX_TO_IGNORE);
                dataParser.setFilePathList(Arrays.asList(
                        Paths.get("src/main/resources/input_4.txt"),
                        Paths.get("src/main/resources/input_6.txt")
                ));
                CacheAnalyzer analyzer=new CacheAnalyzer(dataParser.parseToCacheBuilder());
                analyzer.analyze();
                System.out.println("finish:"+System.currentTimeMillis());

            //System.out.print(input);
        }catch (Exception e){
            e.printStackTrace();
        }
    }







}
