package com.ej.quicksamples.utils;

/**
 * TimeStamp, that contains and converts timestamp described in hours, minutes,seconds,and hundredths
 * or milis.
 *
 * @author Emil Jarosiewicz
 */
public class TimeStamp {

    private int stamp;
    private int hours;
    private int minutes;
    private int seconds;
    private int milis;

    public TimeStamp(int stamp) {
        this.stamp = stamp;
        fromStampToTime();
    }

    public TimeStamp(int hours, int minutes, int seconds, int milis) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milis = milis;
        fromTimeToStamp();
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
        fromStampToTime();
    }

    public int getHours() {
        return hours;

    }

    public void setHours(int hours) {
        this.hours = hours;
        fromTimeToStamp();
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        fromTimeToStamp();
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        fromTimeToStamp();
    }

    public int getMilis() {
        return milis;
    }

    public void setMilis(int milis) {
        this.milis = milis;
        fromTimeToStamp();
    }

    /**
     * converts from milis to hours, minutes, seconds, and hundredths.
     */
    public void fromStampToTime(){
        this.hours =0;
        this.minutes =0;
        this.seconds =0;
        this.milis =0;

       int tim=stamp;
        while (tim>=1000){
            if (tim>= Constants.HOURS_IN_MILIS){
                tim-=Constants.HOURS_IN_MILIS;
                this.hours++;
            } else if (tim>= Constants.MINUTES_IN_MILIS){
                tim-=Constants.MINUTES_IN_MILIS;
                this.minutes++;
            } else if (tim>= Constants.SEONDS_IN_MILIS){
                tim-=Constants.SEONDS_IN_MILIS;
                this.seconds++;
            }
        }
        this.milis=tim;
    }

    /**
     * converts from hours, minutes, seconds, and hundredths to milis.
     */
    public void fromTimeToStamp(){
        this.stamp=this.milis+(this.seconds*Constants.SEONDS_IN_MILIS)
                    +(this.minutes*Constants.MINUTES_IN_MILIS)
                    +(this.hours*Constants.HOURS_IN_MILIS);
    }
}
