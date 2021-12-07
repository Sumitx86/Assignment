package com.structure.task.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsApi {

    //@GET("v2/everything?q=tesla&from=2021-11-07&sortBy=publishedAt&apiKey=50d0bcfd54c24e77a2194bf0f0651aea")
    @GET
    fun getNews(@Url url:String): Single<News>
}