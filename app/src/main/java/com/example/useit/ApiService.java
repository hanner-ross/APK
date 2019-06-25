package com.example.useit;

import com.example.useit.response.IdeasResponse;
import com.example.useit.response.PostIdeasResponse;
import com.example.useit.response.TablerosResponse;
import com.example.useit.response.UsuariosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("usuarios")
    Call<List<UsuariosResponse>> getUsuarios();

    @Headers("Content-Type: application/json")
    @GET("tableros")
    Call<List<TablerosResponse>> getTableros();

    @Headers("Content-Type: application/json")
    @GET("ideas")
    Call<List<IdeasResponse>> getIdeas();

    @FormUrlEncoded
    @POST("ideas/")
    Call<PostIdeasResponse> postIdea(@Field("user_name") int user_name, @Field("table_name") int table_name, @Field("thing") String thing);
}
