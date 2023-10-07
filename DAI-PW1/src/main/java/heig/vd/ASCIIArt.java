package heig.vd;
/**
 * Sources:
 * https://www.baeldung.com/ascii-art-in-java
 * https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/java/awt/Graphics.html
 * https://www.tabnine.com/code/java/methods/java.awt.Graphics/clearRect
 * https://picocli.info/quick-guide.html
 *
 * @author: Sarah Jallon with the help of chat GPT
 */

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true)
public class ASCIIArt implements Runnable {

    @Option(names = { "-s", "--font-size" }, description = "Font size")
    private int fontSize = 14;

    @Option(names = {"-n", "--negative"}, description = "A boolean  that is true when the ascii art should be created " +
            "in negative")

     boolean neg;
    @Parameters(paramLabel = "<inputFile", defaultValue = "./inputFile.txt",
                description = "File to be read as an input.")
    private File inputFile;

    @Parameters(paramLabel = "<outputFile", defaultValue = "./inputFile.txt",
            description = "File where the result will be written.")
    private File outputFile;

    @Override
    public void run() {
        try {

            FileReader fileReader = new FileReader(inputFile, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            FileWriter fileWriter = new FileWriter(outputFile, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int width = 144, height = 32;

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.getGraphics();
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Clear the BufferedImage for each new line of text
                graphics.clearRect(0, 0, width, height);

                // Draw the current line of text onto the BufferedImage
                graphics2D.drawString(line, 12, 24);

                // Generate ASCII art for the current line
                StringBuilder asciiArt = new StringBuilder();
                for (int y = 0; y < height; y++) {
                    StringBuilder lineAscii = new StringBuilder();
                    for (int x = 0; x < width; x++) {
                        if(neg) {
                            lineAscii.append(bufferedImage.getRGB(x, y) == -16777216 ? "*" : " ");
                        } else {
                            lineAscii.append(bufferedImage.getRGB(x, y) == -16777216 ? " " : "*");
                        }

                    }
                    if (!lineAscii.toString().trim().isEmpty()) {
                        asciiArt.append(lineAscii).append("\n");
                    }
                }

                // Write the ASCII art for the current line to the output file
                bufferedWriter.write(asciiArt.toString());
                System.out.println(asciiArt.toString());
            }

            bufferedReader.close();
            fileReader.close();
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args);
        System.exit(exitCode);
    }

}