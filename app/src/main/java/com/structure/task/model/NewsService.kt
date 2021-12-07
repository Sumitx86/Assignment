package com.structure.task.model

import com.structure.task.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class NewsService {

    @Inject
    lateinit var api: NewsApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getNews(url:String): Single<News> {
        return api.getNews(url)
    }
}