package org.worr;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * byr (Birth Year) - four digits; at least 1920 and at most 2002.
 * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
 * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
 * hgt (Height) - a number followed by either cm or in:
 * If cm, the number must be at least 150 and at most 193.
 * If in, the number must be at least 59 and at most 76.
 * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
 * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
 * pid (Passport ID) - a nine-digit number, including leading zeroes.
 * cid (Country ID) - ignored, missing or not.
 */
public class Day04Test {
    @Test
    public void validLowBoundry() {
        //Arrange
        Map<String, String> onePassport = new HashMap<>();
        onePassport.put("byr", "1920");
        onePassport.put("iyr", "2010");
        onePassport.put("eyr", "2020");
        onePassport.put("hgt", "150cm");
        onePassport.put("hcl", "#000000");
        onePassport.put("ecl", "amb");
        onePassport.put("pid", "000000001");
        List<Map<String, String>> list = new ArrayList<>();
        list.add(onePassport);
        long expected = 1;

        //Act
        long actual = Day04.betterAnalyzeData(list);

        //Assert
        assertThat(actual).isEqualTo(expected);
    }
}
