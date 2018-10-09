package com.eltendawy.anamuslim.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eltendawy.anamuslim.Adapter.Aya_List_Adapter;
import com.eltendawy.anamuslim.Model.Aya;
import com.eltendawy.anamuslim.R;
import com.eltendawy.anamuslim.helper.BaseActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AyatActivity extends BaseActivity {


    RecyclerView aya_List;
    int sura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayat);
        sura = getIntent().getIntExtra("sura", 0);
        String sura_file =String.valueOf("Quran/").concat( String.valueOf(sura)).concat(".txt");
        aya_List = findViewById(R.id.sura_content);
        ArrayList<Aya> ayat = new ArrayList<>();
        InputStream inputStream = null;
        Scanner scanner = null;
        int counter=1;
        try {
            inputStream = activity.getAssets().open(sura_file);
            scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                ayat.add(new Aya(scanner.nextLine(),counter++));
            }
        } catch (Exception ignored) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (scanner != null) {
                    scanner.close();
                }
            } catch (Exception ignored) {
            }
        }

        Aya_List_Adapter adapter= new Aya_List_Adapter(ayat, activity);
        adapter.setOnItemClickListener(new Aya_List_Adapter.onItemClick() {
            @Override
            public void onClick(int position, View view) {

                try {
                } catch (Exception ignored) {
                }
            }
        });

        aya_List.setLayoutManager(new LinearLayoutManager(activity));
        aya_List.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
