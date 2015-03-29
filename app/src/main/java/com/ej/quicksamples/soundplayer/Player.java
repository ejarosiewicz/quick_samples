package com.ej.quicksamples.soundplayer;

/**
 * Interface of the Player.
 *
 * @author Emil Jarosiewicz
 */
public interface Player {

    public void play();

    //true, when it's paused
    public boolean pause();

    public void pauseOnly();

    public void stop();

}
