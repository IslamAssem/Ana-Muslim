package com.eltendawy.anamuslim.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eltendawy.anamuslim.R;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
       protected Context activity;
    public static ArrayList<Character> numbersChars =null;

    public BaseActivity() {
        activity = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] numbers=getResources().getStringArray(R.array.numbers);
        numbersChars=new ArrayList<>(10);
        for (String number : numbers) numbersChars.add(number.charAt(0));
    }

    public static String getARNumber(int EnNumber) {

        String text=String.valueOf(EnNumber);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
                builder.append(numbersChars.get((int) (text.charAt(i)) - 48));
        return builder.toString();
    }
}
