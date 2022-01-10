package com.nipunapps.hncc.feature_hncc.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Content
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Message
import com.nipunapps.hncc.feature_hncc.domain.model.HeadBodyModel
import com.nipunapps.hncc.feature_hncc.presentation.viewmodels.AboutViewModel
import com.nipunapps.hncc.ui.theme.*

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel()
) {
    val aboutState = viewModel.aboutState.value
    val coroutine = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(BigPadding)
        ) {
            item {
                val head = HeadBodyModel(
                    head = aboutState.data.header,
                    message = aboutState.data.header_message
                )
                TextWithHead(
                    modifier = Modifier.fillMaxWidth(),
                    headBodyModel = head
                )
                Spacer(modifier = Modifier.size(BigPadding))
            }
            val contents = aboutState.data.content
            items(contents.size) {
                if (it > 0) {
                    Spacer(modifier = Modifier.size(BigPadding))
                }
                ContentComp(
                    modifier = Modifier.fillMaxWidth(),
                    content = contents[it]
                )
            }
        }
        if (aboutState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if (aboutState.message != null) {
            aboutState.message?.let { message ->
                Text(
                    text = message,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ContentComp(
    modifier: Modifier = Modifier,
    content: Content
) {
    val messages = content.messages
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = content.header,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.size(BigStroke))
        LinearProgressIndicator(
            modifier = Modifier
                .height(MediumStroke)
                .alpha(0.5f),
            color = SkyBlueLow,
            backgroundColor = Color.Transparent
        )
        Spacer(modifier = Modifier.size(BigPadding))
        messages.forEachIndexed { index, message ->
            if (index > 0) {
                Spacer(modifier = Modifier.size(SmallPadding))
            }
            MessageComp(
                modifier = Modifier.fillMaxWidth(),
                message = message
            )
        }
    }
}

@Composable
fun MessageComp(
    modifier: Modifier = Modifier,
    message: Message
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val annotatedText = buildAnnotatedString {
            append(message.message)
            message.know_more?.let { knowMore ->
                pushStringAnnotation(
                    tag = "URL",
                    annotation = if (!knowMore.startsWith("https")) "https$knowMore" else knowMore
                )
                withStyle(
                    style = SpanStyle(
                        color = SkyBlue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("  Know More")
                }
            }
        }
        ClickableText(text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "URL", start = offset,
                    end = offset
                )
                    .firstOrNull()?.let { annotation ->
                        uriHandler.openUri(annotation.item)
                    }
            },
            style = MaterialTheme.typography.body1
        )
    }
}