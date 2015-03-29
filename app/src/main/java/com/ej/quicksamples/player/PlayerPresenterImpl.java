package com.ej.quicksamples.player;

import android.content.Context;

import com.ej.quicksamples.utils.PlayerOptons;
import com.ej.quicksamples.utils.PlayerPack;
import com.ej.quicksamples.utils.TimeStamp;
import com.ej.quicksamples.soundplayer.InternMediaPlayer;
import com.ej.quicksamples.utils.Constants;

/**
 * Implementation of the Player Presenter.
 *
 * @author Emil Jarosiewicz
 */
public class PlayerPresenterImpl implements PlayerPresenter, PlayerListener {

    private PlayerListener playerActivity;
    private PlayerController playerController;


    public PlayerPresenterImpl(PlayerListener playerView) {
        this.playerActivity = playerView;
        playerController = new PlayerControllerImpl();
    }

    @Override
    public void onInit(Context context, PlayerPack playerPack) {
        playerPack.setPlayerListener(this);
        playerController.onInit(context, playerPack);
    }

    @Override
    public void onInit(Context context) {

    }

    @Override
    public void onPlay(PlayerOptons playerOptons) {
        playerController.onPlay(playerOptons);
    }

    @Override
    public void onStop() {
        playerController.onStop();
    }

    @Override
    public void onPause() {
        playerController.onPause();
    }

    @Override
    public void timeCompare(TimeStamp fromTimestamp, TimeStamp toTimestamp) {
        playerController.timeCompare(fromTimestamp, toTimestamp);
    }

    @Override
    public void onSaveButtonClick(Context context, String fileName, String path, int buttonNumber, PlayerOptons playerOptons) {
        playerController.onSaveButtonClick(context, fileName, path, buttonNumber, playerOptons);
    }

    @Override
    public String textCheck(String txt, boolean isMinute) {
        return playerController.textCheck(txt, isMinute);
    }

    @Override
    public void onFileCheck() {
        playerController.onFileCheck(InternMediaPlayer.getInstancePlayer().getFilepath());
    }


    @Override
    public void onInitEnd(String name, String path, int duration, int startTime, int endTime) {
        playerActivity.onInitEnd(name, path, duration, startTime, endTime);
    }

    @Override
    public void onReadPosition(int time) {
        playerActivity.onReadPosition(time);
    }

    @Override
    public void onTimeError(Constants.TimeError errorCode, TimeStamp fromTimeStamp, TimeStamp toTimeStamp) {
        playerActivity.onTimeError(errorCode, fromTimeStamp, toTimeStamp);
    }

    @Override
    public void onTimeSuccess() {
        playerActivity.onTimeSuccess();
    }

    @Override
    public void onSaveSuccess() {
        playerActivity.onSaveSuccess();
    }

    @Override
    public void onFileReadError() {
        playerActivity.onFileReadError();
    }

}
