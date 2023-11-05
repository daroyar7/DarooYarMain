package com.example.darooyar2.utils;

import java.util.Calendar;

public class PersianDate {
    public static String[] monthNames={"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};

    //Vaghti inja ro edit mikonin, taghirato agar mishe tooye GregorianDate ham emal konin ke tarikhaye miladi ham ok bashan
    private static long timeMillis;

    public static int year;
    public static int month;
    public static int day;
    public static int dayWeek;

    public static void install() {
        timeMillis = System.currentTimeMillis();
        install(timeMillis);
    }

    public static void install(long time) {
        timeMillis = time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int[] convertedTime = PersianDate.toSolar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        year = convertedTime[0];
        month = convertedTime[1];
        day = convertedTime[2];
        dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int[] toSolar(int gy, int gm, int gd) {
        int[] out = {(gm > 2) ? (gy + 1) : gy, 0, 0};

        int[] g_d_m = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        out[2] = 355666 + (365 * gy) + ((out[0] + 3) / 4) - ((out[0] + 99) / 100) + ((out[0] + 399) / 400) + gd + g_d_m[gm - 1];

        out[0] = -1595 + (33 * (out[2] / 12053));
        out[2] %= 12053;
        out[0] += 4 * (out[2] / 1461);
        out[2] %= 1461;
        if (out[2] > 365) {
            out[0] += (out[2] - 1) / 365;
            out[2] = (out[2] - 1) % 365;
        }
        if (out[2] < 186) {
            out[1] = 1 + (out[2] / 31);
            out[2] = 1 + (out[2] % 31);
        } else {
            out[1] = 7 + ((out[2] - 186) / 30);
            out[2] = 1 + ((out[2] - 186) % 30);
        }
        return out;
    }

    public static String standardFormatDate() {
        return year + "/" + (month < 10 ? "0" : "") + month + "/" + (day < 10 ? "0" : "") + day + " - " + (getHour() < 10 ? "0" : "") + getHour()
                + ":" + (getMinute() < 10 ? "0" : "") + getMinute();
    }

    public static String quickSplitFormat() {
        return year + "_" + month + "_" + day + "_" + getHour() + "_" + getMinute();
    }

    public static String standardFormatTime() {
        return (getHour() < 10 ? "0" : "") + getHour() + ":" + (getMinute() < 10 ? "0" : "") + getMinute();
    }

    public static String monthName() {
        return monthNames[month - 1];
    }

    public static int calculateDaysBetween(int[] start, int[] end) {
        int daysStart = getDayOfYear(start[0], start[1], start[2]);
        int daysEnd = getDayOfYear(end[0], end[1], end[2]);
        int diffInDays = daysStart - daysEnd;

        int diffInYears = start[0] - end[0];
        int startYear = Math.min(start[0], end[0]);

        for (int i = 1; i <= diffInYears; i++) {
            if (isLeapYear(startYear + i)) {
                diffInDays += 366;
            } else {
                diffInDays += 365;
            }
        }
        return Math.max(diffInDays - 1, 0);
    }

    // function to get the day of year for a given PersianDate
    public static int getDayOfYear(int year, int month, int day) {
        int dayOfYear = day;
        for (int i = 1; i < month; i++) {
            dayOfYear += getDaysInMonth(year, i);
        }
        return dayOfYear;
    }

    // function to get the number of days in a given Persian month
    public static int getDaysInMonth(int year, int month) {
        if (month <= 6) {
            return 31;
        } else if (month <= 11) {
            return 30;
        } else if (isLeapYear(year)) {
            return 30;
        } else {
            return 29;
        }
    }

    // function to check if a given Persian year is a leap year
    public static boolean isLeapYear(int year) {
        int mod = year % 33;
        return mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30;
    }

}
