package com.ej.quicksamples.soundplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.ej.quicksamples.player.PlayerListener;
import com.ej.quicksamples.utils.Constants;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Sound player of the project. It contains two instances of Media Player for Player Activity and widget.
 *
 * @author Emil Jarosiewicz
 */
public class InternMediaPlayer implements Player {

    private MediaPlayer mediaPlayer;
    private String receiverId;
    private int startPosition;
    private int endPosition;
    private int pausePosition = 0;
    private String filepath;
    private Subscriber<Long> trackSubscriber;
    private PlayerListener playerListener;
    private boolean reInit=false;
    private String currentButton="";

    public String getCurrentButton() {
        return currentButton;
    }

    public void setCurrentButton(String currentButton) {
        this.currentButton = currentButton;
    }

    private static InternMediaPlayer ourInstancePlayer = new InternMediaPlayer(true);

    public static InternMediaPlayer getInstancePlayer() {
        return ourInstancePlayer;
    }

    private static InternMediaPlayer ourInstanceWidget = new InternMediaPlayer(true);

    public static InternMediaPlayer getInstanceWidget() {
        return ourInstanceWidget;
    }

    public boolean isReInit() {
        return reInit;
    }

    public void setReInit(boolean reInit) {
        this.reInit = reInit;
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public PlayerListener getPlayerListener() {
        return playerListener;
    }

    public void setPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    private InternMediaPlayer(boolean isPlayer) {
        if (isPlayer)
                this.receiverId=Constants.RECEIVER_PLAYER;
        else this.receiverId=Constants.RECEIVER_WIDGET;
    }

    @Override
    public void play() {


/*        if (reInit){
            reInit=false;
            InternMediaPlayer.this.initPlayer();
        }*/
        if (pausePosition == 0) {
            initPlayer();
            mediaPlayer.seekTo(this.getStartPosition());
            mediaPlayer.start();
            initObservable();
        } else {
            mediaPlayer.seekTo(pausePosition);
            mediaPlayer.start();
            pausePosition=0;
        }
    }

    @Override
    public boolean pause() {
        if (pausePosition == 0) {
            mediaPlayer.pause();
            pausePosition = mediaPlayer.getCurrentPosition();
            return true;
        } else{
            mediaPlayer.seekTo(pausePosition);
            mediaPlayer.start();
            pausePosition=0;
            return false;
        }
    }

    @Override
    public void pauseOnly() {
        if (mediaPlayer!=null && mediaPlayer.isPlaying() &&pausePosition == 0) {
            mediaPlayer.pause();
            pausePosition = mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    public void stop() {
        pausePosition=0;
        if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
        mediaPlayer.stop();
        initPlayer();
        }
    }

    public void createPlayer(Context context){
        File file=new File(filepath);
        mediaPlayer=MediaPlayer.create(context,Uri.fromFile(file));
    }

    public void initPlayer(){
        try {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(filepath);
        mediaPlayer.prepare();
        mediaPlayer.setVolume(1f,1f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getReceiverId() {
        return receiverId;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public int getPausePosition() {
        return pausePosition;
    }

    public void setPausePosition(int pausePosition) {
        this.pausePosition = pausePosition;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Subscriber<Long> getTrackSubscriber() {
        return trackSubscriber;
    }

    public void setTrackSubscriber(Subscriber<Long> trackSubscriber) {
        this.trackSubscriber = trackSubscriber;
    }


    private void initObservable() {
        Log.d("OBSERVABLE","INIT");
        Observable
                .interval(10, TimeUnit.MILLISECONDS)
                .takeWhile(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        Log.d("OBSERVABLE", aLong.toString());
                        if (mediaPlayer.getCurrentPosition() >= endPosition)
                            stop();
                        return mediaPlayer.isPlaying() || pausePosition>0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(selectSubscriber());

    }

    private Subscriber<Long> selectSubscriber(){
        if (receiverId.equals(Constants.RECEIVER_WIDGET))
            return widgetSubscriber();
        else return playerSubscriber();
    }


    private Subscriber<Long> widgetSubscriber(){
       return new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d("OBSERVABLE", "COMPLETE");


            }

            @Override
            public void onError(Throwable e) {
                Log.d("OBSERVABLE ERROR", e.toString());

            }

            @Override
            public void onNext(Long aLong) {
                Log.d("OBSERVABLE", "NEXT");
            }
        };
    }

    private Subscriber<Long> playerSubscriber(){
        return new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                pausePosition=0;
                if (playerListener!=null)
                    playerListener.onReadPosition(pausePosition);

            }

            @Override
            public void onError(Throwable e) {
                if (playerListener!=null)
                    playerListener.onReadPosition(pausePosition);
            }

            @Override
            public void onNext(Long aLong) {
                if (aLong % 20 == 0 && playerListener!=null) {
                    int time = mediaPlayer.getCurrentPosition();
                    playerListener.onReadPosition(time);
                }
            }
        };
    }



}
