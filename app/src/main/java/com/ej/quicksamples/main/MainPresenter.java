package com.ej.quicksamples.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;

/**
 * Interface of Main Presenter.
 *
 * @author Emil Jarosiewicz
 */
public interface MainPresenter {

    public void onClickButton(SharedPreferences sharedPreferences,int no);

    public void onInit(Context context,RelativeLayout relativeLayout);


}
