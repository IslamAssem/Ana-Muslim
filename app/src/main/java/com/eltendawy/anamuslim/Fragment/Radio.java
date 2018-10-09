package com.eltendawy.anamuslim.Fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eltendawy.anamuslim.Adapter.SimpleFragmentPagerAdapter;
import com.eltendawy.anamuslim.Api.Model.RadiosItem;
import com.eltendawy.anamuslim.Base.BaseFragment;
import com.eltendawy.anamuslim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Radio extends BaseFragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    SimpleFragmentPagerAdapter adapter;
    RadioPlayer radioPlayer;
    RadioChannels AllChannels;
    RadioChannels favouriteChannels;

    public Radio() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_radio, container, false);
        initViews();
        setfragmentContainer(R.id.fragment_container);
        return view;
    }

    public void initViews()
    {
        radioPlayer=new RadioPlayer();
        AllChannels=new RadioChannels().setShowFavOnly(false).setPlayer(radioPlayer);
        favouriteChannels=new RadioChannels().setShowFavOnly(true).setPlayer(radioPlayer);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager=view.findViewById(R.id.viewpager);
        adapter=new SimpleFragmentPagerAdapter(getChildFragmentManager());

        AllChannels.addOnFavouriteRadiosChangeListener(new onFavouriteRadiosChangeListener() {
            @Override
            public void onChanege(RadiosItem radio, int State) {
                getRadioChannels(AllChannels.getAdapter(),AllChannels.showFavOnly);
            }
        });
        favouriteChannels.addOnFavouriteRadiosChangeListener(new onFavouriteRadiosChangeListener() {
            @Override
            public void onChanege(RadiosItem radio, int State) {
                getRadioChannels(favouriteChannels.getAdapter(),favouriteChannels.showFavOnly);
            }
        });
        radioPlayer.addOnFavouriteRadiosChangeListener(new onFavouriteRadiosChangeListener() {
            @Override
            public void onChanege(RadiosItem radio, int State) {
                getRadioChannels(radioPlayer.getAdapter(),false);
            }
        });
        adapter.add(radioPlayer,radioPlayer.toString(),R.drawable.radio);
        adapter.add(AllChannels,AllChannels.toString(),R.drawable.playlist_play);
        adapter.add(favouriteChannels,favouriteChannels.toString(),R.drawable.favourite_on);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
