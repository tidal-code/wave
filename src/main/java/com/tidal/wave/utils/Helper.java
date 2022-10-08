package com.tidal.wave.utils;


import java.nio.file.FileSystems;
import java.util.Random;


public class Helper {


    public static int randomNumberGenerator(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static String randomString(int count) {

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxxtz";

        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * letters.length());
            builder.append(letters.charAt(character));
        }
        return builder.toString();
    }


    public static String getAbsoluteFromRelativePath(String path) {
        return FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
    }


}


