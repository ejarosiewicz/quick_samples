package com.ej.quicksamples.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

import com.ej.quicksamples.R;
import com.ej.quicksamples.explorer.ExplorerActivity;
import com.ej.quicksamples.player.PlayerActivity;
import com.ej.quicksamples.utils.Constants;
import com.ej.quicksamples.utils.Tools;

/**
 * Main activity, that allows button selection for configuration
 *
 * @author Emil Jarosiewicz
 */

public class MainActivity extends Activity implements MainListener, View.OnClickListener {


    RelativeLayout contentLayout;
    Button[] button;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLayoutReload();
        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);
        mainPresenter = new MainPresenterImpl(this);
        ButterKnife.inject(this);
        mainPresenter.onInit(this, contentLayout);
    }

    @Override
    public void onClick(View v) {
        mainPresenter.onClickButton(PreferenceManager.getDefaultSharedPreferences(this),
                Tools.getButtonNumber(v.getId()));
    }


    /**
     * Setting names of buttons.
     *
     * @param i    number of button
     * @param name new name of button
     */
    @Override
    public void onButtonLoad(int i, String name) {
        if (!name.equals("")) {
            ((Button) findViewById(Tools.getButtonId(i))).setText(name);
        }
    }


    /**
     * Starting the Player Activity in case when current button has got saved configuration.
     *
     * @param i number of buuton
     */
    @Override
    public void onFileExist(int i) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(Constants.BUTTON_NUMBER, i);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    /**
     * Starting the Explorer Activity in case when current button hasn't got saved configuration.
     *
     * @param i number of buuton
     */
    @Override
    public void onFileNotExist(int i) {
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra(Constants.BUTTON_NUMBER, i);
        startActivity(intent);
    }


    /**
     * Loads button layout, and sets on click listeners of them.
     */
    @Override
    public void onLayoutReload() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout buttonLayout = (RelativeLayout) findViewById(R.id.buttonLayout);
        buttonLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_buttons, buttonLayout);
        button = new Button[Constants.NUMBER_OF_BUTTONS];
        for (int i = 0; i < Constants.NUMBER_OF_BUTTONS; i++)
            button[i] = (Button) findViewById(Tools.getButtonId(i + 1));
        for (Button iterateButton : button)
            iterateButton.setOnClickListener(this);
    }

    /**
     * Sets the transition between activities.
     */
    @Override
    public void onSettingPendingTransition() {
        overridePendingTransition(R.anim.in_transition_main, R.anim.out_transition_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }


}
