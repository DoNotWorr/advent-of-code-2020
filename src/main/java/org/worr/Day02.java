package org.worr;

import java.util.List;

public class Day02 {
    public static void main(String[] args) {

        FileHelper fileHelper = new FileHelper();
        List<String> listString = fileHelper.getFileLines("day-02.txt");

        //Part 1
        int partOneValidPasswordCounter = 0;
        for (String entry : listString) {
            if (validatePassword(entry)) {
                partOneValidPasswordCounter++;
            }
        }

        System.out.println("Number of entries in password list: " + listString.size());
        System.out.println("Number of valid password: " + partOneValidPasswordCounter);

        //Part 2
        //Part 1
        int partTwoValidPasswordCounter = 0;
        for (String entry : listString) {
            if (differentValidatePassword(entry)) {
                partTwoValidPasswordCounter++;
            }
        }

        System.out.println("Number of entries in password list: " + listString.size());
        System.out.println("Number of valid password: " + partTwoValidPasswordCounter);


    }

    public static boolean validatePassword(String entry) {
        //"1-3" a: abcde
        String[] fields = entry.split(" ");

        //"1-3"
        String[] tempThreshold = fields[0].split("-");

        //1, 3
        int lowThreshold = Integer.parseInt(tempThreshold[0]);
        int highThreshold = Integer.parseInt(tempThreshold[1]);

        //'a'
        char requiredChar = fields[1].charAt(0);

        //Count how many times required character apprears in string
        int counter = 0;
        for(int i = 0; i < fields[2].length(); i++) {
            if (fields[2].charAt(i) == requiredChar) {
                counter++;
            }
        }

        //Returns true if the password passed requirement
        return (counter >= lowThreshold && counter <= highThreshold);
    }

    public static boolean differentValidatePassword(String entry) {
        //"1-3" a: abcde
        String[] fields = entry.split(" ");

        //"1-3"
        String[] tempThreshold = fields[0].split("-");

        //0, 2 (converted to 0-index)
        int firstIndex = Integer.parseInt(tempThreshold[0]) - 1;
        int secondIndex = Integer.parseInt(tempThreshold[1]) - 1;

        //'a'
        char importantChar = fields[1].charAt(0);

        //XOR
        if(fields[2].charAt(firstIndex) == fields[2].charAt(secondIndex)) {
            return false;
        } else {
            return (fields[2].charAt(firstIndex) == importantChar || fields[2].charAt(secondIndex) == importantChar);
        }
    }
}
