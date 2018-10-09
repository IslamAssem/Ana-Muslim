package com.eltendawy.anamuslim.Base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.eltendawy.anamuslim.Adapter.RadiosRecyclerAdapter;
import com.eltendawy.anamuslim.Api.APIManager;
import com.eltendawy.anamuslim.Api.Model.RadiosItem;
import com.eltendawy.anamuslim.Api.Model.RadiosResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 9/13/2018.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class BaseFragment extends Fragment {

    protected BaseActivity activity;
    private int fragmentContainer;
    public static final String RADIOS_FILE= "anaMuslim-";
    public static final String LANGUAGES_FILE= "lang";
    public static final String RadiosKey= "radio";
    public static final String LanguagesKey= "lang";
    private static ArrayList<onFavouriteRadiosChangeListener> onFavouriteRadiosChangeListeners;
    public final int RADIO_REMOVED=-1;
    public final int RADIO_ADDED=1;


    public BaseFragment() {

        fragmentContainer = -1;
        onFavouriteRadiosChangeListeners=new ArrayList<>(2);
    }

    public void addOnFavouriteRadiosChangeListener(BaseFragment.onFavouriteRadiosChangeListener onFavouriteRadiosChangeListener) {
        this.onFavouriteRadiosChangeListeners.add(onFavouriteRadiosChangeListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    public void setfragmentContainer(int fragmentContainer) {
        this.fragmentContainer = fragmentContainer;
    }

    protected void showFragment(Fragment fragment) throws Exception {
        if (fragmentContainer == -1) throw new Exception("set main fragment container first");
        getChildFragmentManager().beginTransaction().replace(fragmentContainer, fragment).commit();
    }


    protected void getRadioChannels(final RadiosRecyclerAdapter adapter, boolean showFavOnly) {

        if (showFavOnly)
        {
            adapter.setRadios(getRadios(activity.getLanguage()));
        } else
        {
            APIManager.getAPIS().getRadios(activity.getLanguage()).enqueue(new Callback<RadiosResponse>() {
                @Override
                public void onResponse(Call<RadiosResponse> call, Response<RadiosResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        try
                        {
                            ArrayList<RadiosItem> radios=new ArrayList<>(response.body().getRadios());
                            ArrayList<RadiosItem> favouriteRadios=getRadios(activity.getLanguage());
                            int index;
                            for(int i=favouriteRadios.size()-1;i>-1;i--)
                            {
                                index=radios.indexOf(favouriteRadios.get(i));
                                if(index==-1)continue;;
                                radios.get(index).setFavourite(true);
                            }
                            adapter.setRadios(radios);
                        } catch (Exception ignored) {
                            Log.e("",ignored.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RadiosResponse> call, Throwable t) {

                }
            });
        }
    }

    private void saveRadios(ArrayList<RadiosItem> radios,String language) {
        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor
                =activity.getSharedPreferences(RADIOS_FILE.concat(language),activity.MODE_PRIVATE).edit();
        String gspn= new Gson().toJson(radios);
        editor.putString(RadiosKey,gspn);
        editor.apply();
    }

    public void  saveRadio(RadiosItem radio,String language) {
        try
        {
            ArrayList<RadiosItem>radios=getRadios(language);
            radios.add(radio);
            saveRadios(radios,activity.getLanguage());
            notifyListeners(radio,RADIO_ADDED );
        }
        catch (Exception ignore)
        {
        }
    }

    public void  removeRadio(RadiosItem radio,String language) {
        try
        {
            ArrayList<RadiosItem>radios=getRadios(language);
            radios.remove(radio);
            saveRadios(radios,activity.getLanguage());
            notifyListeners(radio,RADIO_REMOVED );
        }
        catch (Exception ignore)
        {
        }
    }
    public ArrayList<RadiosItem> getRadios(String language){
        try {
            SharedPreferences sharedPreferences =
                    activity.getSharedPreferences(RADIOS_FILE.concat(language), activity.MODE_PRIVATE);
            String dataGson = sharedPreferences.getString(RadiosKey, null);
            if(new Gson().fromJson(dataGson, new TypeToken<ArrayList<RadiosItem>>(){}.getType())!=null)
            return  new Gson().fromJson(dataGson, new TypeToken<ArrayList<RadiosItem>>(){}.getType());
        }catch(Exception ignored)
        {

        }
        return  new ArrayList<RadiosItem>();
    }

    public void notifyListeners(RadiosItem radio,int State)
    {
        for (onFavouriteRadiosChangeListener listener:onFavouriteRadiosChangeListeners) {
            listener.onChanege(radio, State);
        }
    }
    public interface onFavouriteRadiosChangeListener
    {
        void onChanege(RadiosItem radio,int State);
    }

    private void addLanguage(String language) {
        //save to value with the key in SharedPreferences
        ArrayList<String>langs=getLanguages();
        if(langs==null)langs=new ArrayList<>();
        langs.add(language);
        SharedPreferences.Editor editor
                =activity.getSharedPreferences(LANGUAGES_FILE,activity.MODE_PRIVATE).edit();
        String gson= new Gson().toJson(langs);
        editor.putString(LanguagesKey,gson);
        editor.apply();
    }

    private ArrayList<String> getLanguages() {
        try {
        SharedPreferences sharedPreferences =
                activity.getSharedPreferences(LANGUAGES_FILE, activity.MODE_PRIVATE);
        String dataGson = sharedPreferences.getString(LanguagesKey, null);
        if(new Gson().fromJson(dataGson, new TypeToken<ArrayList<String>>(){}.getType())!=null)
            return  new Gson().fromJson(dataGson, new TypeToken<ArrayList<String>>(){}.getType());
    }catch(Exception ignored)
    {

    }
        return  new ArrayList<String>();
    }

    private void printLog(String s) {
// display a message in Log File
        Log.d("LifeCycle:",this.getClass().getName().concat(" : ") .concat(s));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        printLog("onActivityCreated Called");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        printLog("onViewCreated Called");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        printLog("onDestroy Called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        printLog("onDestroyView Called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        printLog("onDetach Called");
    }

    @Override
    public void onPause() {
        super.onPause();
        printLog("onPause Called");
    }

    @Override
    public void onResume() {
        super.onResume();
        printLog("onResume Called");
    }

    @Override
    public void onStart() {
        super.onStart();
        printLog("onStart Called");
    }

    @Override
    public void onStop() {
        super.onStop();
        printLog("onStop Called");
    }
}
