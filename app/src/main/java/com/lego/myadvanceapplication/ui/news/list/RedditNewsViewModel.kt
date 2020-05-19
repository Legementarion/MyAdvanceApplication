package com.lego.myadvanceapplication.ui.news.list

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lego.myadvanceapplication.domain.news.model.RedditPost
import com.lego.myadvanceapplication.domain.news.usecase.GetTopNewsUseCase

class RedditNewsViewModel(private val getTopNewsUseCase: GetTopNewsUseCase) : ViewModel() {

    private lateinit var dataSource: RedditNewsDataSource
    private var postsLiveData: LiveData<PagedList<RedditPost>>
    val newsDataSourceLiveData = MutableLiveData<RedditNewsDataSource>()

    fun getState(): LiveData<RedditNewsDataSource.State> =
        Transformations.switchMap(newsDataSourceLiveData, RedditNewsDataSource::state)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(LIST_SIZE)
            .setInitialLoadSizeHint(LIST_SIZE)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

    fun getPosts(): LiveData<PagedList<RedditPost>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, RedditPost> {

        val dataSourceFactory = object : DataSource.Factory<String, RedditPost>() {
            override fun create(): DataSource<String, RedditPost> {
                dataSource = RedditNewsDataSource(viewModelScope, getTopNewsUseCase)
                newsDataSourceLiveData.postValue(dataSource)
                return dataSource
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun refresh() {
        dataSource.invalidate()
    }

    companion object {
        const val LIST_SIZE: Int = 30
    }

}