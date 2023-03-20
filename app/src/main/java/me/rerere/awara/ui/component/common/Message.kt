package me.rerere.awara.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.theme.info
import me.rerere.awara.ui.theme.success
import me.rerere.awara.ui.theme.warning

/**
 * Provide message container
 *
 * @param modifier The modifier of the message container
 * @param content The content of the message container
 */
@Composable
fun MessageProvider(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val messageHolder = rememberMessageHolder()
    DisposableEffect(Unit) {
        onDispose {
            messageHolder.messages.clear()
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(50)
            messageHolder.messages.removeAll {
                System.currentTimeMillis() - it.createTime > it.duration
            }
        }
    }
    Box {
        CompositionLocalProvider(
            LocalMessageProvider provides messageHolder
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .safeContentPadding()
                .padding(16.dp),
        ) {
            val topMessages = messageHolder.messages.filter { it.position == MessagePosition.TOP }
            val bottomMessages =
                messageHolder.messages.filter { it.position == MessagePosition.BOTTOM }
            MessageList(Alignment.TopCenter, topMessages)
            MessageList(Alignment.BottomCenter, bottomMessages)
        }
    }
}

@Composable
fun rememberMessageHolder() = remember {
    MessageHolder()
}

@Composable
private fun BoxScope.MessageList(
    alignment: Alignment,
    messages: List<Message>,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.align(alignment),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        reverseLayout = alignment == Alignment.TopCenter
    ) {
        items(messages) { message ->
            Surface(
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(2.dp),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 6.dp,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    when (message.type) {
                        MessageType.INFO -> {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info",
                                tint = MaterialTheme.colorScheme.info,
                            )
                        }

                        MessageType.SUCCESS -> {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Success",
                                tint = MaterialTheme.colorScheme.success,
                            )
                        }

                        MessageType.WARNING -> {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                contentDescription = "Warning",
                                tint = MaterialTheme.colorScheme.warning,
                            )
                        }

                        MessageType.ERROR -> {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error,
                            )
                        }

                        else -> {}
                    }
                    message.content()
                }
            }
        }
    }
}

/**
 * The type of the message
 */
enum class MessageType {
    DEFAULT, INFO, SUCCESS, WARNING, ERROR
}

/**
 * The position of the message
 */
enum class MessagePosition {
    TOP, BOTTOM
}

/**
 * @param type The type of the message
 * @param position The position of the message
 * @param content The content of the message
 * @param createTime The time when the message is created
 * @param duration The duration of the message
 */
class Message(
    val type: MessageType,
    val position: MessagePosition,
    val content: @Composable () -> Unit,
    val createTime: Long = System.currentTimeMillis(),
    val duration: Long = 3000
)

class MessageHolder {
    val messages = mutableStateListOf<Message>()

    private fun createMessage(
        type: MessageType = MessageType.DEFAULT,
        position: MessagePosition = MessagePosition.BOTTOM,
        duration: Long = 3000,
        content: @Composable () -> Unit
    ) {
        messages.add(Message(type, position, content))
    }

    /**
     * Create a default message
     *
     * @param position The position of the message
     * @param duration The duration of the message
     * @param content The content of the message
     */
    fun default(
        position: MessagePosition = MessagePosition.BOTTOM,
        duration: Long = 3000,
        content: @Composable () -> Unit
    ) {
        createMessage(MessageType.DEFAULT, position, duration, content)
    }

    /**
     * Create a info message
     *
     * @param position The position of the message
     * @param duration The duration of the message
     * @param content The content of the message
     */
    fun info(
        position: MessagePosition = MessagePosition.BOTTOM,
        duration: Long = 3000,
        content: @Composable () -> Unit
    ) {
        createMessage(MessageType.INFO, position, duration, content)
    }

    /**
     * Create a success message
     *
     * @param position The position of the message
     * @param duration The duration of the message
     * @param content The content of the message
     */
    fun success(
        position: MessagePosition = MessagePosition.BOTTOM,
        duration: Long = 3000,
        content: @Composable () -> Unit
    ) {
        createMessage(MessageType.SUCCESS, position, duration, content)
    }

    /**
     * Create a warning message
     *
     * @param position The position of the message
     * @param duration The duration of the message
     * @param content The content of the message
     */
    fun warning(
        position: MessagePosition = MessagePosition.BOTTOM,
        duration: Long = 3000,
        content: @Composable () -> Unit
    ) {
        createMessage(MessageType.WARNING, position, duration, content)
    }

    /**
     * Create a error message
     *
     * @param position The position of the message
     * @param duration The duration of the message
     * @param content The content of the message
     */
    fun error(
        position: MessagePosition = MessagePosition.BOTTOM,
        duration: Long = 3000,
        content: @Composable () -> Unit
    ) {
        createMessage(MessageType.ERROR, position, duration, content)
    }
}