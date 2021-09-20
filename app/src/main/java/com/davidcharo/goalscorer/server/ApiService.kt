package com.davidcharo.goalscorer.server

import com.davidcharo.goalscorer.model.TeamList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("x-apisports-key: ae095914fff0f557ef1769e23bc19db0")
    @GET("leagues/seasons")
    fun getTopRated(): Call<TeamList>

    companion object{
        val URL_API = "https://v3.football.api-sports.io/"

        fun create(): ApiService{

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)

        }
    }
}