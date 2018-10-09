package com.eltendawy.anamuslim.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eltendawy.anamuslim.Base.BaseFragment;
import com.eltendawy.anamuslim.activity.AyatActivity;
import com.eltendawy.anamuslim.Adapter.Suar_List_Adapter;
import com.eltendawy.anamuslim.Model.Sura;
import com.eltendawy.anamuslim.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quran extends BaseFragment {

    RecyclerView suar_List;
    View view;
    public Quran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_quran, container, false);
        initViews();
        return view;
    }
    public void initViews(){
        suar_List = view.findViewById(R.id.suar_list);
        Suar_List_Adapter adapter = new Suar_List_Adapter(getSuarList(activity));
        adapter.setOnItemClickListener(new Suar_List_Adapter.onItemClick() {
            @Override
            public void onClick(int position, View view) {

                try {

                    Intent intent = new Intent(activity, AyatActivity.class);
                    intent.putExtra("sura", position + 1);
                    startActivity(intent);
                } catch (Exception ignored) {
                }
            }
        });

        suar_List.setLayoutManager(new LinearLayoutManager(activity));
        suar_List.setAdapter(adapter);
    }
    public ArrayList<Sura> getSuarList(Context context)
    {
        ArrayList<Sura> suarList=new ArrayList<>(114);
        String[] suar=context.getResources().getStringArray(R.array.suar_names);
        for(int i=0;i<suar.length;i++)
        {
            suarList.add(new Sura(suar[i],i+1));
        }
        return suarList;
    }

}
