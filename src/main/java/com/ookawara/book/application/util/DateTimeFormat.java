package com.ookawara.book.application.util;

import com.ookawara.book.application.exception.DateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateTimeFormat {

    private String format;

    public DateTimeFormat(String format) {
        this.format = format;
    }

    public LocalDate getFormat() {
        if (format != null) {
            try {
                return LocalDate.parse(format, DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException e) {
                throw new DateFormatException("YYYY/MM/DD の形式（例：2000/01/01）で存在する日付を入力してください。");
            }
        } else {
            return null;
        }
    }

    public String getFormatCheck() {
        if (format != null && !format.isBlank()) {
            if (format.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
                try {
                    LocalDate localDateFormat = LocalDate.parse(format, DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
                    return localDateFormat.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                } catch (DateTimeParseException e) {
                    throw new DateFormatException("存在する日付を入力してください。");
                }
            } else if (format.matches("^[0-9]{4}-[0-9]{2}$") || format.matches("^[0-9]{4}$")) {
                return format;
            } else {
                throw new DateFormatException("YYYY-MM-DD の形式（例：2000-01-01）で年か、年月か、年月日を指定してください。");
            }
        } else {
            return "";
        }
    }
}
