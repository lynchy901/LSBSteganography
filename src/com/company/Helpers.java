package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Scanner;

/**
 * Created by nathan on 5/4/16.
 */
public class Helpers {

    //prints message passed and returns what the user inputs
    public static String getInput(String message) {
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        String input = in.nextLine();
        return input;
    }

    //returns an image at the path provided
    public static BufferedImage getImage(String path) {
        File file = new File(path);
        BufferedImage image = null;

        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Image not found!");
        }

        return image;
    }

    //Creates a copy of the image. I think this avoids certain issues with editing an already-existing image.
    public static BufferedImage getUserSpace(BufferedImage b) {
        BufferedImage new_img  = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = new_img.createGraphics();
        graphics.drawRenderedImage(b, null);
        graphics.dispose(); //release all allocated memory for this image
        return new_img;
    }

    //Turn the image into a byte array.
    public static byte[] getPictureBytes(BufferedImage image) {
        WritableRaster raster = image.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return data.getData();
    }

    //Saves the modified image
    public static boolean saveNewImage(BufferedImage image, File file) {
        try {
            file.delete();
            ImageIO.write(image, "png", file);
            return true;
        } catch(Exception e) {
            System.out.println("File save failed");
            return false;
        }
    }
}
