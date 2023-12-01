package com.health.darooyar.utils;

public class Utils {
    public static String convertToPersianNumber(String str) {
        str = str.replace("0", "۰");
        str = str.replace("1", "۱");
        str = str.replace("2", "۲");
        str = str.replace("3", "۳");
        str = str.replace("4", "۴");
        str = str.replace("5", "۵");
        str = str.replace("6", "۶");
        str = str.replace("7", "۷");
        str = str.replace("8", "۸");
        str = str.replace("9", "۹");
        return str;
    }
}
