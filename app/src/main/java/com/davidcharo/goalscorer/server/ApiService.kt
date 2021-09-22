package com.davidcharo.goalscorer.server

import com.davidcharo.goalscorer.model.EventsList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    //@Headers("x-apisports-key: ae095914fff0f557ef1769e23bc19db0")
    @GET("eventspastleague.php?id=4497")
    fun getTopRated(): Call<EventsList>

    companion object{
        val URL_API = "https://www.thesportsdb.com/api/v1/json/1/"

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