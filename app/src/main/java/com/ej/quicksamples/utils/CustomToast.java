package com.ej.quicksamples.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ej.quicksamples.R;


/**
 * Customized toast messages.
 *
 * @author Emil Jarosiewicz
 */
public class CustomToast {

    /**
     * Shows typed message.
     *
     * @param context current context of activity
     * @param text message text
     */
    public static void show(Context context,String text){
        Toast toast=new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_appearance,null);
        ((TextView)layout.findViewById(R.id.message)).setText(text);
        toast.setView(layout);
        toast.show();

    }

    /**
     * Shows message from the resources.
     *
     * @param context current context of activity
     * @param id id of text resource
     */
    public static void show(Context context,int id){
        show(context,context.getResources().getString(id));
    }
}
