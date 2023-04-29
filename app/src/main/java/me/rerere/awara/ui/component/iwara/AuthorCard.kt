package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.rerere.awara.R
import me.rerere.awara.data.entity.User
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType

@Composable
fun AuthorCard(
    user: User?,
    onClickSub: (() -> Unit)?
) {
    val router = LocalRouterProvider.current
    if (user != null) {
        Card(
            onClick = {
                router.navigate("user/${user.username}")
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Avatar(
                    user = user,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = "@${user.username}",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }

                onClickSub?.let {
                    Button(
                        onClick = onClickSub,
                        type = if (user.following) ButtonType.Outlined else ButtonType.Default
                    ) {
                        Text(
                            if (user.following) stringResource(R.string.unfollow) else stringResource(
                                R.string.follow
                            )
                        )
                    }
                }
            }
        }
    }
}