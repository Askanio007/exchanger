package com.demo.exchanger.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static ThreadLocal<DateFormat> fixerClientFormat = new ThreadLocal<>();

    private static DateFormat getFixerClientFormat() {
        if (fixerClientFormat.get() == null) {
            fixerClientFormat.set(new SimpleDateFormat("yyyy-MM-dd"));
        }
        return fixerClientFormat.get();
    }

    public static String getFixerRequestDate(Date date) {
        return getFixerClientFormat().format(date);
    }

}
