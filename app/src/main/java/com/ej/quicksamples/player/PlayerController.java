package com.ej.quicksamples.player;

import android.content.Context;

import com.ej.quicksamples.utils.PlayerOptons;
import com.ej.quicksamples.utils.PlayerPack;
import com.ej.quicksamples.utils.TimeStamp;

/**
 * Interface of Player Controller.
 *
 * @author Emil Jarosiewicz
 */
public interface PlayerController {

    public void onInit(Context context, PlayerPack playerPack);

    public void onPlay(PlayerOptons playerOptons);

    public void onPause();

    public void onStop();

    public void timeCompare(TimeStamp fromTimestamp, TimeStamp toTimestamp);

    public String textCheck(String txt, boolean isMinute);

    public void onSaveButtonClick(Context context, String fileName, String path, int buttonNumber, PlayerOptons playerOptons);

    public boolean onFileCheck(String filepath);

}
