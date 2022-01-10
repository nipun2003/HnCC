package com.nipunapps.hncc.feature_hncc.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.nipunapps.hncc.R
import com.nipunapps.hncc.core.noRippleClickable
import com.nipunapps.hncc.feature_hncc.presentation.viewmodels.ImageFullViewModel
import com.nipunapps.hncc.ui.theme.ExtraBigPadding
import com.nipunapps.hncc.ui.theme.SmallPadding
import com.nipunapps.hncc.ui.theme.SocialIconSize
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ImageFullScreen(
    viewModel: ImageFullViewModel = hiltViewModel()
) {
    val image = viewModel.image.value
    Column(
        Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(ExtraBigPadding))
        val scale = remember { mutableStateOf(1f) }
        val rotationState = remember { mutableStateOf(0f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        var zoom by remember {
            mutableStateOf(2)
        }
        AnimatedVisibility(visible = viewModel.lockVisibility.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SocialIconSize)
                    .padding(SmallPadding)
                    .zIndex(10f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        id = if (!viewModel.lock.value) R.drawable.ic_unlock else R.drawable.ic_lock
                    ),
                    contentDescription = "Lock",
                    modifier = Modifier
                        .size(ExtraBigPadding)
                        .noRippleClickable {
                            viewModel.toggleLock()
                        }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotation ->
                        if (!viewModel.lock.value) {
                            scale.value *= zoom
                            rotationState.value += rotation
                            val x = pan.x * zoom
                            val y = pan.y * zoom
                            val angleRad = rotationState.value * PI / 180.0
                            if (scale.value != 1f) {
                                offsetX += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                                offsetY += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                            }
                        }
                    }
                }
                .noRippleClickable {
                    viewModel.showLock()
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = image
                ),
                contentDescription = image,
                modifier = Modifier
                    .align(Center)
                    .zIndex(-3f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { /* Called when the gesture starts */ },
                            onDoubleTap = {
                                if (zoom == 0) {
                                    offsetX = 0f
                                    offsetY = 0f
                                    scale.value = 1f
                                    rotationState.value = 0f
                                    zoom = 2
                                } else {
                                    offsetX = 0f
                                    offsetY = 0f
                                    scale.value *= 2
                                    rotationState.value = 0f
                                    zoom -= 1
                                }
                            },
                            onLongPress = { /* Called on Long Press */ },
                            onTap = { /* Called on Tap */ }
                        )
                    }
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .graphicsLayer(
                        // adding some zoom limits (min 50%, max 200%)
                        scaleX = maxOf(.5f, minOf(100f, scale.value)),
                        scaleY = maxOf(.5f, minOf(100f, scale.value)),
                        rotationZ = rotationState.value
                    )
            )
        }
    }
}
