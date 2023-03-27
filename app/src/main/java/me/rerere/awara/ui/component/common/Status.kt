package me.rerere.awara.ui.component.common

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import me.rerere.awara.R

@Composable
fun BoxScope.TodoStatus() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.maintenance))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.align(Alignment.Center)
    )
}