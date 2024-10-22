import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class encodeST {

    public static String ENDING_MESSAGE = "&*09^%$__"; // super arbitrary BUT NEEDS TO MATCH. Just marks the end of the
                                                       // message
                                                       // could and should probably also use a header at the beginning
                                                       // of the message to indicate size, but this is good enough for
                                                       // now

    public static void main(String[] args) {
        try {
            // get the file name from the user
            System.out.println("Enter the name of the image file you want to encode: ");
            Scanner userInput = new Scanner(System.in);
            String fileName = userInput.nextLine();

            // use image path as FileInputStream argument
            InputStream pngFile = new FileInputStream(fileName);
            BufferedImage pngImage = ImageIO.read(pngFile);

            System.out.println("Maximum message length = "
                    + ((3 * pngImage.getHeight() * pngImage.getWidth() / 8) - ENDING_MESSAGE.length()) + " characters.");

            // Get the text file name from the user ****
            System.out.println("Enter the name of the text file containing the message: ");
            String textFileName = userInput.nextLine();
            userInput.close();

            // Read the message from the text file
            String message = readMessageFromFile(textFileName);

            // checks if message is too long for image
            if (message.length() + ENDING_MESSAGE.length() > (3 * pngImage.getHeight() * pngImage.getWidth()) / 8) {
                System.out.println("Message too long!");
                System.exit(1);
            }

            System.out.println("Message being encoded = " + message);

            // extracts the file name excluding the .png extension
            fileName = fileName.substring(0, fileName.indexOf("."));

            // encode the message into new file with ending, then writes it to new file
            ImageIO.write(encodeMessage(pngImage, message + ENDING_MESSAGE), "png", new File(fileName + "ENCODED.png"));
            System.out.println("Message encoded into image file: " + fileName + "ENCODED.png");
        }

        catch (IOException e) {
            System.out.println("Image not found");
        }
    }

    // Read the message from a text file
    private static String readMessageFromFile(String fileName) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                messageBuilder.append(line).append(System.lineSeparator()); // Append line breaks
            }
        }
        return messageBuilder.toString().trim(); // Trim to remove any trailing line breaks
    }

    // given a byte and the number we want the LSB to become, we encode a 0 or 1
    // based on newValue
    public static int encodeBit(int number, int newValue) {
        if (newValue == 0) {
            number = (number & ~1); // bitwise operator that 0's out the last bit of the number
        } else if (newValue == 1) {
            number = (number & ~1) | 1; // bitwise operator that 0's out last bit then makes it 1
        } else {
            System.exit(0);
        } // error

        return number;
    }

    public static BufferedImage encodeMessage(BufferedImage image, String message) {
        int pixelValue = 0;
        int alpha, red, green, blue;

        int height = image.getHeight();
        int width = image.getWidth();

        // converts message to bits
        int[] messageBits = messageToBits(message);

        // create new ARGB buffered image
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int count = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelValue = image.getRGB(x, y);
                alpha = (pixelValue >> 24) & 0xFF; // bitwise operation moves necessary value down to
                red = (pixelValue >> 16) & 0xFF; // last 8 bits, then ANDS it with 0xFF (11111111)
                green = (pixelValue >> 8) & 0xFF; // to only select the last 8 bits
                blue = pixelValue & 0xFF;

                if (count < messageBits.length) {
                    // Encode the next three bits into red, green, and blue channels
                    if (count < messageBits.length) {
                        red = encodeBit(red, messageBits[count++]); // Red LSB
                    }
                    if (count < messageBits.length) {
                        green = encodeBit(green, messageBits[count++]); // Green LSB
                    }
                    if (count < messageBits.length) {
                        blue = encodeBit(blue, messageBits[count++]); // Blue LSB
                    }

                    int newPixelValue = ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8)
                            | (blue & 0xFF);
                    output.setRGB(x, y, newPixelValue);
                } else {
                    output.setRGB(x, y, pixelValue);
                }
            }
        }
        return output;
    }

    // converts a string to bits
    public static int[] messageToBits(String message) {
        int[] messageBits = new int[message.length() * 8]; // each character is 8 bits
        int count = 0;
        int ascii;

        // iterates through each bit in each character
        for (int i = 0; i < message.length(); i++) {
            ascii = message.charAt(i);

            for (int j = 7; j >= 0; j--) {
                messageBits[count++] = (ascii >> j) & 1; // moves necessary bit to last bit, then ANDS it with 1 to
                                                         // select it
            }
        }
        return messageBits;
    }
}
