package com.example.myapp.network;

import com.example.myapp.models.PredictionResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("predict")
    Call<PredictionResponse> predictHeartDisease(
            @Field("cp") int cp,
            @Field("thalach") int thalach,
            @Field("slope") int slope,
            @Field("restecg") int restecg,
            @Field("chol") float chol,
            @Field("trestbps") float trestbps,
            @Field("fbs") int fbs,
            @Field("oldpeak") float oldpeak);
}