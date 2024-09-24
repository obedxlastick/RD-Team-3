import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageTransformation extends JPanel {
    private BufferedImage image;

    public ImageTransformation(String filePath) {
        try {
            // Load the image
            image = ImageIO.read(new File(filePath));

            // Apply an RGB inversion transformation
            invertImageColors();

            // Pixelate the Image
            pixelate(10);

            // Save the transformed image under a new name
            saveImage("penguin_transformed.jpeg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to invert the colors of the image
    private void invertImageColors() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = image.getRGB(x, y);
                Color color = new Color(rgba, true);
                // Invert the RGB values
                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();
                // Set the new RGB value
                Color invertedColor = new Color(red, green, blue, color.getAlpha());
                image.setRGB(x, y, invertedColor.getRGB());
            }
        }
    }

    private void pixelate(int blockSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                int redSum = 0, greenSum = 0, blueSum = 0, pixelCount = 0;

                // Sum colors in the block
                for (int by = 0; by < blockSize && y + by < height; by++) {
                    for (int bx = 0; bx < blockSize && x + bx < width; bx++) {
                        Color color = new Color(image.getRGB(x + bx, y + by));
                        redSum += color.getRed();
                        greenSum += color.getGreen();
                        blueSum += color.getBlue();
                        pixelCount++;
                    }
                }

                // Compute average color
                int avgRed = redSum / pixelCount;
                int avgGreen = greenSum / pixelCount;
                int avgBlue = blueSum / pixelCount;
                Color avgColor = new Color(avgRed, avgGreen, avgBlue);

                // Apply the average color to the block
                for (int by = 0; by < blockSize && y + by < height; by++) {
                    for (int bx = 0; bx < blockSize && x + bx < width; bx++) {
                        image.setRGB(x + bx, y + by, avgColor.getRGB());
                    }
                }
            }
        }
    }

    // Method to save the image
    private void saveImage(String outputFilePath) {
        try {
            File outputfile = new File(outputFilePath);
            // Save the image as a JPEG (or another format like "png")
            ImageIO.write(image, "jpg", outputfile);
            System.out.println("Image saved as: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Transformation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Load and display the image
        ImageTransformation imagePanel = new ImageTransformation("penguin.jpeg");
        frame.add(imagePanel);
        frame.setVisible(true);
    }
}
