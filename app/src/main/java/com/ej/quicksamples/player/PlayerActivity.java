package com.ej.quicksamples.player;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.ej.quicksamples.R;
import com.ej.quicksamples.explorer.ExplorerActivity;
import com.ej.quicksamples.utils.PlayerOptons;
import com.ej.quicksamples.utils.PlayerPack;
import com.ej.quicksamples.utils.TimeStamp;
import com.ej.quicksamples.soundplayer.InternMediaPlayer;
import com.ej.quicksamples.utils.Constants;
import com.ej.quicksamples.utils.CustomToast;
import com.ej.quicksamples.utils.RangeSeekBar;
import com.ej.quicksamples.utils.Tools;


/**
 * Activity of the sound player.
 *
 * @author Emil Jarosiewicz
 */
public class PlayerActivity extends Activity implements PlayerListener {

    @InjectView(R.id.lengthText)
    TextView lengthText;

    @InjectView(R.id.totalLengthText)
    TextView totalLengthText;

    @InjectView(R.id.playBar)
    ProgressBar playBar;

    @InjectView(R.id.toHours)
    EditText toHours;

    @InjectView(R.id.toMinutes)
    EditText toMinutes;

    @InjectView(R.id.toSeconds)
    EditText toSeconds;

    @InjectView(R.id.toMilis)
    EditText toMilis;

    @InjectView(R.id.fromHours)
    EditText fromHours;

    @InjectView(R.id.fromMinutes)
    EditText fromMinutes;

    @InjectView(R.id.fromSeconds)
    EditText fromSeconds;

    @InjectView(R.id.fromMilis)
    EditText fromMilis;

    @InjectView(R.id.fileName)
    EditText fileName;

    @InjectView(R.id.buttonText)
    TextView buttonText;

    private PlayerPresenter playerPresenter;
    private PlayerOptons playerOptons;
    private String path;
    private TimeStamp timeStamp;
    private RangeSeekBar<Integer> seekBar;
    private OnFocusTimeChangeListener minuteSecondFrom = new OnFocusTimeChangeListener(true);
    private OnFocusTimeChangeListener minuteSecondTo = new OnFocusTimeChangeListener(true);
    private OnFocusTimeChangeListener restFrom = new OnFocusTimeChangeListener(false);
    private OnFocusTimeChangeListener restTo = new OnFocusTimeChangeListener(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.in_transition_play, R.anim.out_transition_play);
        setContentView(R.layout.activity_player);
        playerPresenter = new PlayerPresenterImpl(this);
        ButterKnife.inject(this);
        timeStamp = new TimeStamp(0);
        PlayerPack playerPack = new PlayerPack();
        path = getIntent().getStringExtra(Constants.FILE_PATH);
        if (path != null)
            playerPack.setFilepath(path);
        else {
            playerPack.setFilepath("");
            playerPack.setButtonNumber(getIntent().getIntExtra(Constants.BUTTON_NUMBER, -1));
        }
        playerPresenter.onInit(getApplicationContext(), playerPack);
        buttonText.setText(getResources().getString(R.string.buttonNumber) + " " +
                getIntent().getIntExtra(Constants.BUTTON_NUMBER, 1));
    }

    @OnClick(R.id.playButton)
    public void playClicked() {
        playerPresenter.onPlay(playerOptons);
    }


    @OnClick(R.id.stopButton)
    public void stopClicked() {
        playerPresenter.onStop();
    }


    @OnClick(R.id.pauseButton)
    public void pauseClicked() {
        playerPresenter.onPause();
    }


    @OnClick(R.id.saveButton)
    public void saveButtonClick() {
        int buttonNumber = getIntent().getIntExtra(Constants.BUTTON_NUMBER, 1);

        playerPresenter.onSaveButtonClick(this, fileName.getText().toString(),
                path, buttonNumber, playerOptons);
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick() {
        InternMediaPlayer.getInstancePlayer().stop();
        PreferenceManager.getDefaultSharedPreferences(this).
                edit().
                putBoolean(Constants.SHOULD_RESTORE, false).
                commit();
        finish();
    }

    @OnClick(R.id.takeAnotherButton)
    public void takeAnotherButtonClick() {
        InternMediaPlayer.getInstancePlayer().stop();
        PreferenceManager.getDefaultSharedPreferences(this).
                edit().
                putBoolean(Constants.SHOULD_RESTORE, false).
                commit();
        int buttonNumber = getIntent().getIntExtra(Constants.BUTTON_NUMBER, 1);
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra(Constants.BUTTON_NUMBER, buttonNumber);
        startActivity(intent);
        //finish();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(Constants.SHOULD_RESTORE, false)) {
            timeStamp.setStamp(playerOptons.getStartPosition());
            setTimeFromFields();
            timeStamp.setStamp(playerOptons.getEndPosition());
            setTimeToFields();
            InternMediaPlayer.getInstanceWidget().stop();
            InternMediaPlayer.getInstancePlayer().setReInit(true);
        }
    }

    /**
     * Layout operations after initialization - adding seek bar, setting time into range fields, and
     * name of current button.
     *
     * @param name      name of the button
     * @param path      patch of the sound file
     * @param duration  duration of the sound file
     * @param startTime
     * @param endTime
     */
    @Override
    public void onInitEnd(String name, String path, int duration, int startTime, int endTime) {
        lengthText.setText(Tools.computeLength(0));
        totalLengthText.setText(Tools.computeLength(duration));
        playBar.setMax(duration);
        playerOptons = new PlayerOptons(0, duration);
        seekBar = new RangeSeekBar<Integer>(0, duration, getApplicationContext());
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                playerOptons.setStartPosition(minValue);
                playerOptons.setEndPosition(maxValue);
                int difMax = maxValue % 10;
                int difMin = minValue % 10;
                ((RangeSeekBar<Integer>) bar).setSelectedMaxValue(maxValue - difMax);
                ((RangeSeekBar<Integer>) bar).setSelectedMinValue(minValue - difMin);
                timeStamp.setStamp(minValue);
                setTimeFromFields();
                timeStamp.setStamp(maxValue);
                setTimeToFields();
            }

        });
        ViewGroup layout = (ViewGroup) findViewById(R.id.barLayout);
        layout.addView(seekBar, 1);
        fromHours.setOnFocusChangeListener(restFrom);
        fromMilis.setOnFocusChangeListener(restFrom);
        fromMinutes.setOnFocusChangeListener(minuteSecondFrom);
        fromSeconds.setOnFocusChangeListener(minuteSecondFrom);
        toHours.setOnFocusChangeListener(restTo);
        toMilis.setOnFocusChangeListener(restTo);
        toMinutes.setOnFocusChangeListener(minuteSecondTo);
        toSeconds.setOnFocusChangeListener(minuteSecondTo);
        setLabels(new TimeStamp(startTime), new TimeStamp(endTime), true);
        if (this.path == null || this.path.equals(""))
            this.path = path;
        fileName.setText(Tools.properName(name));
    }


    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this).
                edit().
                putBoolean(Constants.SHOULD_RESTORE, false).
                commit();
        super.onDestroy();
    }

    /**
     * Shows position of the playing sound
     *
     * @param time current sound time in milis
     */
    @Override
    public void onReadPosition(int time) {
        lengthText.setText(Tools.computeLength(time));
        playBar.setProgress(time);
    }

    /**
     * Shows error and corrects timestaps, when setted timestamps in labels are inproper.
     *
     * @param errorCode     code of error.
     * @param fromTimeStamp corrected begin timestamp
     * @param toTimeStamp   corrected end timestamp
     */
    @Override
    public void onTimeError(Constants.TimeError errorCode, TimeStamp fromTimeStamp, TimeStamp toTimeStamp) {
        switch (errorCode) {
            case DURATION_ERROR:
                CustomToast.show(this, R.string.durationError);
                break;
            case MAX_LESS_THAN_MIN:
                CustomToast.show(this, R.string.maxError);
                break;
            case MIN_GREATER_THAN_MAX:
                CustomToast.show(this, R.string.minError);
                break;
        }
        setLabels(fromTimeStamp, toTimeStamp, true);
    }

    /**
     * Sets labels, when timestamps are proper.
     */
    @Override
    public void onTimeSuccess() {
        setLabels(getFromTimeStamp(), getToTimeStamp(), false);
    }


    /**
     * Finish activity after save success.
     */
    @Override
    public void onSaveSuccess() {
        finish();
    }

      /**
     * Shows error, and finishes activity, when file is missing.
     */
    @Override
    public void onFileReadError() {
        CustomToast.show(this, R.string.fileMissingError);
        int buttonNumber = getIntent().getIntExtra(Constants.BUTTON_NUMBER, 1);
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra(Constants.BUTTON_NUMBER, buttonNumber);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        InternMediaPlayer.getInstancePlayer().stop();
        super.onBackPressed();
    }

    private void setLabels(TimeStamp fromTimestamp, TimeStamp toTimestamp, boolean labelInclude) {
        if (labelInclude) {
            timeStamp = toTimestamp;
            setTimeFromFields();
        }
        seekBar.setSelectedMinValue(fromTimestamp.getStamp());
        if (labelInclude) {
            timeStamp = toTimestamp;
            setTimeToFields();
        }
        seekBar.setSelectedMaxValue(toTimestamp.getStamp());
        playerOptons.setStartPosition(fromTimestamp.getStamp());
        playerOptons.setEndPosition(toTimestamp.getStamp());
    }

    private void setTimeToFields() {
        toHours.setText(Integer.toString(timeStamp.getHours()));
        toMinutes.setText(Integer.toString(timeStamp.getMinutes()));
        toSeconds.setText(Integer.toString(timeStamp.getSeconds()));
        toMilis.setText(Integer.toString(timeStamp.getMilis() / 10));
    }

    private void setTimeFromFields() {
        fromHours.setText(Integer.toString(timeStamp.getHours()));
        fromMinutes.setText(Integer.toString(timeStamp.getMinutes()));
        fromSeconds.setText(Integer.toString(timeStamp.getSeconds()));
        fromMilis.setText(Integer.toString(timeStamp.getMilis() / 10));
    }

    private TimeStamp getFromTimeStamp() {
        TimeStamp timeStamp = new TimeStamp(0);
        timeStamp.setHours(Integer.parseInt(fromHours.getText().toString()));
        timeStamp.setMinutes(Integer.parseInt(fromMinutes.getText().toString()));
        timeStamp.setSeconds(Integer.parseInt(fromSeconds.getText().toString()));
        timeStamp.setMilis(Integer.parseInt(fromMilis.getText().toString()) * 10);
        return timeStamp;
    }

    private TimeStamp getToTimeStamp() {
        TimeStamp timeStamp = new TimeStamp(0);
        timeStamp.setHours(Integer.parseInt(toHours.getText().toString()));
        timeStamp.setMinutes(Integer.parseInt(toMinutes.getText().toString()));
        timeStamp.setSeconds(Integer.parseInt(toSeconds.getText().toString()));
        timeStamp.setMilis(Integer.parseInt(toMilis.getText().toString()) * 10);
        return timeStamp;
    }

    private class OnFocusTimeChangeListener implements View.OnFocusChangeListener {


        private boolean isMin;

        private OnFocusTimeChangeListener(boolean isMin) {
            this.isMin = isMin;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String txt = ((EditText) v).getText().toString();
                ((EditText) v).setText(playerPresenter.textCheck(txt, isMin));
                playerPresenter.timeCompare(getFromTimeStamp(), getToTimeStamp());
            }
        }
    }


}
