package com.eltendawy.anamuslim.Fragment;


import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eltendawy.anamuslim.Adapter.RadiosRecyclerAdapter;
import com.eltendawy.anamuslim.Api.Model.RadiosItem;
import com.eltendawy.anamuslim.Base.BaseFragment;
import com.eltendawy.anamuslim.Player;
import com.eltendawy.anamuslim.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioPlayer extends BaseFragment implements Player.onPlayerStatusChanged {

    View view;
    RecyclerView recyclerView;
    RadiosRecyclerAdapter adapter;
    LinearLayoutManager manager;
    ImageButton play, next, previous;
    ArrayList<RadiosItem> radios;
    public static Player mPlayer;
    TextView chanelTitle;
    boolean  showFavOnly;
    ArrayList<Player.onPlayerStatusChanged> onPlayerStatusChangedListeners;


    public RadioPlayer() {
        // Required empty public constructor
        radios = new ArrayList<>();
        showFavOnly = false;
    }

    public void addonPlayerStatusChanged(Player.onPlayerStatusChanged onPlayerStatusChanged)
    {
        if(onPlayerStatusChangedListeners==null)onPlayerStatusChangedListeners=new ArrayList<>(2);
        onPlayerStatusChangedListeners.add(onPlayerStatusChanged);
    }

    public RadiosRecyclerAdapter getAdapter() {
        return adapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_radio_player, container, false);
        initViews();
        return view;
    }


    public void initViews() {
        recyclerView = view.findViewById(R.id.recycler_channels);
        play = view.findViewById(R.id.play);
        next = view.findViewById(R.id.next);
        chanelTitle=view.findViewById(R.id.channel_title);
        chanelTitle.setSelected(true);
        previous = view.findViewById(R.id.previous);
        adapter = new RadiosRecyclerAdapter(recyclerView,R.layout.recycler_item_radio_channel_player);
        adapter.setOnFavouriteClickListner(new RadiosRecyclerAdapter.onClickListner() {
            @Override
            public void onClick(int position, View view, RadiosItem radio) {
                if (radio.isFavourite()) {
                    radio.setFavourite(false);
                    ((ImageButton) view).setImageResource(R.drawable.favourite_off);
                    removeRadio(radio,activity.getLanguage());
                } else {
                    radio.setFavourite(true);
                    ((ImageButton) view).setImageResource(R.drawable.favourite_on);
                    saveRadio(radio,activity.getLanguage());
                }

            }

        });
        adapter.setOnChannelClickListner(new RadiosRecyclerAdapter.onClickListner() {
            @Override
            public void onClick(int position, View view, RadiosItem radio) {
                playChannel(radio);
            }
        });
        manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        mPlayer=Player.CreatePlayer(activity.getApplicationContext());
        mPlayer.setOnPlayerStatusChanged(this);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    play.setImageResource(R.drawable.play);
                    adapter.setPlayingIndicator(((RadiosRecyclerAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition
                            (adapter.getCurrent_playing())), View.GONE);
                }else {
                    mPlayer.resume(adapter.getRadio(0).getURL());
                    play.setImageResource(R.drawable.stop);
                    adapter.setPlayingIndicator(((RadiosRecyclerAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition
                            (adapter.getCurrent_playing())), View.VISIBLE);
                }
                if(onPlayerStatusChangedListeners!=null)
                    for (Player.onPlayerStatusChanged listener:onPlayerStatusChangedListeners) {
                        if(listener!=null)
                            listener.onChange(mPlayer.isPlaying());
                    }
            }
        });
        getRadioChannels(adapter, showFavOnly);
    }



    @Override
    public String toString() {
        return "player";
    }

    @Override
    public void onChange(boolean status) {
        if(status){
            play.setImageResource(R.drawable.stop);
            Log.e("log","status==Player.PLAYING");
            adapter.setPlayingIndicator(((RadiosRecyclerAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition
                    (adapter.getCurrent_playing())), View.VISIBLE);
        }
        else {
            play.setImageResource(R.drawable.play);
            Log.e("log","status==Player.STOPPED");
            adapter.setPlayingIndicator(((RadiosRecyclerAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition
                    (adapter.getCurrent_playing())), View.GONE);
        }
    if(onPlayerStatusChangedListeners!=null)
        for (Player.onPlayerStatusChanged listener:onPlayerStatusChangedListeners) {
            if(listener!=null)
                listener.onChange(status);
        }
    }

    public void playChannel(RadiosItem radio)
    {
        mPlayer.playChannel(radio.getURL());
        chanelTitle.setText(padText(radio.getName(),chanelTitle));
        chanelTitle.setSelected(true);
    }
    public static CharSequence padText(CharSequence text, TextView textView) {
        TextPaint paint=textView.getPaint();
        int width=textView.getWidth();
        // First measure the width of the text itself
        Rect textbounds = new Rect();
        paint.getTextBounds(text.toString(), 0, text.length(), textbounds);
        /**
         * check to see if it does indeed need padding to reach the target width
         */
        if (textbounds.width() > width) {
            return text;
        }

        /*
         * Measure the text of the space character (there's a bug with the
         * 'getTextBounds() method of Paint that trims the white space, thus
         * making it impossible to measure the width of a space without
         * surrounding it in arbitrary characters)
         */
        String workaroundString = "a a";
        Rect spacebounds = new Rect();
        paint.getTextBounds(workaroundString, 0, workaroundString.length(), spacebounds);

        Rect abounds = new Rect();
        paint.getTextBounds(new char[] {
                'a'
        }, 0, 1, abounds);

        float spaceWidth = spacebounds.width() - (abounds.width() * 2);

        /*
         * measure the amount of spaces needed based on the target width to fill
         * (using Math.ceil to ensure the maximum whole number of spaces)
         */
        int amountOfSpacesNeeded = (int)Math.ceil((width - textbounds.width()) / spaceWidth);

        CharSequence x=amountOfSpacesNeeded > 0 ? padRight(text.toString(), text.toString().length()+1
                + amountOfSpacesNeeded) : text;
        // pad with spaces til the width is less than the text width
        return x;
    }
    /**
     * Pads a string with white space on the right of the original string
     *
     * @param s The target string
     * @param n The new target length of the string
     * @return The target string padded with whitespace on the right to its new
     *         length
     */
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

}
