package com.ej.quicksamples.utils;

/**
 * Constants of the project.
 *
 * @author Emil Jarosiewicz
 */
public class Constants {

    //types for adapter
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_HEADER = 1;

    //path for load files
    public static final String FILE_PATH="FILE_PATH";
    public static final String BUTTON="BUTTON";
    public static final String BUTTON_NUMBER="BUTTON_NUMBER";

    //receiver's id
    public static final String RECEIVER_PLAYER="RECEIVER_PLAYER";
    public static final String RECEIVER_WIDGET="RECEIVER_WIDGET";

    //actions
    public static final String ACTION_PLAY1="com.ej.intent.action.ACTION_PLAY1";
    public static final String ACTION_PLAY2="com.ej.intent.action.ACTION_PLAY2";
    public static final String ACTION_PLAY3="com.ej.intent.action.ACTION_PLAY3";
    public static final String ACTION_PLAY4="com.ej.intent.action.ACTION_PLAY4";
    public static final String ACTION_PLAY5="com.ej.intent.action.ACTION_PLAY5";
    public static final String ACTION_PLAY6="com.ej.intent.action.ACTION_PLAY6";

    //should restore player
    public static final String SHOULD_RESTORE="SHOULD_RESTORE";


    //Button file name
    public static final String BUTTON_FILE="ButtonFile";

    //should start animation
    public static final String SHOULD_ANIMATE="SHOULD_ANIMATE";

    //number of the all buttons
    public static final int NUMBER_OF_BUTTONS=6;

    //time
    public static final int SEONDS_IN_MILIS=1000;
    public static final int MINUTES_IN_MILIS=60000;
    public static final int HOURS_IN_MILIS=3600000;

    //size of widget
    public static final int WIDTH_SINGLE=120;
    public static final int WIDTH_DOUBLE=240;
    public static final int HEIGHT_SINGLE=120;
    public static final int HEIGHT_DOUBLE=120;
    public static final int HEIGHT_TRIPLE=240;

    /**
     * Kinds of Time Erros typed i n Player Activity.
     * @see PlayerControllerImpl
     */
    public enum TimeError{DURATION_ERROR,MIN_GREATER_THAN_MAX,MAX_LESS_THAN_MIN};


    /**
     * Description of the widget size.
     */
    public enum WidgetSize{ONExONE,ONExTWO,TWOxONE,TWOxTWO,THREExONE,THREExTWO};

}
