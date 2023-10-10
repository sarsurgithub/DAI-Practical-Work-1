package heig.vd;
/**
 * The ASCIIArt program generates ASCII art from text content in a specified input file and writes the result
 * to an output file. It allows customizing the character used for the ASCII art and supports printing in negative mode,
 * as well as choosing the encoding of the files.
 *
 * Sources:
 * https://www.baeldung.com/ascii-art-in-java
 * https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/java/awt/Graphics.html
 * https://www.tabnine.com/code/java/methods/java.awt.Graphics/clearRect
 * https://picocli.info/quick-guide.html
 *
 * @author: Sarah Jallon with the help of chat GPT
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true)
public class ASCIIArt implements Runnable {

    @Option(names = {"-n", "--negative"}, description = "Prints the ASCII art in negative")
    private boolean neg;

    @Parameters(paramLabel = "<inputFile>", defaultValue = "examples/inputs/single_word_input.txt",
            description = "File to be read as an input. (default: ${DEFAULT-VALUE})")
    private File inputFile;

    @Parameters(paramLabel = "<outputFile>", defaultValue = "examples/outputs/single_word_output.txt",
            description = "File where the result will be written. (default: ${DEFAULT-VALUE})")
    private File outputFile;

    @Option(names = "-c", defaultValue = "*",
            description = "Character used for the ASCII art (default: ${DEFAULT-VALUE})")
    private char c;

    @Option(names = "-enc", defaultValue = "UTF-8",
            description = "Encoding use to read and write in the files. (default: ${DEFAULT-VALUE})")
    private String charsetName;

    /**
     * Main function of the program, coming from runnable interface
     */
    @Override
    public void run() {
        long start = System.nanoTime();
        Logger logger = LoggerFactory.getLogger(ASCIIArt.class);

        // Check if the input file is valid (text file)
        if (!isValidTextFile(inputFile)) {
            logger.error("Error: Invalid input file. Please provide a valid text file.");
            return;
        }

        // Check if the output file is valid (text file)
        if (!isValidTextFile(outputFile)) {
            logger.error("Error: Invalid output file. Please provide a valid text file.");
            return;
        }

        //check if valid charset
        try {
            Charset charset = Charset.forName(charsetName);
        } catch (UnsupportedCharsetException e) {
            logger.info("Failure: please use a valid charset");
            return;
        }

        try (FileReader fileReader = new FileReader(inputFile, Charset.forName(charsetName));
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             FileWriter fileWriter = new FileWriter(outputFile, Charset.forName(charsetName));
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Calculate the width required for this line based on the characters in the line
                // the factor 10 was found after some testing
                int lineWidth = line.length() * 10;
                int height = 32;

                // Create a BufferedImage
                BufferedImage bufferedImage = new BufferedImage(lineWidth, height, BufferedImage.TYPE_INT_RGB);
                Graphics graphics = bufferedImage.getGraphics();
                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Draw the current line of text onto the BufferedImage
                graphics2D.drawString(line, 4, 24);

                // Generate ASCII art for the current line
                StringBuilder asciiArt = new StringBuilder();
                for (int y = 0; y < height; y++) {
                    StringBuilder lineAscii = new StringBuilder();
                    for (int x = 0; x < lineWidth; x++) {
                        if (neg) {
                            lineAscii.append(bufferedImage.getRGB(x, y) == -16777216 ? c : " ");
                        } else {
                            lineAscii.append(bufferedImage.getRGB(x, y) == -16777216 ? " " : c);
                        }
                    }
                    //if the line is not empty after deleting white characters we append a line return
                    if (!lineAscii.toString().trim().isEmpty()) {
                        asciiArt.append(lineAscii).append("\n");
                    }
                }

                // Write the ASCII art for the current line to the output file
                bufferedWriter.write(asciiArt.toString());
                bufferedWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Failure :(");
        }

        long end = System.nanoTime();
        logger.info("Success! " + "Elapsed time: " + (end - start) / 1000000 + "ms / " + (end - start) + "ns");

    }

    /**
     * Checks if a file is a valid text file based on its extension and content.
     *
     * @param file The file to be checked.
     * @return True if the file is a valid text file, false otherwise.
     */
    private boolean isValidTextFile(File file) {
        // Check if the file name ends with a valid text file extension
        String fileName = file.getName().toLowerCase();
        if (!fileName.endsWith(".txt") && !fileName.endsWith(".csv") && !fileName.endsWith(".xml")) {
            return false;
        }

        //check the content of the file to ensure it's a text file
        try (FileInputStream inputStream = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                // You can add more content validation logic here if needed
                // For simplicity, we check the first few lines for non-binary content
                if (containsBinaryData(line)) {
                    return false;
                }

                // Limit the number of lines to check
                if (++lineCount >= 5) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger logger = LoggerFactory.getLogger(ASCIIArt.class);
            logger.info("Failure");
            return false;
        }

        return true;
    }

    /**
     * check is a line contains binary data
     * @param line The line of text to be checked.
     * @return true if the line contains binary data, false otherwise.
     */
    private boolean containsBinaryData(String line) {
        // For simplicity, we check for the presence of null bytes (non-printable characters)
        return line.contains("\0");
    }

    /**
     * Main entry point of the program
     * @param args
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args);
        System.exit(exitCode);
    }
}
