package com.ej.quicksamples.player;

import com.ej.quicksamples.utils.TimeStamp;
import com.ej.quicksamples.utils.Constants;

/**
 * Interface of the Player Listener.
 *
 * @author Emil Jarosiewicz
 */
public interface PlayerListener {

    public void onInitEnd(String name, String path, int duration, int startTime, int endTime);

    public void onReadPosition(int time);

    public void onTimeError(Constants.TimeError errorCode, TimeStamp fromTimeStamp, TimeStamp toTimeStamp);

    public void onTimeSuccess();

    public void onSaveSuccess();

    public void onFileReadError();


}
