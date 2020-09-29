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


- The file input contains sentences  with the same length
- The sentences in the file contain data and time.
- There are no duplicate sentences (disregard (data,time) prefix)  
- The "words" in the sentence are separated by space. 
- First phase can improved to O(NxM) due to lack of time.

#### Complexity
- **First phase** O(NxM<sup>2</sup>) <br>traverse each line (N) <BR> for each line traverse (M) words <br> concatenate a string from all the words except the missing word  
- **Second phase** O(NxM)  <br>traverse each line (N) <BR> for each line traverse (M) hashValues and push to list of (M) maps. 
- **Third phase** O(NxM<sup>2</sup>) <br> traverse the List of maps (M) <br> for each map traverse all values 
<br>&nbsp;&nbsp;&nbsp;**scenario** : 'no similar sentences',  we will have N hashvalues with list containing 1 sentence
<br>&nbsp;&nbsp;&nbsp;**scenario** : 'all sentences are similar in the same position' , we will have one hashvalue with list containing N sentences (**only one of the maps in the list!**)
<br>&nbsp;&nbsp;&nbsp;**scenario** : 'max similarities of sentences' , we will have N hashvalues with list containing M sentences 
<br> conclusion the complexity is max of all three phases  O(NxM<sup>2</sup>) .
 
    
