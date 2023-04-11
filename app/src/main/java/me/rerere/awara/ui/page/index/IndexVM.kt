package me.rerere.awara.ui.page.index

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.R
import me.rerere.awara.data.entity.Media
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching
import me.rerere.awara.ui.component.iwara.param.FilterValue
import me.rerere.awara.ui.component.iwara.param.sort.MediaSortOptions
import me.rerere.awara.ui.component.iwara.param.toParams

private const val TAG = "IndexVM"

class IndexVM(
    private val userRepo: UserRepo,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    var state by mutableStateOf(IndexState())
        private set

    init {
        loadSubscriptions()
        loadVideoList()
    }

    private fun loadSubscriptions() {
        viewModelScope.launch {
            state = state.copy(subscriptionLoading = true)
            runAPICatching {
                val param = mapOf(
                    "subscribed" to "true",
                    "limit" to "24",
                    "page" to (state.subscriptionPage - 1).toString()
                )
                when(state.subscriptionType){
                    SubscriptionType.VIDEO -> mediaRepo.getVideoList(param)
                    SubscriptionType.IMAGE -> mediaRepo.getImageList(param)
                }
            }.onSuccess {
                state = state.copy(
                    subscriptions = it.results,
                    subscriptionTotal = it.count
                )
            }
            state = state.copy(subscriptionLoading = false)
        }
    }

    fun jumpToSubscriptionPage(page: Int){
        if(page == state.subscriptionPage || page < 1) return
        state = state.copy(subscriptionPage = page)
        loadSubscriptions()
    }

    fun changeSubscriptionType(it: IndexVM.SubscriptionType) {
        state = state.copy(subscriptionType = it)
        loadSubscriptions()
    }

    fun loadVideoList() {
        viewModelScope.launch {
            state = state.copy(videoLoading = true)
            runAPICatching {
                mediaRepo.getVideoList(
                    mapOf(
                        "limit" to "24",
                        "page" to (state.videoPage - 1).toString(),
                        "sort" to state.videoSort,
                    ) + state.videoFilters.toParams()
                )
            }.onSuccess {
                state = state.copy(
                    videoList = it.results,
                    videoCount = it.count
                )
            }
            state = state.copy(videoLoading = false)
        }
    }

    fun updateVideoSort(sort: String){
        state = state.copy(videoSort = sort)
        loadVideoList()
    }

    fun updateVideoPage(it: Int) {
        if(it == state.videoPage || it < 1) return
        state = state.copy(videoPage = it)
        loadVideoList()
    }

    fun addFilter(filterValue: FilterValue) {
        state = state.copy(videoFilters = state.videoFilters + filterValue)
    }

    fun removeFilter(filterValue: FilterValue) {
        state = state.copy(videoFilters = state.videoFilters - filterValue)
    }

    data class IndexState(
        val subscriptionLoading: Boolean = false,
        val subscriptionPage: Int = 1,
        val subscriptionTotal: Int = 0,
        val subscriptionType : SubscriptionType = SubscriptionType.VIDEO,
        val subscriptions: List<Media> = emptyList(),
        val videoLoading: Boolean = false,
        val videoPage: Int = 1,
        val videoCount: Int = 0,
        val videoList: List<Media> = emptyList(),
        val videoSort: String = MediaSortOptions.first().name,
        val videoFilters: List<FilterValue> = emptyList(),
    )

    enum class SubscriptionType(
        val id: Int
    ) {
        VIDEO(R.string.video),
        IMAGE(R.string.image),
    }
}

