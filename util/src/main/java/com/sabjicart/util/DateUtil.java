
package com.sabjicart.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil
{
    public static String simpleDateFormat = "dd-MM-yyyy";

    public static String formatDateDayMonth (Date date)
    {
        if (null == date)
            return null;
        SimpleDateFormat sdt =
            new SimpleDateFormat("E dd-MM-yyyy 'at' hh:mm:ss a");
        return sdt.format(date);
    }

    //DD Month, YYYY
    public static String today ()
    {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        return dateFormat.format(new Date());
    }

    //DD Month, YYYY
    public static String tomorrow ()
    {
        return nthDayDateInString(new Date(), 1);
    }

    public static String yesterday ()
    {
        return nthDayDateInString(new Date(), -1);
    }

    public static String nextMonth (Date date)
    {
        return nthMonthDate(date, 1);
    }

    public static String nextYear (Date date, String format)
    {
        return nthYearDate(date, 1);
    }

    public static Date nthMinutesDate (Date date, int n)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Add n minutes to the calendar time
        c.add(Calendar.MINUTE, n);

        // convert calendar to date
        Date currentDatePlusNMinues = c.getTime();

        return currentDatePlusNMinues;
    }

    public static Date nthDayDate (Date date, int n)
    {

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date
        c.add(Calendar.DATE, n);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        return currentDatePlusOne;
    }

    public static String nthDayDateInString (Date date, int n)
    {
        //Date currentDate = new Date();
        DateFormat dateFormat = null;

        //format 30 June, 2017
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date
        c.add(Calendar.DATE, n);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        return dateFormat.format(currentDatePlusOne);
    }

    public static String nthMonthDate (Date date, int n)
    {
        DateFormat dateFormat = null;
        //format 30 June, 2017
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date
        c.add(Calendar.MONTH, 1);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();
        return dateFormat.format(currentDatePlusOne);
    }

    public static String nthYearDate (Date date, int n)
    {
        DateFormat dateFormat = null;
        //format 30 June, 2017
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date
        c.add(Calendar.YEAR, 1);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        return dateFormat.format(currentDatePlusOne);
    }

    // Date validation
    public static PropertiesHolder validateDateFormat (String date)
    {
        PropertiesHolder genericUtilResp = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = GenericUtil.isValid(date);
        if (!isNullOrEmpty.isValid()) {
            genericUtilResp.setValid(false);
            genericUtilResp.setMessage(isNullOrEmpty.getMessage());
        }
        else {
            Pattern datePtrn = Pattern.compile(
                "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20|21)\\d\\d)");
            Matcher mtch = datePtrn.matcher(date);
            if (mtch.matches()) {
                genericUtilResp.setValid(true);
            }
            else {
                genericUtilResp.setValid(false);
                genericUtilResp.setMessage("Invalid date format");
            }

        }
        return genericUtilResp;
    }

    public static Date getDateInSimpleFormat (String date)
    {
        if (!StringUtil.nullOrBlankOrEmptyString(date)) {
            Calendar c = Calendar.getInstance();
            String[] dateSplits = date.split("-");
            if (dateSplits.length != 3) {
                return null;
            }
            c.set(Integer.valueOf(dateSplits[2]),
                Integer.valueOf(dateSplits[1]),
                Integer.valueOf(dateSplits[0])
            );
            return c.getTime();
        }
        return null;
    }

    public static LocalDate getDateFromEpochMillis (long millis)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(millis);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        return LocalDate.of(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            // Adjusting Calender Zero based index with LocalDate one based Index
            cal.get(Calendar.DATE)
        );
    }

    public static LocalDateTime getDateTimeFromEpochMillis (long millis)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(millis);
        return LocalDateTime.of(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            // Adjusting Calender Zero based index with LocalDate one based Index
            cal.get(Calendar.DATE),
            cal.get(Calendar.HOUR),
            cal.get(Calendar.MINUTE),
            cal.get(Calendar.SECOND)
        );
    }

    public static long getTimeInMillisAsOf1970 ()
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.set(1970, 1, 1); // LocalDate one based Index
        return cal.getTimeInMillis();
    }

    public static LocalDateTime getLocalDateTimeAsOf1970 ()
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.set(1970, 1, 1); // LocalDate one based Index
        return getDateTimeFromEpochMillis(cal.getTimeInMillis());
    }

    public static LocalDate getInfiniteLocalDateTime ()
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.set(9999, 1, 1); // LocalDate one based Index
        return getDateFromEpochMillis(cal.getTimeInMillis());
    }

    public static long getCurrentDayTimeInHours ()
    {
        return LocalDateTime.now().getHour() * 100 + LocalDateTime.now()
                                                                  .getMinute();
    }

    public static int differenceInMinutes (
        String time24Hours1, String time24Hours2) throws ParseException
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        // Parsing the Time Period
        Date date1 = simpleDateFormat.parse(time24Hours1);
        Date date2 = simpleDateFormat.parse(time24Hours2);

        // Calculating the difference in milliseconds
        long differenceInMilliSeconds =
            Math.abs(date2.getTime() - date1.getTime());

        // Calculating the difference in Minutes
        long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000));

        return (int)differenceInMinutes;

        //        // Calculating the difference in Hours
        //        long differenceInHours
        //            = (differenceInMilliSeconds / (60 * 60 * 1000))
        //            % 24;
        //
        //        // Calculating the difference in Seconds
        //        long differenceInSeconds
        //            = (differenceInMilliSeconds / 1000) % 60;

    }

    // Covert time 1220 Hours to "12:20"
    public static String parseTimeInClockFormat (Integer time)
    {

        String timeInClock = "";
        int hour;
        int minute;
        String hourHand;
        String minuteHand;
        if (time != null) {
            hour = time / 100;
            minute = time % 100;
            hourHand = String.valueOf(hour);
            if (hour < 10) {
                hourHand = "0" + hourHand;
            }
            minuteHand = String.valueOf(minute);
            if (minute < 10) {
                minuteHand = "0" + minuteHand;
            }
            timeInClock = hourHand + ":" + minuteHand;
        }
        return timeInClock;

    }

    public static LocalDate[] getPreviousWeek (LocalDate date)
    {
        final int dayOfWeek = date.getDayOfWeek().getValue();
        final LocalDate from =
            date.minusDays(dayOfWeek + 6); // (dayOfWeek - 1) + 7
        final LocalDate to = date.minusDays(dayOfWeek);

        return new LocalDate[] { from, to };
    }


    public static LocalDateTime getLocalDateTime(LocalDate date, long time) {
        String timeInHours = DateUtil.parseTimeInClockFormat((int) time);

        String[] array = timeInHours.split(":");

        return getLocalDateTimeFromDateAndTime(date,
                Integer.parseInt(array[0]),
                Integer.parseInt(array[1]));
    }

    public static LocalDateTime getLocalDateTimeFromDateAndTime (
        LocalDate date,
        int hours,
        int minutes)
    {
        return LocalDateTime.of(date.getYear(),
            date.getMonthValue(),
            date.getDayOfMonth(),
            hours,
            minutes,
            0
        );
    }

    public static long getZonedTimeInMillis (LocalDate localDate)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.set(
            localDate.getYear(),
            localDate.getMonthValue() - 1,
            localDate.getDayOfMonth()
        ); // LocalDate one based Index

        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        return cal.getTimeInMillis();
    }
}
