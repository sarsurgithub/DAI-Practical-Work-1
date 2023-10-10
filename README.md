# DAI-Practical-Work-1

The goal of this practical work was to better understand how git, github, java, java I/Os, intellij and maven work. For this we were introduce to PicoCLI, which is used to create a simple CLI application.


## What the CLI is for

The goal of this CLI is to take some text as an input via a text file. The program will then create an ASCII art based on the text found in the input file. The ASCII art will keep the exact same disposition as the text file (end of lines). Once the ASCII art is created it will be written into an output text file. Both of those files can be specified using their path, or you can simply use the default ones. 
On top of this feature, you can choose to use some option to tweak the look of your ASCII art, for example change the character that is used, or print the ASCII art in negative or choose the encoding. <br>
```Have fun creating some text art!```

## How to build the CLI
The following commands are to be used in the terminal when placed in the DAI-PW1 folder

```
 .\mvnw dependency:resolve clean install package
```
With this command maven will:
- Ensure that all necessary external libraries (dependencies) are downloaded.
- Clean up any previous build artifacts.
- Compile your Java code and create a JAR file.
- Make this JAR file available for use within your project in the target folder.


## How to run the CLI 

### Helper

To print the helper you can use the -h or --help flag

```
java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar -h
```

This will print the following help message:
```
Usage: ASCIIArt [-hnV] [-c=<c>] [-enc=<charsetName>] <inputFile> <outputFile>
      <inputFile>          File to be read as an input. (default:
                             examples/inputs/single_word_input.txt)
      <outputFile>         File where the result will be written. (default:
                             examples/outputs/single_word_output.txt)
  -c=<c>                   Character used for the ASCII art (default: *)
      -enc=<charsetName>   Encoding use to read and write in the files.
                             (default: UTF-8)
  -h, --help               Show this help message and exit.
  -n, --negative           Prints the ASCII art in negative
  -V, --version            Print version information and exit.
```

### Version
Used to print the version of the ASCIIArt app
```
 java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar -V

```
Result:
```
ASCIIArt 1.0
```

### With default params
Run the JAR file using the java command:
```
java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar
```
When the default params are used the read file is examples/inputs/single_word_input.txt and the written file is examples/outputs/single_word_output.txt
The ASCII art is made using the basic character "*" and is printed in positive.
The result file is the following:
```
      ***   ******   *****   *  *                 
      ***   ******  *******  *  *                 
     *****  **  **  **   **  *  *                 
     ** **  ****    **       *  *                 
     ** **  ******  **       *  *                 
    *******    ***  **   **  *  *                 
    **   ** **  **  **   **  *  *                 
    **   ** ******  *******  *  *                 
   ***   *********   *****   *  * 
```

### Using the negative param
Run the JAR file using the java command:
```
java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar -n
```
If we use this command on the same file but using the -n flag we obtain the following result:
```
**************************************************
**************************************************
**************************************************
******   ***      ***     *** ** *****************
******   ***      **       ** ** *****************
*****     **  **  **  ***  ** ** *****************
*****  *  **    ****  ******* ** *****************
*****  *  **      **  ******* ** *****************
****       ****   **  ***  ** ** *****************
****  ***  *  **  **  ***  ** ** *****************
****  ***  *      **       ** ** *****************
***   ***         ***     *** ** *****************
**************************************************
**************************************************
**************************************************
**************************************************
```

### Using explicit input and output files
If we want to use the command with differents files than the default one, run as follow:
```
java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar .\examples\inputs\multiple_words_input.txt .\examples\outputs\multiple_words_output.txt
```

### Using explicit char option
If we want any other character than the default "*" to create our ASCII art we can run the following command:
```
 java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar -c ^ 
 ```
 The result:
 ```
      ^^^   ^^^^^^   ^^^^^   ^  ^                 
      ^^^   ^^^^^^  ^^^^^^^  ^  ^                 
     ^^^^^  ^^  ^^  ^^   ^^  ^  ^                 
     ^^ ^^  ^^^^    ^^       ^  ^                 
     ^^ ^^  ^^^^^^  ^^       ^  ^                 
    ^^^^^^^    ^^^  ^^   ^^  ^  ^                 
    ^^   ^^ ^^  ^^  ^^   ^^  ^  ^                 
    ^^   ^^ ^^^^^^  ^^^^^^^  ^  ^                 
   ^^^   ^^^^^^^^^   ^^^^^   ^  ^                 
 ```

### Using explicit encoding
The result using UTF-8 and a japanese string meaning DAI COURSE 大コース
```
        **                                                                     
         **                                                                     
         **                                ******                               
    ************  ********                *******                               
     *   **        ***  *   ***********       **                                
         **            **    ***********     ***                                
        ****           **                   *****                               
        ** **         ***                  *** ***                              
       *** ***    *******                 ***   **                              
      ***   ****                         **     **                              
    ***      ***                                              
```

For the same string if we use US-ASCII encoding
```
 ******   ******   ******   ******   ******   ******   ******   ******   ******   ******   ******   ******          
     *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *          
     *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *          
     *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *          
     *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *          
     *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *          
     *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *   *    *          
     ******   ******   ******   ******   ******   ******   ******   ******   ******   ******   ******   ******  
```

If we use the japanese EUC-JP it is not working correctly because the file was not encoded in EUC-JP
``` 
        ***                                **  **           
     **********                         ***********         
     ********          ******   ******   * *** *     ****** 
        ****     ****  *    *   *    *     *****     *    * 
     *********** ****  *    *   *    *  ************ *    * 
    ***********  **    *    *   *    *  ** ***       *    * 
      ********   **    *    *   *    *     *******   *    * 
      ********   **    *    *   *    *    ***   **   *    * 
      ********   ****  *    *   *    *  *****   **   *    * 
      *********  ****  ******   ******  ** *******   ****** 
     ** ***  **                            **   **          
```

 ### Using everything at once

 ```
 java -jar .\target\DAI-PW1-1.0-SNAPSHOT.jar examples/inputs/single_word_input.txt .\examples\outputs\single_word_output.txt -n -c ^  -enc US-ASCII
 ```

 ```
 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^   ^^^      ^^^     ^^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^^^   ^^^      ^^       ^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^^     ^^  ^^  ^^  ^^^  ^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^^  ^  ^^    ^^^^  ^^^^^^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^^  ^  ^^      ^^  ^^^^^^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^       ^^^^   ^^  ^^^  ^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^  ^^^  ^  ^^  ^^  ^^^  ^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^  ^^^  ^      ^^       ^^ ^^ ^^^^^^^^^^^^^^^^^
^^^   ^^^         ^^^     ^^^ ^^ ^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 ```