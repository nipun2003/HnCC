package com.nipunapps.hncc.feature_hncc.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.nipunapps.hncc.feature_hncc.presentation.components.SocialComponent
import com.nipunapps.hncc.ui.Screen
import com.nipunapps.hncc.ui.theme.*
import com.nipunapps.hncc.R

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    onclick: (String) -> Unit
) {
    Column(
        modifier = modifier.background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.size(PaddingStatusBar))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(BigPadding)
                    .size(LogoSize),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
            Divider()
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.size(PaddingStatusBar))
                NavOption(screen = Screen.HomeScreen) { route ->
                    onclick(route)
                }
                NavOption(screen = Screen.AboutScreen) { route ->
                    onclick(route)
                }
                NavOption(screen = Screen.TeamScreen) { route ->
                    onclick(route)
                }
            }
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            WEmail(
                modifier = Modifier
                    .padding(BigPadding)
                    .fillMaxWidth()
            )
            SocialComponent()
            Spacer(modifier = Modifier.size(ExtraSmallPadding))
            Text(
                text = "2021 Hackathon and Coding Club.",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(BigPadding))
        }
    }
}

@Composable
fun WEmail(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Write",
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        SelectionContainer {
            Text(
                text = "hnccbits@gmail.com",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun NavOption(
    screen: Screen,
    onclick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(SmallPadding)
            .clickable {
                onclick(screen.route)
            },
    ) {
        Text(
            text = screen.title,
            style = MaterialTheme.typography.h2
        )
    }
}