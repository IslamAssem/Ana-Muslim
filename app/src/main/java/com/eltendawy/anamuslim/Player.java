package com.eltendawy.anamuslim;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import static android.media.AudioManager.*;

public class Player implements OnAudioFocusChangeListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean IsPlaying;
    private boolean IsStopped;
    private int audioFocusState;
    private String lastPlayed;
    private static Player player;
    private onPlayerStatusChanged onPlayerStatusChanged;
    private Player(Context context)
    {
        audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        IsPlaying=false;
        IsStopped=true;
        lastPlayed="";
    }

    public static Player CreatePlayer(Context context)
    {
        if(player==null)player=new Player(context);
        return player;
    }

    public boolean isPlaying() {
        if(mediaPlayer!=null)
        return mediaPlayer.isPlaying();
        else return false;
    }

    public void setOnPlayerStatusChanged(Player.onPlayerStatusChanged onPlayerStatusChanged) {
        this.onPlayerStatusChanged = onPlayerStatusChanged;
    }

    public void playChannel(String url) {

        try {
            lastPlayed=url;
            release();
            audioFocusState=audioManager.requestAudioFocus(this,STREAM_MUSIC,AUDIOFOCUS_GAIN);
            if(audioFocusState==AUDIOFOCUS_REQUEST_FAILED)
                return;
            if(audioFocusState==AUDIOFOCUS_REQUEST_DELAYED)
                //TODO handle delayed focus //ask eng mohamed nabil if lost
                return;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);

        } catch (Exception ignored) {
        }
    }
    private void resume()
    {
        try
        {
            if(mediaPlayer!=null&&!IsPlaying&&!IsStopped)
            {
                mediaPlayer.start();
                IsPlaying=true;
            }else
            {
                playChannel(lastPlayed);
            }
        }
        catch (Exception ignored)
        {

        }
    }

    public void resume(String lastPlayed)
    {
        this.lastPlayed=lastPlayed;
        try
        {
            if(mediaPlayer!=null&&!IsPlaying&&!IsStopped)
            {
                mediaPlayer.start();
                IsPlaying=true;
            }else
            {
                playChannel(lastPlayed);
            }
        }
        catch (Exception ignored)
        {

        }
    }
    public void pause()
    {
        try
        {
            if(mediaPlayer!=null&&IsPlaying)
            {
                mediaPlayer.pause();
                IsPlaying=false;
                IsStopped=false;
            }
        }
        catch (Exception ignored)
        {

        }
    }

    private void release()
    {
        try
        {
            if(mediaPlayer!=null)
            {
                if(!IsStopped)
                    mediaPlayer.stop();
                IsStopped=true;
                IsPlaying=false;
                mediaPlayer.release();
            }
        }
        catch (Exception ignored)
        {

        }
        finally {
            mediaPlayer=null;
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        audioFocusState=focusChange;
        if(audioFocusState==AUDIOFOCUS_LOSS)
        {
            release();
            if(onPlayerStatusChanged!=null)
                onPlayerStatusChanged.onChange(false);
            Log.e("log","audioFocusState==AUDIOFOCUS_LOSS");
        }else if(audioFocusState==AUDIOFOCUS_LOSS_TRANSIENT) {
            pause();
            if(onPlayerStatusChanged!=null)
                onPlayerStatusChanged.onChange(false);
            Log.e("log","audioFocusState==AUDIOFOCUS_LOSS_TRANSIENT");
        }else if(audioFocusState==AUDIOFOCUS_GAIN){
            resume();
            if(onPlayerStatusChanged!=null)
                onPlayerStatusChanged.onChange(true);
            Log.e("log","audioFocusState==AUDIOFOCUS_GAIN");
        }

    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        IsPlaying = true;
        IsStopped=false;
        if(onPlayerStatusChanged!=null)
            onPlayerStatusChanged.onChange(true);
    }
    public interface onPlayerStatusChanged
    {
        void onChange(boolean status);
    }
}
