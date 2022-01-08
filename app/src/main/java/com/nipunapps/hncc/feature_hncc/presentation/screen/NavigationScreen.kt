package com.nipunapps.hncc.feature_hncc.presentation.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.nipunapps.hncc.R
import com.nipunapps.hncc.feature_hncc.presentation.components.SocialComponent
import com.nipunapps.hncc.ui.Screen
import com.nipunapps.hncc.ui.theme.*

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    onclick: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .verticalScroll(state = scrollState, enabled = true),
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

    Button(
        onClick = {
            onclick(screen.route)
        },
        modifier = Modifier
            .width(NavItemWidth)
            .padding(SmallPadding),
        border = BorderStroke(
            width = SmallStroke,
            color = GenreColor
        ),
        shape = RoundedCornerShape(ExtraSmallPadding),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
    ) {
        Text(
            text = screen.title,
            style = MaterialTheme.typography.h2
        )
    }
}