package com.structure.task.di

import com.structure.task.di.ApiModule
import com.structure.task.model.NewsService
import com.structure.task.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: NewsService)
    fun inject(viewModel: ListViewModel)
}