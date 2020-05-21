package com.lego.myadvanceapplication.core

import androidx.room.Room
import com.lego.myadvanceapplication.core.notification.NotificationController
import com.lego.myadvanceapplication.data.*
import com.lego.myadvanceapplication.data.local.AppDatabase
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository
import com.lego.myadvanceapplication.domain.news.repository.RedditRepositoryImpl
import com.lego.myadvanceapplication.domain.news.usecase.GetFavoriteNewsUseCase
import com.lego.myadvanceapplication.domain.news.usecase.GetHotNewsUseCase
import com.lego.myadvanceapplication.domain.news.usecase.GetNewNewsUseCase
import com.lego.myadvanceapplication.domain.news.usecase.GetTopNewsUseCase
import com.lego.myadvanceapplication.ui.main.BasicMainViewModel
import com.lego.myadvanceapplication.ui.news.list.RedditNewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModule = module {
    viewModel { BasicMainViewModel() }
    viewModel { RedditNewsViewModel(get(), get(), get(), get()) }
}

private val networkModule = module {
    single { RedditRemoteDataSourceImpl(get()) }
    single<RedditApi> { RedditApi.getRetrofitInstance().create(RedditApi::class.java) }
//    single { RedditApiInitializer.getRedditApi() } // todo check this way
}

private val databaseModule = module {
    single<RedditLocalDataSource> { RedditLocalDataSourceImpl(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "app_database"
        ).build().redditDao()
    }
    single<RedditRemoteDataSource> { RedditRemoteDataSourceImpl(get()) }
}

private val domainModule = module {
    single<RedditRepository> { RedditRepositoryImpl(get(), get()) }
    factory { GetTopNewsUseCase(get()) }
    factory { GetHotNewsUseCase(get()) }
    factory { GetNewNewsUseCase(get()) }
    factory { GetFavoriteNewsUseCase(get()) }
}

private val coreModule = module {
    single { NotificationController(get()) }
}

val baseModule = coreModule +
        viewModule + networkModule + databaseModule + domainModule