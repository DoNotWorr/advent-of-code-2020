package org.worr.day05;

public class BinaryConverter {
    private char off = '0';
    private char on = '1';

    BinaryConverter() {}

    BinaryConverter(char off, char on) {
        this.off = off;
        this.on = on;
    }


    public int binToDec(String input) {
        char currentChar = input.charAt(0);
        if (!(currentChar == on || currentChar == off)) {
            throw new IllegalArgumentException("Not a valid binary");
        }
        if (input.length() == 1) {
            if (currentChar == off) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (currentChar == off)
                return binToDec(input.substring(1));
            else {
                return (int) Math.pow(2.0, input.length() - 1) + binToDec(input.substring(1));
            }
        }
    }
}
