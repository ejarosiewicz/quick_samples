package com.ej.quicksamples.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.ej.quicksamples.utils.Constants;
import com.ej.quicksamples.utils.Tools;


/**
 * Provider of the button's widget.
 *
 * @author Emil Jarosiewicz
 */

public class WidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        pushWidgetUpdate(context);
    }

    /**
     * Preparing widget's intent
     *
     * @param views   remote views of widget
     * @param context context of widget
     * @param number  number of button
     */
    private static void preparePendingIntent(RemoteViews views, Context context, int number) {
        Intent intent = new Intent();
        intent.setAction(Tools.getAction(number));
        intent.putExtra(Constants.BUTTON_NUMBER, number);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(Tools.getButtonId(number), pendingIntent);
        String buttonName = Tools.getButtonName(number, context);
        if (!buttonName.equals(""))
            views.setTextViewText(Tools.getButtonId(number), buttonName);
    }


    /**
     * Static method for updating widget from the activity
     *
     * @param context
     */
    public static void pushWidgetUpdate(Context context) {
        ComponentName myWidget = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        for (int i = 1; i <= Constants.NUMBER_OF_BUTTONS; i++) {
            RemoteViews views = prepareRemoteViews(context, i);
            manager.updateAppWidget(myWidget, views);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        ComponentName myWidget = new ComponentName(context, WidgetProvider.class);
        RemoteViews views = prepareRemoteViews(context, appWidgetId);
        appWidgetManager.updateAppWidget(myWidget, views);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * Preparing remote views by te saved options, and widget's size.
     *
     * @param context
     * @param appWidgetId
     * @return prepared RemoteViews.
     */
    private static RemoteViews prepareRemoteViews(Context context, int appWidgetId) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        Bundle options = manager.getAppWidgetOptions(appWidgetId);
        int rows = Tools.getCellsForSize(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT));
        int columnss = Tools.getCellsForSize(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH));
        Constants.WidgetSize widgetSize = Tools.getWidgetSize(rows, columnss);
        return preparedViews(widgetSize, context);
    }


    /**
     * Prepares widget buttons by the type of layout.
     *
     * @param widgetSize Kind of widget size.     *
     * @param context
     * @return prepared Remote Views.
     */
    private static RemoteViews preparedViews(Constants.WidgetSize widgetSize, Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), Tools.getWidgetLayout(widgetSize));
        switch (widgetSize) {
            case ONExONE:
                preparePendingIntent(views, context, 1);
                break;
            case ONExTWO:
                preparePendingIntent(views, context, 1);
                preparePendingIntent(views, context, 2);
                break;
            case TWOxONE:
                preparePendingIntent(views, context, 1);
                preparePendingIntent(views, context, 3);
                break;
            case TWOxTWO:
                for (int i = 1; i <= 4; i++)
                    preparePendingIntent(views, context, i);
                break;
            case THREExONE:
                for (int i = 1; i <= 5; i += 2)
                    preparePendingIntent(views, context, i);
                break;
            default:
                for (int i = 1; i <= 6; i++)
                    preparePendingIntent(views, context, i);
                break;
        }
        return views;
    }
}
