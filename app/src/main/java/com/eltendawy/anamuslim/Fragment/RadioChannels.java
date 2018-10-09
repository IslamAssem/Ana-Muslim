package com.eltendawy.anamuslim.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.eltendawy.anamuslim.Adapter.RadiosRecyclerAdapter;
import com.eltendawy.anamuslim.Api.Model.RadiosItem;
import com.eltendawy.anamuslim.Base.BaseFragment;
import com.eltendawy.anamuslim.Player;
import com.eltendawy.anamuslim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioChannels extends BaseFragment implements Player.onPlayerStatusChanged {


    View view;
    RecyclerView recyclerView;
    RadiosRecyclerAdapter adapter;
    LinearLayoutManager manager;
    boolean showFavOnly;
    RadioPlayer player;

    public RadioChannels() {
        // Required empty public constructor
        showFavOnly=false;
    }

    public boolean isShowFavOnly() {
        return showFavOnly;
    }

    public RadioChannels setShowFavOnly(boolean showFavOnly) {
        this.showFavOnly = showFavOnly;
        return this;
    }

    public RadioChannels setPlayer(RadioPlayer player) {
        this.player = player;
        return this;
    }

    public RadiosRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_radio_channels, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        recyclerView =view.findViewById(R.id.recycler_channels);
        adapter=new RadiosRecyclerAdapter(recyclerView,R.layout.recycler_item_radio_channel);
        adapter.setOnChannelClickListner(new RadiosRecyclerAdapter.onClickListner() {
            @Override
            public void onClick(int position, View view, RadiosItem radio) {
                //TODO play selected channel
            }
        });
        adapter.setOnFavouriteClickListner(new RadiosRecyclerAdapter.onClickListner() {
            @Override
            public void onClick(int position, View view, RadiosItem radio) {
                if(radio.isFavourite())
                {
                    radio.setFavourite(false);
                    ((ImageButton)view).setImageResource(R.drawable.favourite_off);
                    removeRadio(radio,activity.getLanguage());
                }
                else
                {
                    radio.setFavourite(true);
                    ((ImageButton)view).setImageResource(R.drawable.favourite_on);
                    saveRadio(radio,activity.getLanguage());
                }
            }
        });
        adapter.setOnChannelClickListner(new RadiosRecyclerAdapter.onClickListner() {
            @Override
            public void onClick(int position, View view, RadiosItem radio) {
                if(player!=null)
                    player.playChannel(radio);
            }
        });
        manager=new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        getRadioChannels(adapter,showFavOnly);
        player.addonPlayerStatusChanged(this);
    }


    @NonNull
    @Override
    public String toString() {
        return showFavOnly?"Favourite":"all Channels";
    }

    @Override
    public void onChange(boolean IsPlaying) {
        if(IsPlaying){
            adapter.setPlayingIndicator(((RadiosRecyclerAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition
                    (adapter.getCurrent_playing())), View.VISIBLE);
        }
        else {
            adapter.setPlayingIndicator(((RadiosRecyclerAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition
                    (adapter.getCurrent_playing())), View.GONE);
        }
    }
}
