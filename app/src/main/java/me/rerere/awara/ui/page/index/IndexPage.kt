package me.rerere.awara.ui.page.index

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun IndexPage(
    vm: IndexVM = koinViewModel()
) {
    Button(
        onClick = {
            vm.test()
        }
    ) {
        Text("Hello World!")
    }
}