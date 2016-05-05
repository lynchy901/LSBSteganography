/**
 * Written by Nathan Lynch
 * This app is based on a tutorial found at:
 * http://www.dreamincode.net/forums/topic/27950-steganography/
 */


package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        String mode = Helpers.getInput("Hide message (e) or extract message (d)?");
        String path = Helpers.getInput("Please enter path to a directory containing the desired image (Must be png)");
        String fileName = Helpers.getInput("Please enter name if file without extension (E.G. instead of file.png enter file");
        BufferedImage userSpace = Helpers.getUserSpace(Helpers.getImage(path + "/" + fileName + ".png"));

        if (mode.equals("e")) {

            String message = Helpers.getInput("Please enter the message to hide");
            BufferedImage stegImage = Steganography.insertMessage(message, userSpace);
            Helpers.saveNewImage(stegImage, new File(path + "/" + fileName + "steg.png"));

        } else if (mode.equals("d")) {

            String message = Steganography.decode(userSpace);
            System.out.println(message);

        } else {

            System.out.println("invalid choice");
            
        }



    }
}
