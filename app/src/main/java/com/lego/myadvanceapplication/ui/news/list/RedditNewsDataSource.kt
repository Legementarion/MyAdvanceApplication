package com.lego.myadvanceapplication.ui.news.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lego.myadvanceapplication.domain.news.model.RedditPost
import com.lego.myadvanceapplication.domain.news.usecase.GetTopNewsUseCase
import kotlinx.coroutines.*
import timber.log.Timber

class RedditNewsDataSource(
    private val scope: CoroutineScope,
    private val getTopNewsUseCase: GetTopNewsUseCase
) :
    PageKeyedDataSource<String, RedditPost>() {

    var state: MutableLiveData<State> = MutableLiveData()

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        updateState(State.ERROR)  // todo add error/empty state handling
        Timber.e(RedditNewsDataSource::class.java.simpleName, "An error happened: $e")
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RedditPost>
    ) {
        updateState(State.LOADING)
        scope.launch(getJobErrorHandler()) {
            val result = withContext(Dispatchers.IO) {
                getTopNewsUseCase.getTopNews(limit = params.requestedLoadSize)
            }
            updateState(State.DONE)
            callback.onResult(result.posts, result.before, result.after)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        updateState(State.LOADING)
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                getTopNewsUseCase.getTopNews(limit = params.requestedLoadSize, after = params.key)
            }
            updateState(State.DONE)
            callback.onResult(result.posts, result.after)
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {
        updateState(State.LOADING)
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                getTopNewsUseCase.getTopNews(limit = params.requestedLoadSize, before = params.key)
            }
            updateState(State.DONE)
            callback.onResult(result.posts, result.before)
        }

    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    enum class State {
        DONE, LOADING, ERROR, EMPTY
    }

}