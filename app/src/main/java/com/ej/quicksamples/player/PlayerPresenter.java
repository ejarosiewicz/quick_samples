package com.ej.quicksamples.player;

import android.content.Context;

import com.ej.quicksamples.utils.PlayerOptons;
import com.ej.quicksamples.utils.PlayerPack;
import com.ej.quicksamples.utils.TimeStamp;

/**
 * Interface iof the Player Presenter.
 *
 * @author Emil Jarosiewicz
 */
public interface PlayerPresenter {

    public void onInit(Context context, PlayerPack playerPack);

    public void onInit(Context context);

    public void onPlay(PlayerOptons playerOptons);

    public void onStop();

    public void onPause();

    public void timeCompare(TimeStamp fromTimestamp,TimeStamp toTimestamp);

    public void onSaveButtonClick(Context context,String fileName,String path,int buttonNumber,PlayerOptons playerOptons);

    public String textCheck(String txt,boolean isMinute);

    public void onFileCheck();

}
