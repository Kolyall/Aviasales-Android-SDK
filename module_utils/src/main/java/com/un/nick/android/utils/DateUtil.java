package com.un.nick.android.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_FORMAT_2 = "MM.dd.yyyy";
    public static final String SHORT_DATE_FORMAT_3 = "MMMM, dd yyyy";

    private static final Calendar getAsDayOfWeek(Calendar calendar, int dayOfWeek) {
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar;
    }

    public static final Calendar asCalendar(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static final Date getAsSundayOfWeek(Date date) {
        return getAsDayOfWeek(asCalendar(date), Calendar.SUNDAY).getTime();
    }

    /**
     * Return the last day of the welbe week
     */
    public static final Date getAsWelbeSundayOfWeek(Date date) {
        final Calendar calendar = asCalendar(date);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            return date;
        } else {
            final Calendar nextWeek = addDays(calendar, 7);
            nextWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            return nextWeek.getTime();
        }
    }

    /**
     * Return the first day of the welbe week
     */
    public static final Date getAsWelbeMondayOfWeek(Date date) {
        final Calendar calendar = asCalendar(date);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            return addDays(calendar, -6).getTime();
        } else {
            return addDays(calendar, -(dayOfWeek - 2)).getTime();
        }
    }

    public static final Calendar addDays(Calendar calendar, int days) {
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar;
    }

    public static String makeDoubleDateTitle(Date firstDay, Date lastDay, DateFormat monthDayFormatter, DateFormat dayOnlyFormatter) {
        return makeDoubleDateTitle(asCalendar(firstDay), asCalendar(lastDay), monthDayFormatter, dayOnlyFormatter);
    }

    public static String makeDoubleDateTitle(Calendar firstDay, Calendar lastDay, DateFormat monthDayFormatter, DateFormat
            dayOnlyFormatter) {
        final String title;
        if (firstDay.get(Calendar.MONTH) == lastDay.get(Calendar.MONTH)) {
            title = monthDayFormatter.format(firstDay.getTime()) + "-" + dayOnlyFormatter.format(lastDay.getTime());
        } else {
            title = monthDayFormatter.format(firstDay.getTime()) + "-" + monthDayFormatter.format(lastDay.getTime());
        }
        return title;
    }

    public static String differenceTimeToString(Date dateStart, Date dateEnd) {

        String timeString = "";
        if (dateStart != null && dateEnd != null) {
            long difference = Math.abs(dateStart.getTime() - dateEnd.getTime());
            timeString = timeToString(difference);
        }
        return timeString;
    }

    public static String timeToString(Date date) {

        String timeString = "";
        if (date != null) {
            long milliseconds = date.getTime();
            timeString = timeToString(milliseconds);
        }
        return timeString;
    }

    public static String timeToString(long milliseconds) {
        String timeString = "";

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        milliseconds = milliseconds - TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds = milliseconds - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);

        if (days != 0) timeString += days == 1 ? days + " day " : days + " days, ";
        timeString += hours == 1 ? hours + " hour " : hours + " hours, ";
        timeString += minutes + " min";

        return timeString;
    }

    public static String currentDateString() {
        return currentDateString(DEFAULT_FORMAT);
    }

    public static String currentDateString(String endFormat) {
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");

        SimpleDateFormat postFormater = new SimpleDateFormat(endFormat);
        postFormater.setTimeZone(gmtTime);

        return postFormater.format(new Date());
    }

    public static String dateToString(String format, Date date) {
        String text = null;
        try {
            text = android.text.format.DateFormat.format(format, date).toString();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return text;
    }

    public static Date stringToDate(String dateString, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            date = dateFormat.parse(dateString);
            return date;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String suffixOfDay(int day) {
        String[] suffix = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        int m = day % 100;
        return suffix[(m > 10 && m < 20) ? 0 : (m % 10)];
    }

    public static void createEvent(Context context, long startTime, long endTime, boolean allDay, String title, String description, String
            location) {
        //        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, allDay);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //       intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    public static String remainingTime(long milliseconds) {
        String timeString = "";

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        milliseconds = milliseconds - TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds = milliseconds - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds = milliseconds - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        timeString += days + ":";
        timeString += (hours < 10) ? "0" + hours + ":" : hours + ":";
        timeString += (minutes < 10) ? "0" + minutes + ":" : minutes + ":";
        timeString += (seconds < 10) ? "0" + seconds : seconds;

        return timeString;
    }

    public static final String MIN_AIRPORT_TIME_ZONE = "-11:00";
    public static final String DATE_FORMAT_REG_EXP = "[^M]*M{3}[^M]*";
    private static final String AM_SYMBOL = "a";
    private static final String PM_SYMBOL = "p";

    public static Calendar getMinCalendarDate() {
        Calendar minDate = new GregorianCalendar(TimeZone.getTimeZone("GMT-11"));
        // Fixes bug of passed dates
        minDate.set(Calendar.DAY_OF_MONTH, minDate.get(Calendar.DAY_OF_MONTH));
        minDate.setTimeZone(TimeZone.getDefault());
        minDate.set(Calendar.HOUR_OF_DAY, 0);
        minDate.set(Calendar.MINUTE, 0);
        minDate.set(Calendar.SECOND, 0);
        minDate.set(Calendar.MILLISECOND, 0);
        return minDate;
    }

    public static Date getMinDate() {
        return getMinCalendarDate().getTime();
    }

    public static Calendar getMaxCalendarDate() {
        Calendar maxDate = new GregorianCalendar(TimeZone.getTimeZone("GMT-11"));
        maxDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) + 1);
        return maxDate;
    }

    @Nullable
    public static Calendar convertToCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static DateFormatSymbols getDateFormatSymbols() {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());
        dateFormatSymbols.setAmPmStrings(new String[]{AM_SYMBOL, PM_SYMBOL});
        return dateFormatSymbols;
    }

    public static boolean isDateBeforeDateShiftLine(Calendar checkDate) {
        // We don't use -12 because no any airports in that zone
        Calendar todayCalendar = getTodayInLastTimezone();
        return todayCalendar.get(Calendar.ZONE_OFFSET) + todayCalendar.getTimeInMillis() >
                checkDate.get(Calendar.ZONE_OFFSET) + checkDate.getTimeInMillis();
    }

    public static boolean isDateBeforeDateShiftLine(Date date) {
        Calendar checkDate = Calendar.getInstance();
        checkDate.setTime(date);
        return isDateBeforeDateShiftLine(checkDate);
    }

    public static Calendar getTodayInLastTimezone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-11"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static boolean isDateBeforeDateShiftLine(String checkDate) {
        // We don't use -12 because no any airports in that zone
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(simpleDateFormat.parse(checkDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isDateBeforeDateShiftLine(calendar);
    }

    public static Date getAmPmTime(Integer hr, Integer min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getCurrentDateInGMTMinus11Timezone() {
        Date date = new Date();
        date.setTime(date.getTime() - 11 * 1000 * 60 * 60 -
                TimeZone.getDefault().getOffset(date.getTime()));
        return date;
    }

    public static Date getCurrentDayMidnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String convertDateFromTo(String date, String formatFrom, String formatTo) {
        SimpleDateFormat fdfFrom = new SimpleDateFormat(formatFrom);
        SimpleDateFormat fdfTo = new SimpleDateFormat(formatTo);
        TimeZone utc = TimeZone.getTimeZone("Etc/UTC");
        fdfFrom.setTimeZone(utc);
        fdfTo.setTimeZone(utc);

        String dateString = convertDateFromTo(date, fdfFrom, fdfTo);
        if (formatTo.matches(DATE_FORMAT_REG_EXP)) {
            dateString = dateString.replace(".", "");
        }

        return dateString;
    }

    public static String convertDateFromTo(String date, SimpleDateFormat formatFrom, SimpleDateFormat formatTo) {
        Date parsedDate = null;
        try {
            parsedDate = formatFrom.parse(date);
        } catch (ParseException e) {
            Log.e("Parse exception", e.getMessage());
        }
        return formatTo.format(parsedDate);
    }


    public static boolean isFirstDateBeforeSecondDateWithDayAccuracy(Date firstDate, Date secondDate) {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(firstDate);

        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.setTime(secondDate);

        return firstCalendar.before(secondCalendar) && !areDatesOfOneDay(firstCalendar, secondCalendar);
    }

    public static boolean areDatesOfOneMonth(Calendar firstDate, Calendar secondDate) {
        return firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR)
                && firstDate.get(Calendar.MONTH) == secondDate.get(Calendar.MONTH);
    }

    public static boolean areDatesOfOneDay(Calendar firstDate, Calendar secondDate) {
        return firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR)
                && firstDate.get(Calendar.MONTH) == secondDate.get(Calendar.MONTH)
                && firstDate.get(Calendar.DAY_OF_MONTH) == secondDate.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isDateMoreThanOneYearAfterToday(Date checkDate) {
        Calendar calendarWithYearPassed = Calendar.getInstance();
        calendarWithYearPassed.add(Calendar.YEAR, 1);

        Calendar checkCalendar = Calendar.getInstance();
        checkCalendar.setTime(checkDate);

        return calendarWithYearPassed.before(checkCalendar);
    }
}
