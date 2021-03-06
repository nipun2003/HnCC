package com.nipunapps.hncc.feature_hncc.presentation.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.nipunapps.hncc.R
import com.nipunapps.hncc.feature_hncc.domain.model.HeadBodyModel
import com.nipunapps.hncc.feature_hncc.domain.model.demoArchitects
import com.nipunapps.hncc.feature_hncc.domain.model.demoInfos
import com.nipunapps.hncc.feature_hncc.presentation.components.ArchitectComp
import com.nipunapps.hncc.feature_hncc.presentation.viewmodels.MainViewModel
import com.nipunapps.hncc.ui.Screen
import com.nipunapps.hncc.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val imageState = viewModel.url.value
    val url = imageState.data
    val coroutine = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    Scaffold(modifier = Modifier.fillMaxSize()) {
        if (imageState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState, enabled = true)
        ) {
            TopImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.4f),
                imageUrl = url
            )
            TextWithHead(
                modifier = Modifier
                    .padding(BigPadding)
                    .fillMaxWidth(),
                headBodyModel = demoInfos
            ) {
                navController.navigate(Screen.AboutScreen.route)
            }
            Spacer(modifier = Modifier.size(BigPadding))
            demoArchitects.forEachIndexed { i, a ->
                if (i > 0) {
                    Spacer(modifier = Modifier.size(BigPadding))
                }
                ArchitectComp(
                    modifier = Modifier
                        .padding(horizontal = BigPadding)
                        .fillMaxWidth(),
                    architect = demoArchitects[i]
                )
            }
            Spacer(modifier = Modifier.size(BigPadding))
            Button(
                onClick = {
                    coroutine.launch {
                        scrollState.animateScrollTo(
                            value = 0,
                            animationSpec = tween(
                                durationMillis = 150,
                            )
                        )
                    }
                },
                border = BorderStroke(
                    width = SmallStroke,
                    color = GenreColor
                ),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Up",
                    tint = Color.White,
                    modifier = Modifier
                        .size(PaddingStatusBar)
                        .padding(ExtraSmallPadding)
                        .rotate(90f)
                )
                Spacer(modifier = Modifier.size(ExtraSmallPadding))
                Text(
                    text = "Top",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(ExtraSmallPadding)
                )

            }
            Spacer(modifier = Modifier.size(BigPadding))
        }
    }
}

@Composable
fun TopImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val context = LocalContext.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val imageLoader = ImageLoader.Builder(context)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(GifDecoder())
                }
            }
            .build()
        if (imageUrl.isNotBlank()) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Home Image",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = SPBrush
                    )
                )
                .alpha(0.1f)
        )

        Text(
            text = stringResource(
                id = R.string.club_quote
            ),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(SmallPadding)
                .align(Alignment.Center)
        )
    }
}


@Composable
fun TextWithHead(
    modifier: Modifier = Modifier,
    headBodyModel: HeadBodyModel,
    onMoreClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = headBodyModel.head,
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
        }
        Spacer(modifier = Modifier.size(BigPadding))
        Text(
            text = if (headBodyModel.body != null) stringResource(id = headBodyModel.body) else headBodyModel.message,
            style = MaterialTheme.typography.body1
        )
        if (headBodyModel.more) {
            Spacer(modifier = Modifier.size(SmallPadding))
            Button(
                onClick = {
                    onMoreClick()
                },
                border = BorderStroke(
                    width = SmallStroke,
                    color = GenreColor
                ),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    text = "View More",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(ExtraSmallPadding)
                )
            }
        }
    }
}