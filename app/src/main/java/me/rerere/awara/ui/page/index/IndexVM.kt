package me.rerere.awara.ui.page.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.dto.LoginReq
import me.rerere.awara.data.dto.LoginRes
import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.data.source.ifAPIError
import retrofit2.HttpException

class IndexVM(
    private val iwaraAPI: IwaraAPI
) : ViewModel() {
    fun test() {
        println("Hello World!")
        viewModelScope.launch {
            val result = runCatching {
                iwaraAPI.login(LoginReq("qq1609403959@outlook.com", "a20000512"))
                println("Success")
            }.getOrElse {
                it.ifAPIError {
                    println("API Error: $it")
                }
            }
        }
    }
}