package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.models.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RedditApi {

    @GET("/top.json")
    suspend fun getTopNews(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): NewsResponse

    @GET("/hot.json")
    suspend fun getHotNews(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): NewsResponse

    @GET("/new.json")
    suspend fun getNewNews(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): NewsResponse

    @POST("/api/vote")
    suspend fun vote(
        @Query("id") id: Int,
        @Query("dir") dir: Int
    )

    @POST
    fun authorize()

    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(): Retrofit {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.HEADERS
                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(logging)

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit!!
        }


        const val BASE_URL = "https://www.reddit.com"
    }

}