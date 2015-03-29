package com.ej.quicksamples.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ej.quicksamples.utils.ButtonSet;
import com.ej.quicksamples.soundplayer.InternMediaPlayer;
import com.ej.quicksamples.utils.Constants;
import com.ej.quicksamples.utils.Tools;

import java.io.IOException;

/**
 * Receiver of pressed widget buttons.
 *
 * @author Emil Jarosiewicz
 */
public class WidgetReceiver extends BroadcastReceiver {

    private int buttonNumber;


    /**
     * Plays or stops sound defined by the pressed button.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        if (isReceivedAction(intent.getAction())) {

            try {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String currentButton = Constants.BUTTON + Integer.toString(buttonNumber);
                String buttonSetString = Tools.readDataFromFile(context, buttonNumber);
                if (!buttonSetString.equals("")) {
                    ButtonSet buttonSet = Tools.stringToButtonSet(buttonSetString);
                    String lastButton = InternMediaPlayer.getInstanceWidget().getCurrentButton();
                    if (!currentButton.equals(lastButton))
                        setPlayer(buttonSet, context, currentButton);
                    if (InternMediaPlayer.getInstanceWidget().isPlaying())
                        InternMediaPlayer.getInstanceWidget().stop();
                    else {
                        InternMediaPlayer.getInstancePlayer().stop();
                        InternMediaPlayer.getInstanceWidget().play();
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    /**
     * Sets the intern media player by the pressed button.
     *
     * @param buttonSet
     * @param context
     * @param currentButton
     */
    public void setPlayer(ButtonSet buttonSet, Context context, String currentButton) {
        if (InternMediaPlayer.getInstanceWidget().getMediaPlayer() != null)
            InternMediaPlayer.getInstanceWidget().getMediaPlayer().stop();
        InternMediaPlayer.
                getInstanceWidget().
                setCurrentButton(currentButton);
        InternMediaPlayer.getInstanceWidget().setFilepath(buttonSet.getPath());
        InternMediaPlayer.
                getInstanceWidget().
                setStartPosition(buttonSet.getPlayerOptons().getStartPosition());
        InternMediaPlayer.
                getInstanceWidget().
                setEndPosition(buttonSet.getPlayerOptons().getEndPosition());
        InternMediaPlayer.getInstanceWidget().createPlayer(context);
    }

    /**
     * Sets the played button number by the received action.
     *
     * @param action
     * @return
     */

    private boolean isReceivedAction(String action) {
        if (action.equals(Constants.ACTION_PLAY1)) {
            buttonNumber = 1;
            return true;
        }
        if (action.equals(Constants.ACTION_PLAY2)) {
            buttonNumber = 2;
            return true;
        }
        if (action.equals(Constants.ACTION_PLAY3)) {
            buttonNumber = 3;
            return true;
        }
        if (action.equals(Constants.ACTION_PLAY4)) {
            buttonNumber = 4;
            return true;
        }
        if (action.equals(Constants.ACTION_PLAY5)) {
            buttonNumber = 5;
            return true;
        }
        if (action.equals(Constants.ACTION_PLAY6)) {
            buttonNumber = 6;
            return true;
        } else return false;
    }


}
