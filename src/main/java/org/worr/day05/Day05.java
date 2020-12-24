package org.worr.day05;

import org.worr.FileHelper;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * Since it column and row numbers never actually mattered, it would've been easier to consider the seat ID a 10-bit binary.
 * Not going to change it now though.
 */
public class Day05 {
    public static void main(String[] args) {
        FileHelper fileHelper = new FileHelper();
        List<String> inputData = fileHelper.getFileLines("day-05.txt");

        OptionalInt part1 = getHighestID(inputData);
        if (part1.isPresent()) {
            System.out.println("Highest ID in part 1: " + getHighestID(inputData).getAsInt());
        }

        System.out.println("Your seat ID: " + getYourSeatID(inputData));
    }

    /**
     * Get highest seat ID
     * @param inputData the list of tickets, in binary format
     * @return optionalint that should contain the highest seat ID
     */
    public static OptionalInt getHighestID(List<String> inputData) {
        BinaryConverter binaryRows = new BinaryConverter('F', 'B');
        BinaryConverter binaryColumns = new BinaryConverter('L', 'R');
        return inputData.stream()
                .mapToInt(dataEntry -> binaryRows.binToDec(dataEntry.substring(0,7)) * 8 + binaryColumns.binToDec(dataEntry.substring(7)))
                .max();
    }

    /**
     * Finds seats with two occupied neighbor seat IDs.
     * @param inputData List of seat numbers in binary format where first 7 bits contains row and last 3 bits contains column
     * @return the seat which is your seat ID (in a list, to make sure there is only one result)
     */
    public static List<Integer> getYourSeatID(List<String> inputData) {
        BinaryConverter binaryRows = new BinaryConverter('F', 'B');
        BinaryConverter binaryColumns = new BinaryConverter('L', 'R');
        List<Integer> seatIDs = inputData.stream()
                .map(dataEntry -> binaryRows.binToDec(dataEntry.substring(0,7)) * 8 + binaryColumns.binToDec(dataEntry.substring(7)))
                .collect(Collectors.toList());

        return seatIDs.stream()
                .filter(currentID -> seatIDs.contains(currentID + 2) && !seatIDs.contains(currentID + 1))
                .collect(Collectors.toList());
    }
}
