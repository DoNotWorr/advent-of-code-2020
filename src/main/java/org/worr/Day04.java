package org.worr;

//ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
//byr:1937 iyr:2017 cid:147 hgt:183cm
//
//iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
//hcl:#cfa07d byr:1929
//
//hcl:#ae17e1 iyr:2013
//eyr:2024
//ecl:brn pid:760753108 byr:1931
//hgt:179cm
//
//hcl:#cfa07d eyr:2025 pid:166559648
//iyr:2011 ecl:brn hgt:59in

//Part 1: All fields required except cid

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class Day04 {
    static final int minBirthYear = 1920;
    static final int maxBirthYear = 2002;

    static final int minIssueYear = 2010;
    static final int maxIssueYear = 2020;

    static final int minExpirationYear = 2020;
    static final int maxExpirationYear = 2030;

    static final int minHeigthInch = 59;
    static final int maxHeigthInch = 76;

    static final int minHeigthCentimeter = 150;
    static final int maxHeigthCentimeter = 193;


    static final Set<String> eyeColors = Stream.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").collect(toSet());

    public static void main(String[] args) {
        FileHelper fileHelper = new FileHelper();
        List<String> unformatedData = fileHelper.getFileLines("day-04.txt"); //todo change to live data (day-04-part2-sample contains 4 valid and 4 invalid passports)

        System.out.println("Number of valid passports in part 1: " + analyzeData(toListOfMap(unformatedData)));
        System.out.println("Number of valid passports in part 2: " + betterAnalyzeData(toListOfMap(unformatedData)));
    }

    public static long analyzeData(List<Map<String, String>> data) {
        return data.stream()
                .filter(entry -> entry.containsKey("byr"))
                .filter(entry -> entry.containsKey("iyr"))
                .filter(entry -> entry.containsKey("eyr"))
                .filter(entry -> entry.containsKey("hgt"))
                .filter(entry -> entry.containsKey("hcl"))
                .filter(entry -> entry.containsKey("pid"))
                .filter(entry -> entry.containsKey("ecl"))
                .count();
    }

    public static long betterAnalyzeData(List<Map<String, String>> data) {
        return data.stream()
                .filter(entry -> entry.containsKey("byr") && isValidBirthYear(entry.get("byr")))
                .filter(entry -> entry.containsKey("iyr") && isValidIssueYear(entry.get("iyr")))
                .filter(entry -> entry.containsKey("eyr") && isValidExpirationYear(entry.get("eyr")))
                .filter(entry -> entry.containsKey("hgt") && isValidHeigth(entry.get("hgt")))
                .filter(entry -> entry.containsKey("hcl") && isValidHairColor(entry.get("hcl")))
                .filter(entry -> entry.containsKey("pid") && isValidPassportID(entry.get("pid")))
                .filter(entry -> entry.containsKey("ecl") && isValidEyeColor(entry.get("ecl")))
                .count();
    }


    /**
     * Checks if a birth year is valid
     *
     * @param birthYear birth year
     * @return true if input contains four digits; at least 1920 and at most 2002. Otherwise it returns false.
     */
    private static boolean isValidBirthYear(String birthYear) {
        return isValidYear(birthYear, minBirthYear, maxBirthYear);
    }

    /**
     * Checks if issue year is valid
     *
     * @param issueYear the year passport was issued
     * @return true if year is within boundry defined in final variables, false if it isn't. False if string cannot be parsed to int
     */
    private static boolean isValidIssueYear(String issueYear) {
        return isValidYear(issueYear, minIssueYear, maxIssueYear);
    }

    /**
     * Checks if expiration year is valid
     *
     * @param expirationYear the year passport will expire
     * @return true if year is within boundry defined in global variables, false if it isn't. False if string cannot be parsed to int
     */
    private static boolean isValidExpirationYear(String expirationYear) {
        return isValidYear(expirationYear, minExpirationYear, maxExpirationYear);
    }

    /**
     * Checks if height is valid. Must be within a boundry defined in global variable. Must be either inches (in) or centimeters (cm)
     *
     * @param heigth heigth in the format of a number followed by "in" or "cm", for example "160cm" or "60in"
     * @return true if heigth is valid
     */
    private static boolean isValidHeigth(String heigth) {
        String unit = heigth.substring(heigth.length() - 2);
        int value;
        try {
            value = Integer.parseInt(heigth.substring(0, heigth.length() - 2));
        } catch (NumberFormatException e) {
            return false;
        }
        return validateHeigth(value, unit);
    }


    /**
     * Validates that haircolor is a hexadecimal in format
     *
     * @param hairColor haircolor
     * @return true if haircolor has format #RRGGBB. Otherwise false.
     */
    private static boolean isValidHairColor(String hairColor) {
        if (hairColor.length() != 7) {
            return false;
        }
        if (!hairColor.startsWith("#")) {
            return false;
        }

        try {
            Integer.parseInt(hairColor.substring(1), 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Checks if eye color is valid
     *
     * @param eyeColor the eye color value in a passport
     * @return true if input exists in the global variable eyeColors
     */
    private static boolean isValidEyeColor(String eyeColor) {
        return eyeColors.contains(eyeColor);
    }

    /**
     * Validates passport ID. A valid passport is a nine-digit number, including leading zeroes.
     *
     * @param passportID passportID value in a passport
     * @return true if input is valid, false if invalid
     * todo Läs på om try-catch är ett bra sätt att validera data
     */
    private static boolean isValidPassportID(String passportID) {
        if (passportID.length() != 9) {
            return false;
        }

        try {
            Integer.parseInt(passportID);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Converts flatdata to nested data.
     *
     * @param flatData each string of flatData contains one or more fields in a passport. Each field is separated by space (" "). Each field consists of a field name and its value, separated by colon (":"). Each passport is separated with an empty string
     * @return a list of maps, each passport stored in map
     */
    public static List<Map<String, String>> toListOfMap(List<String> flatData) {
        //For the current nesting of data to work, each passport data should end with a newline.
        flatData.add("\n");
        //List to hold all formated data
        List<Map<String, String>> resultData = new ArrayList<>();

        //Temporary hashmap to store entry. //Todo not sure if this counts as shared mutability, as only a copy is stored
        Map<String, String> tempEntry = new HashMap<>();

        //Loop through all unformated entries and group the passports as maps contained in a list.
        for (String partialFlatData : flatData) {
            //Split entry
            String[] fields = partialFlatData.split(" ");
            //If index 0 has length 1, that means it was an empty new line and we should add copy of hashmap to the result and clear hashmap for reuse.
            if (fields[0].length() < 2) {
                Map<String, String> resultEntry = new HashMap<>(tempEntry);
                resultData.add(resultEntry);
                tempEntry.clear();

                //Else the line should be split to key
            } else {
                for (String field : fields) {
                    String[] pair = field.split(":");
                    tempEntry.put(pair[0], pair[1]);
                }
            }
        }
        return resultData;
    }

    /**
     * Checks if a string representation of a year is within a given boundry
     *
     * @param year    string representation of year that you want to control
     * @param yearMin inclusive lower boundry
     * @param yearMax inclusive upper boundry
     * @return true if year is within boundry, false if year cannot be parsed to integer or if year is not within boundry
     */
    private static boolean isValidYear(String year, int yearMin, int yearMax) {
        //If value cannot be parsed to int, it's invalid
        int intRepresentation;
        try {
            intRepresentation = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return false;
        }

        //Check that int representation of birth year is within boundries
        return (intRepresentation >= yearMin && intRepresentation <= yearMax);
    }

    /**
     * Validate height given a unit "cm" (centimeter) or "in" (inch)
     *
     * @param heigth heigth
     * @param unit   either "cm" or "in"
     * @return true if within boundry defined in global variable. Otherwise false.
     */
    private static boolean validateHeigth(int heigth, String unit) {
        switch (unit) {
            case "cm":
                return (heigth >= minHeigthCentimeter && heigth <= maxHeigthCentimeter);
            case "in":
                return (heigth >= minHeigthInch && heigth <= maxHeigthInch);
            default:
                return false;
        }
    }
}

