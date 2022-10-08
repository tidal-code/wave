package com.tidal.wave.webelement;

public class IframeNewHandler {


    int limit = 5;
    int value = 0;

    public void printElement(){
        for (int i = 0; i <= limit ; i++) {
            System.out.println("value is " + value);
        }
        value = value + 1;
    }

    public static void main(String[] args) {
        new IframeNewHandler().printElement();
    }
}
