package com.lawtin.alarma20;
import java.io.Serializable;

public class Alarm implements Serializable{

    private int id;
    private int hour;
    private int minute;
    private boolean status;
    private String NameAlarm;

    public Alarm(){}

    public Alarm(int hour,int minute,boolean status, String NameAlarm ){
        this.hour=hour;
        this.minute=minute;
        this.status=status;
        this.NameAlarm=NameAlarm;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getHour(){
        return hour;
    }
    public void setHour(int hour){
        this.hour=hour;
    }
    public int getMinute(){
        return minute;
    }
    public void setMinute(int minute){
        this.minute=minute;
    }
    public boolean getStatus(){
        return status;
    }
    public void setStatus(boolean status){
        this.status=status;
    }
    public String getNameAlarm(){
        return NameAlarm;
    }
    public void setNameAlarm(String nameAlarm){
        this.NameAlarm=nameAlarm;
    }

    @Override
    public String toString() {
        String hoursString, minuteString, format;
        if (hour > 12) {
            hoursString = (hour - 12) + "";
            format = " PM";
        } else if (hour==0) {
            hoursString="12";
            format=" AM";
            
        }else if (hour==12){
            hoursString="12";
            format=" PM";
        }else {
            hoursString= hour + "";
            format=" AM";
        }

        if (minute<10){
            minuteString="0"+minute;
        }else {
            minuteString =" "+minute;
        }

        return hoursString + ":"+ minuteString+format;
    }
}
