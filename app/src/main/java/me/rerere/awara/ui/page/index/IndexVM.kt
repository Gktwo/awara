package me.rerere.awara.ui.page.index

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.APIResult
import me.rerere.awara.data.source.runAPICatching

private const val TAG = "IndexVM"

class IndexVM(
    private val userRepo: UserRepo
) : ViewModel() {
    fun test() {
        viewModelScope.launch {
            val result = runAPICatching {
                userRepo.getProfile("rererxe")
            }
            when(result) {
                is APIResult.Success -> {
                    Log.d(TAG, "test: ${result.data}")
                }
                is APIResult.Error -> {
                    Log.d(TAG, "test: ${result.toString()}")
                }
                is APIResult.Exception -> {
                    Log.d(TAG, "test: ${result.exception}")
                }
            }
        }
    }
}