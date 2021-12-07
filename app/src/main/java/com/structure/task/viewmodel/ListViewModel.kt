package com.structure.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.structure.task.di.DaggerApiComponent
import com.structure.task.model.Articles
import com.structure.task.model.News
import com.structure.task.model.NewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {
    @Inject
    lateinit var newsService: NewsService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()
    val articles = MutableLiveData<List<Articles>>()
    val newsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private fun fetchNews(query: String, date: String) {
        //2021-11-07
        val url =
            "v2/everything?q=$query&from=$date&sortBy=publishedAt&apiKey=50d0bcfd54c24e77a2194bf0f0651aea"
        System.out.print(url)
        loading.value = true
        disposable.add(
            newsService.getNews(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<News>() {
                    override fun onSuccess(value: News?) {
                        articles.value = value?.articles
                        newsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        newsLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    fun refresh(query: String, date: String) {
        fetchNews(query, date)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}