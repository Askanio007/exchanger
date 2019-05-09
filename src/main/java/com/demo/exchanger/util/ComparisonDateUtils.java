package com.demo.exchanger.util;

import java.util.Date;
import static org.apache.commons.lang3.time.DateUtils.isSameDay;

public class ComparisonDateUtils {

    public static boolean isAfterToday(Date date) {
        Date today = new Date();
        return !isSameDay(today, date) && date.after(today);
    }

}
