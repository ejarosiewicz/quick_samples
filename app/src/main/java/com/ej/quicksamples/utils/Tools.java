package com.ej.quicksamples.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ej.quicksamples.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Tools used in the project
 *
 * @author Emil Jarosiewicz
 */
public class Tools {

    /**
     * Recognizes, if current file is supported audio file.
     *
     * @param file
     * @return if file is audio.
     */
    public static boolean isAudioFile(File file) {
        boolean audioFileIs = false;
        String fileName = file.getName();
        String[] fileFormats = {".mp3", ".wav", ".ogg", ".midi"};
        for (String format : fileFormats) {
            if (fileName.endsWith(format))
                audioFileIs = true;
        }
        return audioFileIs;
    }


    /**
     * Converts length in milis to time format.
     *
     * @param length length in milis
     * @return string with converted format to hours,minutes and seconds
     */
    public static String computeLength(int length) {
        int time = length / 1000;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        while (time > 3599) {
            hours++;
            time -= 3600;
        }
        while (time > 59) {
            minutes++;
            time -= 60;
        }
        seconds = time;
        return formatTime(hours) + ":" + formatTime(minutes) + ":" + formatTime(seconds);
    }

    /**
     * Formats time appearance in the player.
     *
     * @param time time unit
     * @return formatted time.
     */
    public static String formatTime(int time) {
        if (time > 9)
            return Integer.toString(time);
        else return "0" + Integer.toString(time);
    }

    /**
     * Converts ButtonSet model into json string.
     *
     * @param buttonSet instance of ButtonSet object
     * @return ButtonSet object converted into json
     */
    public static String buttonSetToString(ButtonSet buttonSet) {
        Gson gson = new Gson();
        return gson.toJson(buttonSet);
    }

    /**
     * Converts json string into ButtonSet model.
     *
     * @param buttonSet string with json instance of ButtonSet object
     * @return ButtonSet object converted from json
     */
    public static ButtonSet stringToButtonSet(String buttonSet) {
        Gson gson = new Gson();
        return gson.fromJson(buttonSet, ButtonSet.class);
    }


    /**
     * Returns action play for widget by typing the number of button.
     *
     * @param i number of button
     * @return type of playing action
     */
    public static String getAction(int i) {
        switch (i) {
            case 1:
                return Constants.ACTION_PLAY1;
            case 2:
                return Constants.ACTION_PLAY2;
            case 3:
                return Constants.ACTION_PLAY3;
            case 4:
                return Constants.ACTION_PLAY4;
            case 5:
                return Constants.ACTION_PLAY5;
            case 6:
                return Constants.ACTION_PLAY6;
            default:
                return Constants.ACTION_PLAY1;
        }
    }


    /**
     * Get button id from the layout_buttons.xml by the number.
     *
     * @param i number of the button
     * @return button id
     */
    public static int getButtonId(int i) {
        switch (i) {
            case 1:
                return R.id.button1;
            case 2:
                return R.id.button2;
            case 3:
                return R.id.button3;
            case 4:
                return R.id.button4;
            case 5:
                return R.id.button5;
            case 6:
                return R.id.button6;
            default:
                return R.id.button1;
        }
    }

    /**
     * Get button number from the button id.
     *
     * @param i button id
     * @return button number
     */
    public static int getButtonNumber(int i) {
        switch (i) {
            case R.id.button1:
                return 1;
            case R.id.button2:
                return 2;
            case R.id.button3:
                return 3;
            case R.id.button4:
                return 4;
            case R.id.button5:
                return 5;
            case R.id.button6:
                return 6;
            default:
                return 0;
        }
    }

    /**
     * Gets current button's name from shared preferences
     *
     * @param i                 number of the button
     * @param context context of activity
     * @return string with button name
     */
    public static String getButtonName(int i, Context context) {
                try {
            String buttonSetString = null;
            buttonSetString = readDataFromFile(context,i);
            if (!buttonSetString.equals("")) {
                return ((ButtonSet) (Tools.stringToButtonSet(buttonSetString))).getName();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }


    /**
     * Parser of the filename from directory.
     *
     * @param path full path of the file
     * @return filename
     */
    public static String parseFileName(String path) {
        int end = path.length() - 1;
        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '.' && i > 0)
                end = i;
            if (path.charAt(i) == '/')
                return path.substring(i + 1, end);
        }
        return path;
    }

    /**
     * Cuts filename if is too leng.
     *
     * @param name full filename
     * @return
     */
    public static String properName(String name) {
        if (name.length() > 6)
            return name.substring(0, 4) + "..";
        else return name;
    }


    /**
     * Computes number of cells by widget's size.
     *
     * @param size size of widget in dp
     * @return number of cells
     */
    public static int getCellsForSize(int size) {
        int n = 2;
        while (70 * n - 30 < size) {
            ++n;
        }
        return n - 1;
    }

    /**
     * Returns kind of widget size by rows and columns.
     *
     * @param rows    number of rows
     * @param columns number of columns
     * @return kind of widget size
     */
    public static Constants.WidgetSize getWidgetSize(int rows, int columns) {
        if (rows == 1) {
            if (columns == 1) {
                return Constants.WidgetSize.ONExONE;
            }
            if (columns >= 2) {
                return Constants.WidgetSize.ONExTWO;
            }
        }
        if (rows == 2) {
            if (columns == 1) {
                return Constants.WidgetSize.TWOxONE;
            }
            if (columns >= 2) {
                return Constants.WidgetSize.TWOxTWO;
            }
        }
        if (rows >= 3) {
            if (columns == 1) {
                return Constants.WidgetSize.THREExONE;
            }
            if (columns >= 2) {
                return Constants.WidgetSize.THREExTWO;
            }
        }
        return Constants.WidgetSize.THREExTWO;

    }

    /**
     * Returns widget layout by kind of widget size.
     *
     * @param widgetSize kind of widget size
     * @return widget layout by kind of widget size
     */
    public static int getWidgetLayout(Constants.WidgetSize widgetSize) {
        switch (widgetSize) {
            case THREExTWO:
                return R.layout.layout_buttons;
            case THREExONE:
                return R.layout.layout_buttons_3x1;
            case TWOxTWO:
                return R.layout.layout_buttons_2x2;
            case TWOxONE:
                return R.layout.layout_buttons_2x1;
            case ONExTWO:
                return R.layout.layout_buttons_1x2;
            case ONExONE:
                return R.layout.layout_buttons_1x1;
            default:
                return R.layout.layout_buttons;

        }

    }

    public static void saveDataToFile(Context context,int number,ButtonSet buttonSet) throws IOException {
        ArrayList<String> lines=new ArrayList<String>();
        boolean shouldWriteAtEnd=true;
        fileCreator(context);
        BufferedReader input = new BufferedReader(
                new InputStreamReader(context.openFileInput(Constants.BUTTON_FILE)));
        String line=input.readLine();
        while (line!=null){
            if (Integer.parseInt(line.substring(0,1))==number){
                lines.add(line.substring(0,1)+buttonSetToString(buttonSet)+"\n");
                shouldWriteAtEnd=false;
            }
            else lines.add(line+"\n");
            line=input.readLine();
        }
        if (shouldWriteAtEnd)
            lines.add(Integer.toString(number)+buttonSetToString(buttonSet)+"\n");
        FileOutputStream fos=context.openFileOutput(Constants.BUTTON_FILE,Context.MODE_PRIVATE);
        for (String l:lines)
            fos.write(l.getBytes());
        fos.close();
    }

    public static String readDataFromFile(Context context,int number) throws IOException {
        boolean shouldWriteAtEnd=true;
        fileCreator(context);
        BufferedReader input = new BufferedReader(
                new InputStreamReader(context.openFileInput(Constants.BUTTON_FILE)));
        String line=input.readLine();
        while (line!=null){
            if (Integer.parseInt(line.substring(0,1))==number)
                return line.substring(1);
            line=input.readLine();
        }
        return "";
    }

    public static void fileCreator(Context context) throws IOException {
        File file = context.getFileStreamPath(Constants.BUTTON_FILE);
        if(file == null || !file.exists()) {
            file.createNewFile();
        }
    }

}
