package me.rerere.awara.ui.stores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

interface Reducer<T, A> {
    fun reduce(prevState: T, action: A): T
}

sealed interface Store<T, A> {
    val state: T
    fun dispatch(action: A)
    fun subscribe(listener: (T) -> Unit)
    fun unsubscribe(listener: (T) -> Unit)
    operator fun invoke(action: A) = dispatch(action)
}

class ReduxStore<T, A>(
    initialState: T,
    private val reducer: Reducer<T, A>
) : Store<T, A> {
    private val listeners = mutableListOf<(T) -> Unit>()
    override var state: T = initialState
        private set

    override fun dispatch(action: A) {
        state = reducer.reduce(state, action)
        listeners.forEach { it(state) }
    }

    override fun subscribe(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    override fun unsubscribe(listener: (T) -> Unit) {
        listeners.remove(listener)
    }
}

@Composable
fun <T, A> Store<T, A>.collectAsState(): T {
    val state = remember { mutableStateOf(this.state) }
    DisposableEffect(Unit) {
        val listener = { newState: T ->
            state.value = newState
        }
        subscribe(listener)
        onDispose {
            unsubscribe(listener)
        }
    }
    return state.value
}