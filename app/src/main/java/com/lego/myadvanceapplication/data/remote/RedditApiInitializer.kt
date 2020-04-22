package com.lego.myadvanceapplication.data.remote

import com.lego.myadvanceapplication.data.RedditApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RedditApiInitializer {

    private var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return field
        }
    private const val BASE_URL = "https://api.reddit.com"

    fun getRedditApi() {
        retrofit?.create(RedditApi::class.java)
    }

}