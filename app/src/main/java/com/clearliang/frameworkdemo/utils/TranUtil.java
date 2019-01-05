package com.clearliang.frameworkdemo.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ClearLiang on 2018/7/9
 * <p>
 * Function :
 */
public class TranUtil {

    /**
     * @功能 将json格式转化为RequestBody格式，方便进行请求接口
     */
    public static RequestBody translateJson(Object json) {
        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    /**
     * @功能 根据起止时间截取时间字符串
     */
    public static String translateFormTime(String startTime, String endTime) {
        return "Time" + startTime.substring(10, startTime.length() - 5) + "-" + endTime.substring(10, endTime.length() - 5);
    }

    /**
     * @功能 将时间戳转化为某格式
     */
    public static String translateToNowTime(long currentTimeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(currentTimeMillis));   // 时间戳转换成时间
        return sd;
    }

    public static String translateToNowTime(long currentTimeMillis, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(currentTimeMillis));   // 时间戳转换成时间
        return sd;
    }

    /**
     * 10:00
     * 10:30
     * 2018-10-20 20:55:00
     * @功能 时间往后延续25分钟
     */
    public static String translateTo25Time(String nowTime){
        String lastTime = "";
        if(nowTime.substring(14,16).equals("00")){
            lastTime = nowTime.substring(0,14)+"25:00";
        }else {
            lastTime = nowTime.substring(0,14)+"55:00";
        }
        return lastTime;
    }

    /**
     * @功能 将秒转换为 00:00:00 的格式
     */
    public static String seconds2Data(long seconds) {
        String result = "";
        if (seconds > 60 * 60) {
            long hour = seconds / 3600;
            if (hour < 10) {
                result = "0" + hour + ":";//01:
            } else {
                result = "" + hour + ":"; //11:
            }

            seconds = seconds % 3600;
            long minute = seconds / 60;
            long second = seconds % 60;
            if (minute < 10) {
                result = result + "0" + minute;//01:05
            } else {
                result = "" + minute;          //01:11
            }
            if (second < 10) {
                result = result + ":0" + second;//01:11:05
            } else {
                result = result + ":" + second; //01:11:11
            }
        } else if ((seconds < 60 * 60) & (seconds > 60)) {
            long minute = seconds / 60;
            long second = seconds % 60;
            if (minute < 10) {
                result = "00:0" + minute;
            } else {
                result = "00:" + minute;
            }
            if (second < 10) {
                result = result+":0" + second;
            } else {
                result = result+":" + second;
            }
        } else {
            if (seconds < 10) {
                result = "00:00:0" + seconds;
            } else {
                result = "00:00:" + seconds;
            }

        }
        return result;
    }

    /**
     * @功能 将list转换为数组
     */
    public static String[] list2Array(List<String> list){
        int size = list.size();
        String[] array = list.toArray(new String[size]);
        return array;
    }

    public static String toMbString(long data){
        double currentSize = (double) data / 1024 / 1024;
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(currentSize);
    }

    public static double toMbDouble(long data){
        return Double.parseDouble(toMbString(data));
    }

    public static int toMbInt(long data){
        return Integer.parseInt(toMbString(data));
    }
}
