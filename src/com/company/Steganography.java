package com.company;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by nathan on 5/4/16.
 */
public class Steganography {

    public static BufferedImage insertMessage(String message, BufferedImage image) {
        byte[] msgData = message.getBytes();
        byte length = (byte)message.length();
        byte bitToAdd = 0;
        byte[] picData = Helpers.getPictureBytes(image);

        int picByteCounter = 0; //start at byte 8 so leaves a byte of room to store the length

        //This stores the length of the message in the first 8 bytes
        //loops through all the bits in the byte
        for (int b = 7; b >= 0; b--) {
            bitToAdd = (byte)((length >>> b) & 1); //Note: this is a byte but it's always 0 or 1 because we only add the rightmost bit on the end of the pic byte

            picData[picByteCounter] = (byte)(picData[picByteCounter] & 0xfe); // ANDs with 11111110 to zero out last bit
            picData[picByteCounter] = (byte)(picData[picByteCounter] | bitToAdd); // ORs with bitToAdd (00000001 or 00000000) to change last bit to the bit to add
            System.out.print(bitToAdd);
            picByteCounter++;//makes sure current byte changes each time a new LSB is added
        }

        for (int i = 0; i < msgData.length; i++) {
            for (int b = 7; b >= 0; b--) {
                bitToAdd = (byte)((msgData[i] >>> b) & 1); //Note: this is a byte but it's always 0 or 1 because we only add the rightmost bit on the end of the pic byte

                picData[picByteCounter] = (byte)(picData[picByteCounter] & 0xfe); // ANDs with 11111110 to zero out last bit
                picData[picByteCounter] = (byte)(picData[picByteCounter] | bitToAdd); // ORs with bitToAdd (00000001 or 00000000) to change last bit to the bit to add

                picByteCounter++;//makes sure current byte changes each time a new LSB is added
            }
        }
        return image;
    }

    public static String decode (BufferedImage image) {
        //get length in first 8 bytes
        int length = (int)decodeBytes(0, 1, image)[0];
        System.out.println("Length: " + length);
        String message = new String(decodeBytes(8, length, image));
        System.out.println("message: " + message);
        return "";//message;
    }

    public static byte[] decodeBytes(int startingByte, int length, BufferedImage image) {
        byte[] msgData = new byte[length+startingByte];
        byte[] picData = Helpers.getPictureBytes(image);
        int offset = startingByte; // starts at byte 8 of the photo
        int shiftCount = 0;


        for (int y = 0; y < length; y++) { //for each byte that is being extracted (1 extracted byte = 8 pic bytes)
            int stop = offset + 8;
            byte currByte = 0;
            for (int i = offset; i < stop; i++) {
                //get rightmost bit of image. ie 00000001
                byte bitToAdd = ((byte)(picData[i] | 0xfe));
                bitToAdd = (byte)(bitToAdd & 1);
                //add to msgData then leftshift ie
                currByte = (byte)(currByte | bitToAdd);
                if (shiftCount < 7) {
                    currByte = (byte)(currByte << 1);
                }
                shiftCount++;
            }
            msgData[y] = currByte;
            offset+=8;
            shiftCount = 0;
        }
        return msgData;
    }

}