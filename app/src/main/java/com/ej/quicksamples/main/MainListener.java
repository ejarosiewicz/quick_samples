package com.ej.quicksamples.main;

/**
 * Interface of listener's actions
 *
 * @author Emil Jarosiewicz
 */
public interface MainListener {

    public void onButtonLoad(int i, String name);

    public void onFileExist(int i);

    public void onFileNotExist(int i);

    public void onLayoutReload();

    public void onSettingPendingTransition();

}
