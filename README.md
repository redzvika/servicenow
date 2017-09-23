under src/resources 
there are two input files that are used for testing the app.
 input_6.txt that contains sentences with 6 word (discard date and time)
 input_4.txt that contain  sentences with 4 word and 5 words.
 
 log4j2.xml configured for log level info.
 
 the output of the program will print to the console .
 log will be under the root of the project .
 
 Investigator is the main class to run the project.
 
 In case you want to run other input files change 
 the line below to your needs.
 currently the prefix size to ignore is 2 (data and time).
 
  dataParser.setFilePathList(Arrays.asList(
                        Paths.get("src/main/resources/input_4.txt"),
                        Paths.get("src/main/resources/input_6.txt")
                ));
   
