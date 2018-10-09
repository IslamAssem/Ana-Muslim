package com.eltendawy.anamuslim.Api.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class RadiosItem{

	@SerializedName("URL")
	private String uRL;

	@SerializedName("Name")
	private String name;

	private boolean Favourite;

	public RadiosItem() {
		Favourite=false;
	}

	public void setURL(String uRL){
		this.uRL = uRL;
	}

	public String getURL(){
		return uRL;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}


    public boolean isFavourite() {
        return Favourite;
    }

    public void setFavourite(boolean favourite) {
        Favourite = favourite;
    }


	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj != null) {
			if(this.uRL.equals(((RadiosItem)obj).uRL))
				return true;
			else return false;
		}
		return false;
	}

	@Override
 	public String toString(){
		return 
			"RadiosItem{" + 
			"uRL = '" + uRL + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}