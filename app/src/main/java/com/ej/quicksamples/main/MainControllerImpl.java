package com.ej.quicksamples.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import java.util.TreeSet;
import com.ej.quicksamples.utils.Constants;
import com.ej.quicksamples.utils.Tools;


/**
 * Implementation of Main Controller.
 *
 * @author Emil Jarosiewicz
 */
public class MainControllerImpl implements MainController {

    private TreeSet<Integer> buttonLoadedSet;


    /**
     * Decides, if next activity should be Explorer Activity (in case when current button hasn't got saved
     * settinge), or Player Activity (when button has got settings).
     *
     * @param sharedPreferences Shared preferences from Main Activity
     * @param no                Number of clicked button
     * @param mainListener      current listener
     */
    @Override
    public void onClickButton(SharedPreferences sharedPreferences, int no, MainListener mainListener) {
        sharedPreferences.edit().putBoolean(Constants.SHOULD_RESTORE, true).commit();
        if (buttonLoadedSet.contains(no))
            mainListener.onFileExist(no);
        else mainListener.onFileNotExist(no);
    }


    /**
     * Initialization of Activity - loads buttons names, and initializes of starting animation.
     *
     * @param context           context of activity
     * @param relativeLayout    content layout for starting animation
     * @param mainListener      Main Listener for initialization's feedback
     */
    @Override
    public void onInit(final Context context, final RelativeLayout relativeLayout, MainListener mainListener) {
        loadButtonsName(context, mainListener);
        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getBoolean(Constants.SHOULD_ANIMATE, false))
            relativeLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    relativeLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation(relativeLayout);
                    sharedPreferences.edit().putBoolean(Constants.SHOULD_ANIMATE, false).commit();
                    return true;
                }
            });
        else mainListener.onSettingPendingTransition();
    }


    private void loadButtonsName(Context context, MainListener mainListener) {
        buttonLoadedSet = new TreeSet<Integer>();
        for (int i = 1; i <= Constants.NUMBER_OF_BUTTONS; i++) {
            String buttonString = Tools.getButtonName(i, context);
            if (!buttonString.equals(""))
                buttonLoadedSet.add(i);
            mainListener.onButtonLoad(i, buttonString);
            ;
        }
    }

    private void startIntroAnimation(RelativeLayout relativeLayout) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            relativeLayout.setAlpha(0);
            relativeLayout.setRotation(360);
            relativeLayout.setRotationY(90);
            relativeLayout.setRotationX(90);
            relativeLayout.setScaleX(0);
            relativeLayout.setScaleY(0);
            relativeLayout.animate()
                    .alpha(1)
                    .rotation(0)
                    .rotationY(0)
                    .rotationX(0)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(800)
                    .start();
        }
    }

}
