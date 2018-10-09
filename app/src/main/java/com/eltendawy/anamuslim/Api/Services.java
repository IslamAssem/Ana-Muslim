package com.eltendawy.anamuslim.Api;

import com.eltendawy.anamuslim.Api.Model.RadiosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Services {

    @GET("radio/radio_{language}.json")
    Call<RadiosResponse> getRadios(@Path("language")String  language);
}
