package org.worr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day03 {
    static List<String> inputField = new FileHelper().getFileLines("day-03.txt");

    public static void main(String[] args) {
        //Solution part 1: https://adventofcode.com/2020/day/3
        simpleSolutionPartOne();

        //Other solution part 1: https://adventofcode.com/2020/day/3
        List<List<Integer>> instructionsPart1 = new ArrayList<>();
        instructionsPart1.add(List.of(3, 1));
        System.out.println("Test part 2: " + calculateResultPartTwo(instructionsPart1));

        //Solution part 2: https://adventofcode.com/2020/day/3#part2
        List<List<Integer>> instructionsPart2 = new ArrayList<>();
        instructionsPart2.add(List.of(1, 1));
        instructionsPart2.add(List.of(3, 1));
        instructionsPart2.add(List.of(5, 1));
        instructionsPart2.add(List.of(7, 1));
        instructionsPart2.add(List.of(1, 2));
        System.out.println("Answer part 2: " + calculateResultPartTwo(instructionsPart2));
    }

    /**
     *
     * @param instructions List containing lists of integers, where index 0 is steps right and index 1 is steps down
     * @return the product of the number of trees landed on following each instruction.
     */
    private static long calculateResultPartTwo(List<List<Integer>> instructions) {
        return instructions.stream()
                .mapToLong(instruction -> calculatePath(instruction.get(0), instruction.get(1)))
                .reduce(1, (carry, element) -> carry * element);
    }

    private static long calculatePath(int stepsRight, int stepsDown) {
        int amountTrees = 0;
        int xPos = 0;
        int patternWidth = inputField.get(0).length();

        for (int yPos = 0; yPos < inputField.size(); yPos += stepsDown) {
            if (inputField.get(yPos).charAt(xPos) == '#') {
                amountTrees++;
            }
            xPos = (xPos + stepsRight) % patternWidth;
        }
        return amountTrees;
    }

    private static void simpleSolutionPartOne() {
        File file = new File("C:\\Users\\alexa\\IdeaProjects\\advent-of-code-2020\\src\\main\\resources\\day-03.txt"); //todo why does abstract filepath not work here?

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        //Holds X pos
        int xPos = 0;
        int amountTrees = 0;
        int amountOpenSquares = 0;


        while (scanner.hasNextLine()) {
            //Get next row and the destination
            String currentRow = scanner.nextLine();

            char currentPos = currentRow.charAt(xPos);

            //Count tree or open square
            if (currentPos == '#') amountTrees++;
            else amountOpenSquares++;

            //Adjust xPost for next iteration
            xPos += 3;
            xPos = xPos % currentRow.length();
        }

        System.out.println("Landed on " + amountTrees + " trees and " + amountOpenSquares + " open squares");
    }
}
