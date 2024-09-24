package steganography;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class imageChange {
	public static void main(String args[]) throws IOException {
		
		File file = new File("C:/Users/kenne/Desktop/snoopBMP.bmp");
		BufferedImage pic = ImageIO.read(file);
		
		for (int i = 0; i < pic.getHeight(); i++) {
			for (int j = 0; j < pic.getWidth(); j++) {
				
				int pixel = pic.getRGB(j,i);
				Color color = new Color(pixel, true);
				
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				
				b = 150;
				r = 50;
				
				color = new Color(r, g, b);
				pic.setRGB(j, i, color.getRGB());
			}
		}
		
		file = new File("C:/Users/kenne/Desktop/snoopNBMP.bmp");
		ImageIO.write(pic, "BMP", file);
		System.out.println("Finished editing.");
	}
}
