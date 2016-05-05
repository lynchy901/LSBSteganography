package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String mode = Helpers.getInput("Hide message (e) or extract message (d)?");
        if (mode.equals("e")) {
            String path = Helpers.getInput("Please enter path to a directory with the desired image (jpg)");
            String fileName = Helpers.getInput("Please enter name if file without extension (E.G. instead of fipng enter file");
            BufferedImage userSpace = Helpers.getUserSpace(Helpers.getImage(path + "/" + fileName + ".png"));
            String message = Helpers.getInput("Please enter the message to hide");

            BufferedImage stegImage = Steganography.insertMessage(message, userSpace);

            Helpers.saveNewImage(stegImage, new File(path + "/" + fileName + "steg.png"));
        } else if (mode.equals("d")) {
            String path = Helpers.getInput("Please enter path to a directory with the desired image (jpg)");
            String fileName = Helpers.getInput("Please enter name if file without extension (E.G. instead of file.png enter file");
            BufferedImage userSpace = Helpers.getUserSpace(Helpers.getImage(path + "/" + fileName + ".png"));

            String message = Steganography.decode(userSpace);
            System.out.println(message);
        } else {
            System.out.println("invalid choice");
        }



    }
}
