package com.lego.myadvanceapplication.data

import retrofit2.http.POST

interface RedditApi {

    @POST
    fun getTopNews()

    @POST
    fun getHotNews()

    @POST
    fun authorize()

}