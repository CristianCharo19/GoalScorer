package com.davidcharo.goalscorer.server

import com.davidcharo.goalscorer.model.rating.RatingList
import com.davidcharo.goalscorer.model.score.FixturesList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("x-rapidapi-key: 19c83eefd8mshf56ed5d8e24d0c0p1c5d3djsna35248ec8299")
    @GET("fixtures?league=239&season=2021")
    fun getTopRated(): Call<FixturesList>

    @Headers("x-rapidapi-key: 19c83eefd8mshf56ed5d8e24d0c0p1c5d3djsna35248ec8299")
    @GET("standings?season=2021&league=239")
    fun getTopRating(): Call<RatingList>


    companion object {
        fun create(URL_API: String): ApiService {

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