package com.eltendawy.anamuslim.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.eltendawy.anamuslim.R;

import java.util.ArrayList;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity activity;
    protected Context context_application;
    private int fragmentContainer;
    protected Fragment quran,ahadith,radio;
    private String language;


    public BaseActivity() {
        activity = this;
        fragmentContainer=-1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context_application=getApplicationContext();
        language=Locale.getDefault().getLanguage();


    }

    public String getLanguage() {
        return language;
    }

    public void setfragmentContainer(int fragmentContainer) {
        this.fragmentContainer = fragmentContainer;
    }

    protected void showFragment(Fragment fragment) throws Exception
    {
        if(fragmentContainer==-1)throw new Exception("set main fragment container first");
        getSupportFragmentManager().beginTransaction().replace(fragmentContainer,fragment).commit();

    }
}
