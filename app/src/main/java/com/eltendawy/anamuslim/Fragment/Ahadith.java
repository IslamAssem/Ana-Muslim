package com.eltendawy.anamuslim.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eltendawy.anamuslim.Adapter.Ahadith_recycler_adapter;
import com.eltendawy.anamuslim.Base.BaseFragment;
import com.eltendawy.anamuslim.Model.Hadith;
import com.eltendawy.anamuslim.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ahadith extends BaseFragment {


    View view;
    RecyclerView Ahadith_recycler;

    public Ahadith() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_ahadith, container, false);
        initviews();
        return view;
    }

    private void initviews() {
        Ahadith_recycler=view.findViewById(R.id.ahadith_list);
        LinearLayoutManager manager=new LinearLayoutManager(activity);
        final Ahadith_recycler_adapter adapter=new Ahadith_recycler_adapter(getAhadith(getContext()),activity);
        Ahadith_recycler.setLayoutManager(manager);
        adapter.setOnHadithClickListener(new Ahadith_recycler_adapter.onHadithClickListener() {
            @Override
            public void onClick(int position, Hadith hadith) {
                new HadithDialogFragment().setHadith(hadith).show(getChildFragmentManager(),hadith.getTitle());
            }
        });
        Ahadith_recycler.setAdapter(adapter);

    }
    public ArrayList<Hadith> getAhadith(Context context)
    {
        /*int count=0;*/
        InputStream inputStream = null;
        Scanner scanner = null;
        ArrayList<Hadith>Ahadith=new ArrayList<>();
        try {
            String line,title;
            StringBuilder content=new StringBuilder();
            inputStream = context.getAssets().open("Hadith/ahadith_arabic.txt");
            scanner = new Scanner(inputStream);
            while (scanner.hasNext())
            {
                title=scanner.nextLine();
                while (scanner.hasNext())
                {
                    line=scanner.nextLine();
                    if(line.contains("#"))
                    {
                        Ahadith.add(new Hadith(title,content.toString()));
                        content.delete(0,content.length());
                        break;
                    }
                    content.append(line);

                }
            }
        }
        catch (Exception ignored) {
        }
        finally
        {
            try
            {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (scanner != null) {
                    scanner.close();
                }
            }
            catch (Exception ignored) {
            }
        }
        return Ahadith;
    }

}
