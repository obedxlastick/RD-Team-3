import java.io.*;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

//Reads in designated files and makes blue value for each pixel 0
 
public class blueEraser 
{
    public static void main(String[] args)
    {
        try 
        {

            File png_image = new File("baseball.png");
			BufferedImage png_buffered = ImageIO.read(png_image);
			ImageIO.write(eraseBlue(png_buffered), "png", new File("baseballNoBlue.png"));
            System.out.println("Png Created");

			File tiff_image = new File("nature.tiff");
			BufferedImage tiff_buffered = ImageIO.read(tiff_image);
			ImageIO.write(eraseBlue(tiff_buffered), "tiff", new File("natureNoBlue.tiff"));
            System.out.println("Tiff Created");

			File bmp_image = new File("NY.bmp");
			BufferedImage bmp_buffered = ImageIO.read(bmp_image);
			ImageIO.write(eraseBlue(bmp_buffered), "bmp", new File("NYNoBlue.bmp"));
            System.out.println("BMP Created");
		}

		catch(IOException e) 
        {
			System.out.println("Image not found");
		}
    }

    public static BufferedImage eraseBlue(BufferedImage image) 
    {
        int pixelValue = 0;
        
        int alpha, red, green, blue;

        int height = image.getHeight();
        int width = image.getWidth();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                 pixelValue = image.getRGB(x, y);
                alpha = (pixelValue >> 24) & 0xFF;  //bitwise operation moves necessary value down to 
                red = (pixelValue >> 16) & 0xFF;    //last 8 bits, then ANDS it with 0xFF (11111111)
                green = (pixelValue >> 8) & 0xFF;   //to only select the last 8 bits
                blue = pixelValue & 0xFF;

                blue = 0;

                pixelValue = (alpha << 24) | (red << 16) | (green << 8) | blue;

                image.setRGB(x, y, pixelValue); 

            }
        }

        return image;
    }
}
