import java.io.*;
import java.util.Scanner;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

public class decodeST 
{
    public static String ENDING_MESSAGE = "&*09^%$__"; //Marks the end of the message

    
    public static void main(String[] args)
    {
        //attempt to get image to decode
    	try 
        {
    	    //gets the name of the image file to decode
            System.out.println("Enter the name of the image you want to decode: ");
            Scanner userInput = new Scanner(System.in);
            String fileName = userInput.nextLine();
            userInput.close();
            
            //uses image path as FileInputStream argument
    	    InputStream pngFile = new FileInputStream(fileName);
	    BufferedImage pngImage = ImageIO.read(pngFile);
			
	    //decode message
            System.out.println("Now trying to decode...");

            //gets the contents of the message from beginning at the start of the file until the ENDING_MESSAGE delimiter
            System.out.println("Decoded message = " + decodeMessage(pngImage).substring(0, decodeMessage(pngImage).indexOf(ENDING_MESSAGE)));
		}
    	
        //image not found, inform user
	catch(IOException e) 
        {
		System.out.println("Image not found");
	}
    }

    //Use masking to get final bit of argument integer
    public static int decodeBit(int number)
    {
        return number & 1; //bitwise operator that returns the last bit of the number
                           //if 0, returns 0. if 1, returns 1
    }

    public static String decodeMessage(BufferedImage image)
    {
        int pixelValue = 0;
        int height = image.getHeight();
        int width = image.getWidth();
        int [] message = new int[height * width]; //each pixel is 1 bit, so total bits is height * width
        int count = 0;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (count < message.length) //messageLength is the length of the message in bits, found in encodeMessage method
                { 
                    pixelValue = image.getRGB(x, y);
                    message[count++] = decodeBit(pixelValue);
                }
            }
        }
        
        return bitsToMessage(message);
    }

    // Converts bits of message into ASCII text
    public static String bitsToMessage(int[] bits) {
        StringBuilder message = new StringBuilder();
    
        // Process the bits in chunks of 8
        for (int i = 0; i < bits.length; i += 8) 
        {
            // Ensure we have 8 bits to form a byte
            if (i + 7 < bits.length) 
            {
                int asciiValue = 0;
    
                // Convert the 8 bits to a single ASCII value
                for (int j = 0; j < 8; j++) 
                {
                    asciiValue = (asciiValue << 1) | bits[i + j]; // Shift left and add the current bit
                }
    
                // Convert ASCII value to character and append to message
                message.append((char)asciiValue);
            }
        }
        
        //Return converted message
        return message.toString();
    }
}
