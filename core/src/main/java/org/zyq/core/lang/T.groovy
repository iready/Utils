package org.zyq.core.lang

import java.text.SimpleDateFormat

class T {

    static Long time_diff_seconds(Long d) {
        return (System.currentTimeMillis() - d) / 1000;
    }

    static Date parse(String pattern, String date) {
        return new SimpleDateFormat(pattern).parse(date);
    }

    static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    static Calendar getNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar;
    }

    static Integer getNowMonth() {
        return getNow().get(Calendar.MONTH) + 1;
    }

    static Integer getNowYear() {
        return getNow().get(Calendar.YEAR);
    }

    static Integer getMaxDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    static Integer getNowMonthOfDay() {
        return getNow().get(Calendar.DAY_OF_MONTH)
    }

    static Integer getOverMonthOfYear(Integer year) {
        if (Str.notBlank("${year}")) {
            if (year == getNowYear()) {
                return getNowMonth();
            } else {
                return 12;
            }
        }
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static void main(String[] args) {
        println getWeekOfDate(new Date())
    }

}
