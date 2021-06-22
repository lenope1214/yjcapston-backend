package com.yjwdb2021.jumanji.data;

import com.yjwdb2021.jumanji.service.exception.MyNullPointerException;
import lombok.Getter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateOperator {

    public static final SimpleDateFormat YYMMDD = new SimpleDateFormat("yyMMdd", Locale.KOREA); // hh : 0-11 HH : 0-23
    public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd", Locale.KOREA); // hh : 0-11 HH : 0-23
    public static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
    public static final SimpleDateFormat SYYMMDD = new SimpleDateFormat("yy/MM/dd", Locale.KOREA); // hh : 0-11 HH : 0-23
    public static final SimpleDateFormat SYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
    public static final SimpleDateFormat SYYYYMMDDHHMMSS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
    public static Date stringToMilisecond(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date parseDate = null;
        try {
            parseDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDate;
    }

    public static String dateToHHMM(Date date, boolean slash) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");;

        if(date != null){
            if(!slash){ // slash == false
                dateFormat = new SimpleDateFormat("HHmm");
            }
            return dateFormat.format(date);
        }
        throw new MyNullPointerException("dateToHHMM", "Date", "date");
    }

    public static String dateToYYMMDD(Date date, boolean slash) {

        if (date != null) {
            if(!slash){
                return YYMMDD.format(date);
            }
            return SYYMMDD.format(date);
        }
        throw new MyNullPointerException("dateToYYYYMMDD", "Date", "date");
    }

    public static String dateToYYYYMMDD(Date date, boolean slash) {

        if (date != null) {
            if(!slash){
                return YYYYMMDD.format(date);
            }
            return SYYYYMMDD.format(date);
        }
        throw new MyNullPointerException("dateToYYYYMMDD", "Date", "date");
    }

    public static Timestamp strToTimestamp(String string) {
        return java.sql.Timestamp.valueOf(string);
    }

    public static Date strToDate(String string, boolean slash){
        try {
            if(slash)return SYYYYMMDD.parse(string);
            return YYYYMMDD.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToYYYYMMDDHHMMSS(Date date){
        if(date == null)return null;
        return YYYYMMDDHHMMSS.format(date);
    }

    public static Date trim(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);

        return calendar.getTime();
    }


//    public static Timestamp stringToTimestamp(Time time){
////        Timestamp timestamp = Timestamp.valueOf(time);
//        Timestamp timestamp = Timestamp.valueOf(time);
//        System.out.println(Timestamp.(time));
//        return timestamp;
//    }
}
