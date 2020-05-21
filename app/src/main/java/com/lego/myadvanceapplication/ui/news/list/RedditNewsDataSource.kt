package com.lego.myadvanceapplication.ui.news.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.model.RedditPost
import com.lego.myadvanceapplication.domain.news.usecase.GetFavoriteNewsUseCase
import com.lego.myadvanceapplication.domain.news.usecase.GetHotNewsUseCase
import com.lego.myadvanceapplication.domain.news.usecase.GetNewNewsUseCase
import com.lego.myadvanceapplication.domain.news.usecase.GetTopNewsUseCase
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

class RedditNewsDataSource(
    private val scope: CoroutineScope,
    private val getHotNewsUseCase: GetHotNewsUseCase,
    private val getTopNewsUseCase: GetTopNewsUseCase,
    private val getNewNewsUseCase: GetNewNewsUseCase,
    private val getFavorNewsUseCase: GetFavoriteNewsUseCase,
    private val pageType: Page
) :
    PageKeyedDataSource<String, RedditPost>() {

    var state: MutableLiveData<State> = MutableLiveData()

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        updateState(State.ERROR)  // todo check error/empty state handling
        Timber.e(RedditNewsDataSource::class.java.simpleName, "An error happened: $e")
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RedditPost>
    ) {
        updateState(State.LOADING)
        scope.launch(getJobErrorHandler()) {
            val result = withContext(Dispatchers.IO) {
                loadNewsFor(limit = params.requestedLoadSize)
            }
            result?.let {
                if (result.isEmpty()) {
                    updateState(State.EMPTY)
                } else {
                    updateState(State.DONE)
                    callback.onResult(result.posts, result.before, result.after)
                }
            } ?: run { updateState(State.ERROR) }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        updateState(State.LOADING)
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                loadNewsFor(limit = params.requestedLoadSize, after = params.key)
            }
            result?.let {
                updateState(State.DONE)
                callback.onResult(result.posts, result.after)
            } ?: run {
                updateState(State.ERROR)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {
        updateState(State.LOADING)
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                loadNewsFor(limit = params.requestedLoadSize, before = params.key)
            }
            result?.let {
                updateState(State.DONE)
                callback.onResult(result.posts, result.before)
            } ?: run {
                updateState(State.ERROR)
            }
        }

    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    fun refreshData() {  //dirty hack to avoid coroutine dispatcher canceletion
        super.invalidate()
    }

    private suspend fun loadNewsFor(
        limit: Int,
        after: String? = null,
        before: String? = null
    ): RedditData? {

        try {
            return when (pageType) {
                Page.HOT -> {
                    getHotNewsUseCase.getHotNews(limit = limit, after = after, before = before)
                }
                Page.TOP -> {
                    getTopNewsUseCase.getTopNews(limit = limit, after = after, before = before)
                }
                Page.NEW -> {
                    getNewNewsUseCase.getNewNews(limit = limit, after = after, before = before)
                }
                Page.FAVORITE -> {
                    getFavorNewsUseCase.getFavoriteNews(
                        limit = limit,
                        after = after,
                        before = before
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(RedditNewsDataSource::class.java.simpleName, "An error happened: $e")
        }
        return null
    }

    enum class State {
        DONE, LOADING, ERROR, EMPTY
    }

}