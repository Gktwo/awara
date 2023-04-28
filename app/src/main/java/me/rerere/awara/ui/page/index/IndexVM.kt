package me.rerere.awara.ui.page.index

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import me.rerere.awara.R
import me.rerere.awara.data.entity.Media
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.UpdateChecker
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching
import me.rerere.awara.ui.component.iwara.param.FilterValue
import me.rerere.awara.ui.component.iwara.param.sort.MediaSortOptions
import me.rerere.awara.ui.component.iwara.param.toParams

private const val TAG = "IndexVM"

class IndexVM(
    private val userRepo: UserRepo,
    private val mediaRepo: MediaRepo,
    private val updateChecker: UpdateChecker
) : ViewModel() {
    var state by mutableStateOf(IndexState())
        private set
    var videoSort: String by mutableStateOf(MediaSortOptions.first().name)
    val videoFilters: MutableList<FilterValue> = mutableStateListOf()
    var imageSort: String = MediaSortOptions.first().name
    val imageFilters: MutableList<FilterValue> = mutableStateListOf()

    val events = MutableSharedFlow<IndexEvent>()

    init {
        loadSubscriptions()
        loadVideoList()
        loadImageList()
        checkUpdate()
    }

    private fun checkUpdate() {
        viewModelScope.launch {
            kotlin.runCatching {
                val (code, name, changes) = updateChecker.getLatestVersion()
                events.emit(IndexEvent.ShowUpdateDialog(code, name, changes))
            }
        }
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
                        "sort" to videoSort,
                    ) + videoFilters.toParams()
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

    fun loadImageList() {
        viewModelScope.launch {
            state = state.copy(imageLoading = true)
            runAPICatching {
                mediaRepo.getImageList(
                    mapOf(
                        "limit" to "24",
                        "page" to (state.imagePage - 1).toString(),
                        "sort" to imageSort,
                    ) + imageFilters.toParams()
                )
            }.onSuccess {
                state = state.copy(
                    imageList = it.results,
                    imageCount = it.count
                )
            }
            state = state.copy(imageLoading = false)
        }
    }

    fun updateVideoSort(sort: String){
        videoSort = sort
        loadVideoList()
    }

    fun updateVideoPage(it: Int) {
        if(it == state.videoPage || it < 1) return
        state = state.copy(videoPage = it)
        loadVideoList()
    }

    fun addVideoFilter(filterValue: FilterValue) {
        videoFilters.add(filterValue)
    }

    fun removeVideoFilter(filterValue: FilterValue) {
        videoFilters.remove(filterValue)
    }

    fun updateImageSort(sort: String){
        imageSort = sort
        loadImageList()
    }

    fun updateImagePage(it: Int) {
        if(it == state.imagePage || it < 1) return
        state = state.copy(imagePage = it)
        loadImageList()
    }

    fun addImageFilter(filterValue: FilterValue) {
        imageFilters.add(filterValue)
    }

    fun removeImageFilter(filterValue: FilterValue) {
        imageFilters.remove(filterValue)
    }

    fun clearImageFilter() {
        imageFilters.clear()
    }

    fun clearVideoFilter() {
        videoFilters.clear()
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
        val imageLoading: Boolean = false,
        val imagePage: Int = 1,
        val imageCount: Int = 0,
        val imageList: List<Media> = emptyList(),
    )

    enum class SubscriptionType(
        val id: Int
    ) {
        VIDEO(R.string.video),
        IMAGE(R.string.image),
    }

    sealed class IndexEvent {
        data class ShowUpdateDialog(val code: Int, val version: String, val changes: String) : IndexEvent()
    }
}

