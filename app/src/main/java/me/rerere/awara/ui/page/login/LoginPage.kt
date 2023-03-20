package me.rerere.awara.ui.page.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rerere.awara.R
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType
import me.rerere.awara.util.openUrl
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalTextApi::class)
@Composable
fun LoginPage(
    vm: LoginVM = koinViewModel()
) {
    val context = LocalContext.current
    val messageProvider = LocalMessageProvider.current
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.login_title),
                    )
                },
                navigationIcon = { BackButton() }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .width(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.mipmap.ic_launcher_round,
                    contentDescription = "logo",
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = {
                        Text(text = stringResource(R.string.login_user))
                    }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text(text = stringResource(R.string.login_password))
                    }
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    type = ButtonType.Default
                ) {
                    Text(text = stringResource(R.string.login_button))
                }

                Row {
                    TextButton(
                        onClick = {
                            messageProvider.warning {
                                Text("TODO")
                            }
                        }
                    ) {
                        Text("用户协议")
                    }

                    TextButton(
                        onClick = {
                            context.openUrl("https://iwara.tv/register")
                        }
                    ) {
                        Text("注册")
                    }
                }
            }
        }
    }
}