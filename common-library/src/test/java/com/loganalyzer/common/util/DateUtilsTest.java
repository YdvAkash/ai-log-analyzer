package com.loganalyzer.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void formatUsesSharedTimestampPattern() {
        LocalDateTime timestamp = LocalDateTime.of(2026, 10, 22, 14, 5, 9);

        assertEquals("2026-10-22 14:05:09", DateUtils.format(timestamp));
    }

    @Test
    void formatReturnsNullForNullInput() {
        assertNull(DateUtils.format(null));
    }

    @Test
    void parseReadsTimestampUsingSharedPattern() {
        assertEquals(
                LocalDateTime.of(2026, 10, 22, 14, 5, 9),
                DateUtils.parse("2026-10-22 14:05:09"));
    }

    @Test
    void parseFallsBackToCurrentTimeForNullOrBlankInput() {
        LocalDateTime before = LocalDateTime.now();
        LocalDateTime nullResult = DateUtils.parse(null);
        LocalDateTime blankResult = DateUtils.parse("   ");
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(nullResult);
        assertNotNull(blankResult);
        assertFalse(nullResult.isBefore(before) || nullResult.isAfter(after));
        assertFalse(blankResult.isBefore(before) || blankResult.isAfter(after));
    }

    @Test
    void parseFallsBackToCurrentTimeForMalformedInput() {
        LocalDateTime before = LocalDateTime.now();
        LocalDateTime result = DateUtils.parse("not-a-timestamp");
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(result);
        assertFalse(result.isBefore(before) || result.isAfter(after));
    }
}
