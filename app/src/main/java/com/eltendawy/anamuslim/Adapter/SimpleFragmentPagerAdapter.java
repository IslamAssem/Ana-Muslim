package com.eltendawy.anamuslim.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eltendawy.anamuslim.R;

import java.util.ArrayList;
//import com.android.internal.
/**
 * Created by Islam on 10-Oct-17.
 */

public class SimpleFragmentPagerAdapter  extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<Fragment>fragments;
    private ArrayList<String>titles;
    private ArrayList<Integer>icons;
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public  void add(Fragment fragment,String title,int IconResource)
    {
        if(fragments==null||titles==null||icons==null)
        {
            fragments=new ArrayList<>();
            titles=new ArrayList<>();
            icons=new ArrayList<>();
        }
        fragments.add(fragment);
        titles.add(title);
        icons.add(IconResource);
    }
    public  void add(Fragment fragment,String title)
    {
        if(fragments==null||titles==null||icons==null)
        {
            fragments=new ArrayList<>();
            titles=new ArrayList<>();
            icons=new ArrayList<>();
        }
        fragments.add(fragment);
        titles.add(title);
        icons.add(R.drawable.error);
    }
    public  void add(Fragment fragment,int IconResource)
    {
        if(fragments==null||titles==null||icons==null)
        {
            fragments=new ArrayList<>();
            titles=new ArrayList<>();
            icons=new ArrayList<>();
        }
        fragments.add(fragment);
        titles.add("fragment");
        icons.add(IconResource);

    }
    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
//        if (position == 2){//how to send data
//            {
//                Review r=new Review();
//                Bundle args = new Bundle();
//                args.putString("0", movieid);
//                r.setArguments(args);
//                return r;
//            }
//        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return fragments.size();
    }

    // This determines the title for each tab
    @Override
    public String getPageTitle(int position) {
        return  titles.get(position);
    }



}





