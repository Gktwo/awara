package me.rerere.awara.ui.component.hitokoto

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import me.rerere.awara.data.entity.Hitokoto
import me.rerere.awara.data.source.HitokotoAPI
import org.koin.androidx.compose.get

@Composable
fun Hitokoto(
    modifier: Modifier = Modifier,
    type: String = "a",
) {
    val api = get<HitokotoAPI>()
    val hitokoto by produceState<Hitokoto?>(
        initialValue = null,
        producer = {
            value = kotlin.runCatching { api.getHitokoto(type = type) }.getOrNull()
        }
    )
    Text(
        text = "${hitokoto?.hitokoto ?: ""} -- ${hitokoto?.from ?: ""}",
        modifier = modifier
    )
}