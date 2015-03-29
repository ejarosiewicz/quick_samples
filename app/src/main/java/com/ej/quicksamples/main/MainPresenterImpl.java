package com.ej.quicksamples.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;

/**
 * Implementation of Main Presenter.
 *
 * @author Emil Jarosiewicz
 */
public class MainPresenterImpl implements MainPresenter, MainListener {

    private MainListener mainListener;
    private MainController mainController;


    public MainPresenterImpl(MainListener mainListener) {
        this.mainListener = mainListener;
        mainController = new MainControllerImpl();
    }


    @Override
    public void onClickButton(SharedPreferences sharedPreferences, int no) {
        mainController.onClickButton(sharedPreferences, no, this);
    }

    @Override
    public void onInit(Context context, RelativeLayout relativeLayout) {
        mainController.onInit(context, relativeLayout, this);
    }

    @Override
    public void onFileExist(int i) {
        mainListener.onFileExist(i);
    }

    @Override
    public void onFileNotExist(int i) {
        mainListener.onFileNotExist(i);
    }

    @Override
    public void onLayoutReload() {
        mainListener.onLayoutReload();
    }

    @Override
    public void onSettingPendingTransition() {
        mainListener.onSettingPendingTransition();
    }

    @Override
    public void onButtonLoad(int i, String name) {
        mainListener.onButtonLoad(i, name);
    }
}
