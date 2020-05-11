package com.lego.myadvanceapplication.core

import androidx.room.Room
import com.lego.myadvanceapplication.core.notification.NotificationController
import com.lego.myadvanceapplication.data.RedditLocalDataSourceImpl
import com.lego.myadvanceapplication.data.RedditRemoteDataSourceImpl
import com.lego.myadvanceapplication.data.local.AppDatabase
import com.lego.myadvanceapplication.data.remote.RedditApiInitializer
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository
import com.lego.myadvanceapplication.domain.news.repository.RedditRepositoryImpl
import com.lego.myadvanceapplication.domain.news.usecase.GetTopNewsUseCase
import com.lego.myadvanceapplication.ui.main.BasicMainViewModel
import com.lego.myadvanceapplication.ui.news.list.RedditNewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModule = module {
    viewModel { BasicMainViewModel() }
    viewModel { RedditNewsViewModel(get()) }
}

private val networkModule = module {
    single { RedditRemoteDataSourceImpl(get()) }
    single { RedditApiInitializer.getRedditApi() }
}

private val databaseModule = module {
    single { RedditLocalDataSourceImpl(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "reddit_news_database"
        ).build().redditDao()
    }
}

private val domainModule = module {
    single<RedditRepository> { RedditRepositoryImpl(get(), get()) }
    single { GetTopNewsUseCase(get()) }
}

private val coreModule = module {
    single { NotificationController(get()) }
}

val baseModule = coreModule +
        viewModule + networkModule + databaseModule + domainModule