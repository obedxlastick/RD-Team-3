package imageTransformation;

import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;



public class ModifyingImages {
	
	//Inverts the colors of each pixel in the image
	public static BufferedImage invertColor(BufferedImage original) {
	 
	    int type = original.getType();
	    
	    //Handling unsupported TIFF color type
	    if (type == 0) {
	        type = BufferedImage.TYPE_INT_RGB;
	    }
		
		BufferedImage changedImage = new BufferedImage(original.getWidth(), original.getHeight(), type);
		
		for(int x = 0; x < original.getWidth(); x++) {
			for(int y = 0; y < original.getHeight(); y++) {
				Color originalColor = new Color(original.getRGB(x, y));
				
				Color invertedColor = new Color(255 - originalColor.getRed(),
												255 - originalColor.getGreen(),
												255 - originalColor.getBlue());
				changedImage.setRGB(x, y, invertedColor.getRGB());
			}
		}
		
		return changedImage;
	}
	
	//Changes half the image width-wise to black pixels
	public static BufferedImage blackOutHalf(BufferedImage original) {
		int type = original.getType();
	    
		//Handling unsupported TIFF color type
		if (type == 0) {
	        type = BufferedImage.TYPE_INT_RGB;
	    }
		
		
		BufferedImage changedImage = new BufferedImage(original.getWidth(), original.getHeight(), type);;
		
		for(int x = 0; x < original.getWidth(); x++) {
			for(int y = 0; y < original.getHeight(); y++) {
				
				if(x < original.getWidth() / 2) {
					Color black = new Color(0, 0, 0);
				
					changedImage.setRGB(x, y, black.getRGB());
				}
				
				else {
					changedImage.setRGB(x, y, original.getRGB(x, y));
				}
			}
		}
		
		return changedImage;
	}
	
	public static void main(String[] args) {
		
		//TIFF Image Format
		try {
			
			BufferedImage tiff = ImageIO.read(new File("Desk.tiff"));
			
			System.out.println("TIFF Image was able to be opened");
			System.out.println("TIFF width: " + tiff.getWidth());
			System.out.println("TIFF height: " + tiff.getHeight());

			BufferedImage tiffColorInverted = invertColor(tiff);
			ImageIO.write(tiffColorInverted, "tiff", new File("InvertedDesk.tiff"));
			System.out.println("Inverted TIFF created");

			
			BufferedImage tiffBlackedOut = blackOutHalf(tiff);
			ImageIO.write(tiffBlackedOut, "tiff", new File("HalfBlackOutFlower.tiff"));
			System.out.println("HalfBlackedOut tiff created" + '\n');
			
			
		}
		
		catch(IOException e) {
			System.out.println("TIFF Image could not be opened: " + e.getMessage());
		}
		
		//PNG Image Format
		try {
			
			BufferedImage png = ImageIO.read(new File("OriginalSuperhero.png"));
			
			System.out.println("PNG Image was able to be opened");
			System.out.println("PNG width: " + png.getWidth());
			System.out.println("PNG height: " + png.getHeight());

			BufferedImage pngColorInverted = invertColor(png);
			ImageIO.write(pngColorInverted, "png", new File("InvertedSuperhero.png"));
			System.out.println("Inverted PNG created");

			
			BufferedImage pngBlackedOut = blackOutHalf(png);
			ImageIO.write(pngBlackedOut, "png", new File("HalfBlackOutSuperhero.png"));
			System.out.println("HalfBlackedOut PNG created" + '\n');
			
			
		}
		
		catch(IOException e) {
			System.out.println("PNG Image could not be opened: " + e.getMessage());
		}
		
		//GIF Image Format
		try {
			
			BufferedImage bmp = ImageIO.read(new File("Landscape.bmp"));
			
			System.out.println("BMP Image was able to be opened");
			System.out.println("BMP width: " + bmp.getWidth());
			System.out.println("BMP height: " + bmp.getHeight());

			BufferedImage bmpColorInverted = invertColor(bmp);
			ImageIO.write(bmpColorInverted, "gif", new File("InvertedLandscape.bmp"));
			System.out.println("Inverted BMP created");

			
			BufferedImage bmpBlackedOut = blackOutHalf(bmp);
			ImageIO.write(bmpBlackedOut, "bmp", new File("HalfBlackOutLandscape.bmp"));
			System.out.println("HalfBlackedOut BMP created" + '\n');
			
			
		}
		
		catch(IOException e) {
			System.out.println("BMP Image could not be opened: " + e.getMessage());
		}
	}
}
