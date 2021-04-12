package com.egorbarinov.paymentschedulegenerator.common;

import lombok.Data;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
public class ScheduleGeneratorUtil {
    public static String formatLocalDate(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(date);
    }

    public static String formatBigDecimalToRuLocale(BigDecimal bigDecimal) {
        Locale myLocale = new Locale.Builder().setLanguage("ru").setRegion("RU").setVariant("POSIX").build();
//        Locale locale = new Locale("ru", "RU", " POSIX");
        NumberFormat format = NumberFormat.getCurrencyInstance(myLocale);
        return format.format(bigDecimal.doubleValue());
    }
}
