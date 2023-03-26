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

private const val TAG = "IndexVM"

class IndexVM(
    private val userRepo: UserRepo,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    var state by mutableStateOf(IndexState())
        private set

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
                    subscriptions = it.results
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

    init {
        loadSubscriptions()
    }

    data class IndexState(
        val subscriptionLoading: Boolean = false,
        val subscriptionPage: Int = 1,
        val subscriptionType : SubscriptionType = SubscriptionType.VIDEO,
        val subscriptions: List<Media> = emptyList(),
    )

    enum class SubscriptionType(
        val id: Int
    ) {
        VIDEO(R.string.video),
        IMAGE(R.string.image),
    }
}

