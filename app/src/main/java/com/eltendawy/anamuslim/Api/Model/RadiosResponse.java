package com.eltendawy.anamuslim.Api.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RadiosResponse {

	@SerializedName("Radios")
	private List<RadiosItem> radios;

	public void setRadios(List<RadiosItem> radios){
		this.radios = radios;
	}

	public List<RadiosItem> getRadios(){
		return radios;
	}

	@Override
 	public String toString(){
		return 
			"RadiosResponse{" +
			"radios = '" + radios + '\'' + 
			"}";
		}
}