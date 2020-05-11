package com.lego.myadvanceapplication.ui.news.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lego.myadvanceapplication.domain.news.model.RedditPost
import com.lego.myadvanceapplication.domain.news.usecase.GetTopNewsUseCase
import kotlinx.coroutines.*

class RedditNewsViewModel(private val getTopNewsUseCase: GetTopNewsUseCase) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val list: MutableLiveData<List<RedditPost>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun openNewsDetails(it: Long) {
        TODO("Not yet implemented")
    }

    fun refresh() {
        list.value = null

        uiScope.launch {
            val result = withContext(Dispatchers.IO) {
                getTopNewsUseCase.getTopNews(LIST_SIZE)
            }
            list.value = result
        }
    }

    fun loadData() {
        uiScope.launch {
            val result = withContext(Dispatchers.IO) {
                getTopNewsUseCase.getTopNews(LIST_SIZE)
            }
            list.value = result
        }
    }

    companion object {
        const val LIST_SIZE: Int = 20
    }

}