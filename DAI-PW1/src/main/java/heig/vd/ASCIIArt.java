package heig.vd; /**
 * ASCII Art: Basic Picocli based sample application
 * Explanation: <a href="https://picocli.info/quick-guide.html#_basic_example_asciiart">Picocli quick guide</a>
 * Source Code: <a href="https://github.com/remkop/picocli/blob/master/picocli-examples/src/main/java/picocli/examples/i18n/I18NDemo.java">GitHub</a> 
 * @author Andreas Deininger
 */
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true) // |1|
public class ASCIIArt implements Runnable { // |2|

    @Option(names = { "-s", "--font-size" }, description = "Font size") // |3|
    int fontSize = 14;

    @Parameters(paramLabel = "<word>",  // |4|
            description = "Words to be translated into ASCII art.")
    private String word;

    @Parameters(paramLabel = "<inputFile", defaultValue = "./inputFile.txt",
                description = "File to be read as an input.")
    private File inputFile;

    @Parameters(paramLabel = "<outputFile", defaultValue = "./inputFile.txt",
            description = "File where the result will be written.")
    private File outputFile;

    @Override
    public void run() { // |6|
        System.out.println( word);
        System.out.println(inputFile.getPath());
        System.out.println(outputFile.getPath());
        //source: https://www.baeldung.com/ascii-art-in-java
        //create a buffered image using integer mode as image type
      /*  int width = 144, height = 32;
        BufferedImage bufferedImage = new BufferedImage(
                width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics graphics = bufferedImage.getGraphics();
        graphics.setFont(new Font("Dialog", Font.PLAIN, fontSize));
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.drawString(String.join(" ", words), 6, 24);

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                stringBuilder.append(bufferedImage.getRGB(x, y) == -16777216 ? " " : "*");
            }

            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(stringBuilder);
        }*/
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args); // |7|
        System.exit(exitCode); // |8|
    }

}