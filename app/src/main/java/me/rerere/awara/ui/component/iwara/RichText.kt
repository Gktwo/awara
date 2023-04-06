package me.rerere.awara.ui.component.iwara

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import me.rerere.awara.ui.theme.info
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.acceptChildren
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.ast.visitors.Visitor
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

private const val TAG = "RichText"

@Composable
fun RichText(
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    onLinkClick: (String) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val richTextTheme = RichTextTheme(
        title1 = MaterialTheme.typography.titleLarge.toSpanStyle(),
        title2 = MaterialTheme.typography.titleMedium.toSpanStyle(),
        title3 = MaterialTheme.typography.titleSmall.toSpanStyle(),
        link = SpanStyle(color = MaterialTheme.colorScheme.info)
    )
    val annotatedString = remember(text) {
        buildAnnotatedString {
            toAnnotatedString(text, richTextTheme)
        }
    }
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator = Modifier.pointerInput(onLinkClick) {
        detectTapGestures { pos ->
            layoutResult.value?.let { layoutResult ->
                val offset = layoutResult.getOffsetForPosition(pos)
                // Log.i(TAG, "RichText: offset：$offset")
                annotatedString.getStringAnnotations(offset - 1, offset).forEach {
                    if(it.tag == "link") {
                        onLinkClick(it.item)
                    }
                }
            }
        }
    }
    Text(
        text = annotatedString,
        maxLines = maxLines,
        overflow = overflow,
        modifier = pressIndicator,
        onTextLayout = { layoutResult.value = it },
        style = style
    )
}

@Stable
data class RichTextTheme(
    val title1: SpanStyle,
    val title2: SpanStyle,
    val title3: SpanStyle,
    val link: SpanStyle,
)

private fun AnnotatedString.Builder.toAnnotatedString(
    text: String,
    theme: RichTextTheme
) {
    val parser = MarkdownParser(CommonMarkFlavourDescriptor())
    val syntaxTree = parser.buildMarkdownTreeFromString(text)
    handle(this, syntaxTree, text, theme)
}

@OptIn(ExperimentalTextApi::class)
private fun AnnotatedString.Builder.handle(
    ctx: AnnotatedString.Builder,
    node: ASTNode,
    text: String,
    theme: RichTextTheme
) {
    Log.i(TAG, "handle: access ${node.type}")
    when (node.type.toString()) {
        "WHITE_SPACE" -> {
            append(" ")
        }

        "Markdown:EOL" -> {
            append("\n")
        }

        "Markdown:ATX_1" -> {
            ctx.pushStyle(theme.title1)
            node.acceptChildren(object : Visitor {
                override fun visitNode(node: ASTNode) {
                    handle(ctx, node, text, theme)
                }
            })
            pop()
        }

        "Markdown:ATX_2" -> {
            ctx.pushStyle(theme.title2)
            node.acceptChildren(object : Visitor {
                override fun visitNode(node: ASTNode) {
                    handle(ctx, node, text, theme)
                }
            })
            pop()
        }

        "Markdown:ATX_3" -> {
            ctx.pushStyle(theme.title3)
            node.acceptChildren(object : Visitor {
                override fun visitNode(node: ASTNode) {
                    handle(ctx, node, text, theme)
                }
            })
            pop()
        }

        "Markdown:INLINE_LINK" -> {
            val textNode = node.children.firstOrNull { it.type.name == "LINK_TEXT" }
            val destinationNode = node.children.firstOrNull { it.type.name == "LINK_DESTINATION" }
            Log.i(TAG, "handle: find link $textNode -> $destinationNode")
            if (textNode != null && destinationNode != null) {
                val textNodeText = textNode.children[1].getTextInNode(text).toString()
                val destinationNodeText = destinationNode.getTextInNode(text).toString()
                Log.i(TAG, "handle: write link $textNodeText -> $destinationNodeText")
                withStyle(theme.link) {
                    withAnnotation("link", destinationNodeText) {
                        append(textNodeText)
                    }
                }
            }
        }

        "Markdown:STRONG" -> {
            ctx.pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            node.acceptChildren(object : Visitor {
                override fun visitNode(node: ASTNode) {
                    handle(ctx, node, text, theme)
                }
            })
            pop()
        }

        "Markdown:TEXT" -> {
            append(node.getTextInNode(text))
        }

        else -> {
            if (node.children.isNotEmpty()) {
                node.acceptChildren(object : Visitor {
                    override fun visitNode(node: ASTNode) {
                        handle(ctx, node, text, theme)
                    }
                })
            }
        }
    }
}
