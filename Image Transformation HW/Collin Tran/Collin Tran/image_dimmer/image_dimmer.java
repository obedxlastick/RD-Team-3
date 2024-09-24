package image_dimmer;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class image_dimmer {
	
	private static BufferedImage dim(BufferedImage picture) {
			//get image width and height
			int image_width = picture.getWidth();
			int image_height = picture.getHeight();
			
			//if unsupported, make image type rgb
			int picture_type = picture.getType();
			if(picture_type == 0) {
				picture_type = 2;
			}
			
			//create empty output image
			BufferedImage output = new BufferedImage(image_width, image_height, picture_type);
			
			//traverse all pixels in image matrix
			for(int i = 0; i < image_width; i++) {
				for(int j = 0; j < image_height; j++) {
					//extract alpha and rgb channels from current pixel
					int curr_pixel = picture.getRGB(i, j);
					int alpha = (curr_pixel >> 24) & 0xFF; 
					int red = (curr_pixel >> 16) & 0xFF;
					int green = (curr_pixel >> 8) & 0xFF;
					int blue = curr_pixel & 0xFF;
					
					//dim rgb channels by 50%
					red = Math.max(0, (int)(red * .50));
					green = Math.max(0, (int)(green * .50));
					blue = Math.max(0, (int)(blue * .50));
					
					//concatenate binary values of alpha and channels
					int dimmed_pixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
					
					//set corresponding pixel in output to dimmed value
					output.setRGB(i, j, dimmed_pixel);
				}
			}
			
			return output;
			//output dimmed image
			///ImageIO.write(output, "jpg", new File(dir + "\\" + out_name));

	}
	
	public static void main(String[] args) throws IOException{
		//make this the current directory this is running in, images should be in same location
		String dir = "";
		try {
			//attempt to dim png
			File png_image = new File(dir + "\\image.png");
			BufferedImage png_buffered = ImageIO.read(png_image);
			ImageIO.write(dim(png_buffered), "png", new File(dir + "\\image_dim.png"));
			
			//attempt to dim tiff
			File tiff_image = new File(dir + "\\image2.tiff");
			BufferedImage tiff_buffered = ImageIO.read(tiff_image);
			ImageIO.write(dim(tiff_buffered), "tiff", new File(dir + "\\image2_dim.tiff"));
			
			//attempt to dim bmp
			File bmp_image = new File(dir + "\\image3.bmp");
			BufferedImage bmp_buffered = ImageIO.read(bmp_image);
			ImageIO.write(dim(bmp_buffered), "bmp", new File(dir + "\\image3_dim.bmp"));
			
			System.out.println("Dimming complete");
		}
		//image in one of the attempts was not found
		catch(IOException e) {
			System.out.println("Image not found");
		}
			
	}
}
