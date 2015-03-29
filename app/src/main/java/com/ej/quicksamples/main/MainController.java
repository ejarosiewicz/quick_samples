package com.ej.quicksamples.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;

/**
 * An interface of Main controller
 *
 * @author Emil Jarosiewicz
 */
public interface MainController {

    public void onClickButton(SharedPreferences sharedPreferences, int no, MainListener mainListener);

    public void onInit(Context context, RelativeLayout relativeLayout, MainListener mainListener);


}
