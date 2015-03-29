package com.ej.quicksamples.utils;

import com.ej.quicksamples.player.PlayerListener;
import rx.Subscriber;

/**
 * Contains subscriber for Rx Observable, instance of the Player Listener, and sound properties.
 *
 * @author Emil Jarosiewicz
 */
public class PlayerPack {

   private Subscriber<Integer> subscriber;
   private PlayerListener playerListener;
   private String filepath;
   private String name;
   private int starTime;
   private int endTime;
   private int buttonNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerPack() {
        this.starTime=0;
        this.endTime=this.buttonNumber-1;
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(int buttonNumber) {
        this.buttonNumber = buttonNumber;
    }

    public int getStarTime() {
        return starTime;
    }

    public void setStarTime(int starTime) {
        this.starTime = starTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Subscriber<Integer> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber<Integer> subscriber) {
        this.subscriber = subscriber;
    }

    public PlayerListener getPlayerListener() {
        return playerListener;
    }

    public void setPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
