package com.up.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Samsung on 15.04.2017.
 */
public class Helper {
    public static String zone = "America/Los_Angeles";

    public static Long toMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of(zone)).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Long milli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.of(zone));
    }
}
