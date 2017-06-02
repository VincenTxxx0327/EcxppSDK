package com.ecxppsdk.utils;

import java.util.Calendar;

/**
 * Created by zhen on 2016/8/18.
 */
public class TimeUtils {

    public static int[] getCurrentTime() {
        return getDateTime(getCurrentTimeInMillis());
    }

    private static long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }

    public static int[] getDateTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return new int[]{
                calendar.get(Calendar.YEAR),        //0、年
                calendar.get(Calendar.MONTH) + 1,   //1、月
                calendar.get(Calendar.DAY_OF_MONTH),//2、日
                calendar.get(Calendar.HOUR_OF_DAY), //3、时
                calendar.get(Calendar.MINUTE),      //4、分
                calendar.get(Calendar.SECOND),      //5、秒
                calendar.get(Calendar.DAY_OF_WEEK)  //6、周几
        };
    }

    /**播放器将需处理长度转数字*/
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60000;
            if (minute < 60) {
                second = time % 60000/ 1000;
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = ( time - hour * 3600000 - minute * 60000 ) / 1000;
            }
            timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }

    /**播放器时间轴拼接数字*/
    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String getTimeSpace(int hour, int min, int dayToSelect){
        int tempDayToSelect = dayToSelect;
        Calendar calendarTomorrow = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();

        calendarTomorrow.setTimeInMillis(currentTime + tempDayToSelect * 24 * 60 * 60 * 1000);
        int hourTomorrow = calendarTomorrow.get(Calendar.HOUR_OF_DAY);
        int minTomorrow = calendarTomorrow.get(Calendar.MINUTE);

        int resultHour = 0;
        int resultMin = 0;

        if(hourTomorrow>=hour) {//当前时间X日后小时数大于设置小时数 如当前11时，设置了9时，则返回22小时
            resultHour = 24 - (hourTomorrow - hour);
            tempDayToSelect = Math.abs((tempDayToSelect - 1));
        }else{//如当前9小时，设置了11小时，则返回2小时
            resultHour = Math.abs(hourTomorrow - hour);
        }
        if(minTomorrow>=min) {//当前时间X日后分钟数大于设置分钟数 如当前33分钟，设置了24分，则返回51分钟
            resultMin = 60 - (minTomorrow - min);
            resultHour = Math.abs((resultHour-1));
            if(resultMin==60){
                if(minTomorrow - min == 0){
                    resultMin = 0;
                    resultHour++;
                }else{
                    resultMin--;
                }
            }
        }else{//如当前24分钟，设置了33分钟，则返回9分钟
            resultMin = Math.abs(minTomorrow - min);
        }
        if(resultHour==24) {
            if (hourTomorrow - hour == 0) {
                resultHour = 0;
                tempDayToSelect++;
            } else {
                resultHour--;
            }
        }
        if(tempDayToSelect >=7 && (hourTomorrow - hour != 0 || minTomorrow - min !=0)){//若选定了当天，时间大于7日时
            tempDayToSelect = 0;
        }
        if(resultMin!=0 && resultHour !=0 && tempDayToSelect!=0)//非当天非0小时非0分钟内
            return tempDayToSelect + "天" + resultHour + "小时" + resultMin + "分";
        else if(resultMin==0 && resultHour !=0 && tempDayToSelect!=0)//非当天非0小时允许0分钟内
            return tempDayToSelect + "天" + resultHour + "小时";
        else if(resultMin!=0 && resultHour !=0 && tempDayToSelect==0)//当天非0小时非0分钟
            return resultHour + "小时" + resultMin + "分";
        else if(resultMin==0 && resultHour !=0 && tempDayToSelect==0)//当天非0小时允许0分钟
            return resultHour + "小时";
        else if(resultMin!=0 && resultHour ==0 && tempDayToSelect!=0)//非当天允许0小时非0分钟
            return tempDayToSelect + "天" + resultMin + "分";
        else if(resultMin!=0 && resultHour ==0 && tempDayToSelect==0)//当天允许0小时非0分钟
            return resultMin + "分";
        else if(resultMin==0 && resultHour ==0)//允许0小时0分钟
            return tempDayToSelect + "天";
        else
            return tempDayToSelect + "天" + resultHour + "小时" + resultMin + "分";
    }

    public static boolean checkDate(int month, int day){
        if(month == 1 ||month == 3 || month == 5 || month == 7
                ||month == 8 || month == 10 || month == 12){
            return day <= 31 && day > 0;
        }else if(month == 4 ||month == 6 || month == 9 || month == 11){
            return day <= 30 && day > 0;
        }else if(month == 2){
            return day <= 28 && day > 0;
        }else{
            return false;
        }
    }

    /**
     * 将2017截取后两位取17
     * @param year
     * @return
     */
    public static int getHexYear(String year){
        year = year.substring(year.length()-2, year.length());
        return Integer.valueOf(year);
    }
}
