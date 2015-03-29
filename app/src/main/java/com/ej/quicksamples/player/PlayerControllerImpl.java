package com.ej.quicksamples.player;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;

import com.ej.quicksamples.R;
import com.ej.quicksamples.utils.ButtonSet;
import com.ej.quicksamples.utils.PlayerOptons;
import com.ej.quicksamples.utils.PlayerPack;
import com.ej.quicksamples.utils.TimeStamp;
import com.ej.quicksamples.soundplayer.InternMediaPlayer;
import com.ej.quicksamples.utils.Constants;
import com.ej.quicksamples.utils.CustomToast;
import com.ej.quicksamples.utils.Tools;
import com.ej.quicksamples.widget.WidgetProvider;

/**
 * Implementation of the Player Controller.
 *
 * @author Emil Jarosiewicz
 */
public class PlayerControllerImpl implements PlayerController {

    private PlayerListener playerListener;

    /**
     * Operations after initialization. Chcecking of file correctness, initialization of intern
     * media player, sending to the activity properties of sound file.
     *
     * @param context context of the activity
     * @param playerPack Data pack for the operations.
     *                   @see PlayerPack
     */
    @Override
    public void onInit(Context context, PlayerPack playerPack) {

        if (playerPack.getFilepath().equals(""))
            playerPack=parseSavedPath(context,playerPack);
        playerListener = playerPack.getPlayerListener();
        if (onFileCheck(playerPack.getFilepath())){
            if (InternMediaPlayer.getInstancePlayer().getFilepath() == null
                    || !InternMediaPlayer.getInstancePlayer().getFilepath().equals(playerPack.getFilepath())
                    || !InternMediaPlayer.getInstancePlayer().getReceiverId().equals(Constants.RECEIVER_PLAYER)) {
                if (InternMediaPlayer.getInstancePlayer().getMediaPlayer() != null)
                    InternMediaPlayer.getInstancePlayer().getMediaPlayer().stop();
                InternMediaPlayer.getInstancePlayer().setFilepath(playerPack.getFilepath());
                InternMediaPlayer.getInstancePlayer().createPlayer(context);
            }
            InternMediaPlayer.getInstancePlayer().setPlayerListener(playerPack.getPlayerListener());
            int duration = InternMediaPlayer.getInstancePlayer().getDuration();
            if (playerPack.getEndTime() < 0)
                playerPack.setEndTime(duration);
            if (playerPack.getName() == null)
                playerPack.setName(Tools.parseFileName(playerPack.getFilepath()));
            playerPack.getPlayerListener().onInitEnd(playerPack.getName(),
                    playerPack.getFilepath(),
                    duration,
                    playerPack.getStarTime(),
                    playerPack.getEndTime());
        }
    }

    /**
     * Parser of the Player pach from the Shared Preferences (in case, when button's data had been saved).
     *
     * @param context Context of the activity
     * @param playerPack playerpach with shortage of filepath
     * @return parsed player pack
     */
    public PlayerPack parseSavedPath(Context context,PlayerPack playerPack) {
        PlayerPack returnedPlayerPack=playerPack;
        int buttonNumber=playerPack.getButtonNumber();
        if (buttonNumber>-1){
            String buttonString= null;
            try {
                buttonString = Tools.readDataFromFile(context, buttonNumber);
                if (!buttonString.equals("")){
                    ButtonSet buttonSet=Tools.stringToButtonSet(buttonString);
                    returnedPlayerPack.setName(buttonSet.getName());
                    returnedPlayerPack.setFilepath(buttonSet.getPath());
                    returnedPlayerPack.setStarTime(buttonSet.getPlayerOptons().getStartPosition());
                    returnedPlayerPack.setEndTime(buttonSet.getPlayerOptons().getEndPosition());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return returnedPlayerPack;
    }


    /**
     * Plays the sound between start and end position.
     *
     * @param playerOptons setted player options.
     *                     @see PlayerOptons
     */
    @Override
    public void onPlay(PlayerOptons playerOptons) {
        InternMediaPlayer.getInstancePlayer().setStartPosition(playerOptons.getStartPosition());
        InternMediaPlayer.getInstancePlayer().setEndPosition(playerOptons.getEndPosition());
        InternMediaPlayer.getInstancePlayer().play();
    }

    /**
     * Pauses the player.
     */
    @Override
    public void onPause() {
        InternMediaPlayer.getInstancePlayer().pause();
    }



    /**
     * Stops the player.
     */
    @Override
    public void onStop() {
        InternMediaPlayer.getInstancePlayer().stop();
    }

    /**
     * Checks, if setted time is proper. If not, shows error.
     *
     * @param fromTimestamp start timestamp
     * @param toTimestamp end timestamp
     */
    @Override
    public void timeCompare(TimeStamp fromTimestamp, TimeStamp toTimestamp) {
        int duration = InternMediaPlayer.getInstancePlayer().getDuration();
        if (toTimestamp.getStamp() > duration)
            playerListener.onTimeError(Constants.TimeError.DURATION_ERROR, fromTimestamp, new TimeStamp(duration));
        else if (fromTimestamp.getStamp() > toTimestamp.getStamp())
            playerListener.onTimeError(Constants.TimeError.MIN_GREATER_THAN_MAX, toTimestamp, toTimestamp);
        else if (toTimestamp.getStamp() < fromTimestamp.getStamp())
            playerListener.onTimeError(Constants.TimeError.MAX_LESS_THAN_MIN, fromTimestamp, fromTimestamp);
        else playerListener.onTimeSuccess();
    }

    /**
     * checks, if typed number has proper time format.
     *
     * @param txt typed text.
     * @param isMinute
     * @return corrected text.
     */
    @Override
    public String textCheck(String txt, boolean isMinute) {
        String settedText = txt;
        if (settedText.length() > 1 && settedText.charAt(0) == '0')
            settedText = settedText.substring(1, settedText.length());
        if (settedText.equals(""))
            settedText = "0";
        if (isMinute && Integer.parseInt(settedText) > 59)
            settedText = "59";
        return settedText;
    }


    /**
     * Operations after clicking save button. Checks if filename isn't empty, then stops intern media
     * player, and saves sound configuration into shared preferences.
     *
     * @param context context of the activity.
     * @param fileName typed name of file
     * @param path path of file
     * @param buttonNumber number of button
     * @param playerOptons player options.
     *                     @see PlayerOptons
     */
    @Override
    public void onSaveButtonClick(Context context, String fileName, String path, int buttonNumber, PlayerOptons playerOptons) {
        if(fileName.equals(""))
            CustomToast.show(context, R.string.emptyNameError);
        else{
            InternMediaPlayer.getInstancePlayer().stop();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putBoolean(Constants.SHOULD_RESTORE, false).commit();
            ButtonSet buttonSet = new ButtonSet(fileName, path, playerOptons);

            try {
                Tools.saveDataToFile(context,buttonNumber,buttonSet);
                CustomToast.show(context,R.string.saveSuccess);
                WidgetProvider.pushWidgetUpdate(context);
                playerListener.onSaveSuccess();
            } catch (IOException e) {
                CustomToast.show(context,R.string.saveError);
                e.printStackTrace();
            }


        }
    }

    /**
     * Checks, if file exist.
     *
     * @param filepath path of the file.
     * @return
     */
    @Override
    public boolean onFileCheck(String filepath) {
        if (!new File(filepath).exists()) {
            playerListener.onFileReadError();
            return false;
        }
        else return true;
    }


}
