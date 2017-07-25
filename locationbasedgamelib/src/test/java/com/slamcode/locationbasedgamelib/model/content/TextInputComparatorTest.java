package com.slamcode.locationbasedgamelib.model.content;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by smoriak on 25/07/2017.
 */
public class TextInputComparatorTest {

    private static TextComparisonInputElement.TextComparisonConfiguration IgnoreCaseAndNonANChars = new TextComparisonInputElement.TextComparisonConfiguration(true, true);

    @Test
    public void textInputComparator_compare_ignoreCase_ignoreNonANChars_test()
    {
        TextComparisonInputElement.TextInputComparator comparator = new TextComparisonInputElement.TextInputComparator(IgnoreCaseAndNonANChars);

        String o1 = "some place";
        String o2 = "some place";
        int expected = 0;
        int actual = comparator.compare(o1, o2);

        assertEquals(expected, actual);

        o1 = "SOME Place";
        o2 = "sOmE plaCE";
        expected = 0;
        actual = comparator.compare(o1, o2);

        assertEquals(expected, actual);

        o1 = "//\\]SOME_Place";
        o2 = ",,.,s*)(OmE plaCE\"'";
        expected = 0;
        actual = comparator.compare(o1, o2);

        assertEquals(expected, actual);

        o1 = "śsomepląaceę";
        o2 = ",,.,s*)(OmE plaCE\"'";
        expected = 0;
        actual = comparator.compare(o1, o2);

        assertEquals(expected, actual);
    }
}