# Servicenow assigment
 

* **under src/resources**<br> 
  there are two input files that are used for testing the app.
  - input_6.txt that contains sentences with 6 words (discard date and time)
  - input_4.txt that contains sentences with 4 words (discard date and time)
 
* log4j2.xml configured for log level debug.


### How to use

Initiate an instance of **Investigator** class
pass input file and output file.

`     Investigator investigator = new Investigator();`
<br>
`      investigator.investigateSentences(Paths.get("src/main/resources/input_6.txt"), Paths.get("c:/temp/investigation_result.txt"));` 


#### **Limitations and assumptions**


- The file input contains the same length of sentences.
- The sentences in the file contain data and time.
- There are no duplicate sentences (disregard (data,time) prefix)  
- The "words" in the sentence are separated by space. 

 
    
