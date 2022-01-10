package com.nipunapps.hncc.feature_hncc.presentation.screen

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.nipunapps.hncc.R
import com.nipunapps.hncc.core.noRippleClickable
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Member
import com.nipunapps.hncc.feature_hncc.domain.model.HeadBodyModel
import com.nipunapps.hncc.feature_hncc.presentation.viewmodels.TeamViewModel
import com.nipunapps.hncc.ui.theme.*
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun TeamScreen(
    viewModel: TeamViewModel = hiltViewModel(),
    navController: NavController
) {
    val teamState = viewModel.teamState.value
    val coroutine = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(BigPadding)
        ) {
            item {
                TeamQuote(
                    modifier = Modifier.fillMaxWidth(),
                    headBodyModel = HeadBodyModel(
                        head = "Our Team",
                        body = R.string.team_quote
                    )
                )
                Spacer(modifier = Modifier.size(BigPadding))
            }
            val bearer = teamState.data
            items(bearer.size) {
                val team = bearer[it]
                if (it > 0) {
                    Spacer(modifier = Modifier.size(SmallPadding))
                }
                TeamCard(
                    modifier = Modifier.fillMaxWidth(),
                    team = team
                ) { image ->
                    val enCodeImage = URLEncoder.encode(
                        image, StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate("image/$enCodeImage")
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(top = SmallPadding)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            coroutine.launch {
                                listState.animateScrollToItem(
                                    index = 0
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
                }
                Spacer(modifier = Modifier.size(BigPadding))
            }
        }
        if (teamState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if (teamState.message != null) {
            teamState.message?.let { message ->
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
fun TeamQuote(
    modifier: Modifier = Modifier,
    headBodyModel: HeadBodyModel
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
        Spacer(modifier = Modifier.size(ExtraSmallPadding))
        Text(
            text = if(headBodyModel.body != null)stringResource(id = headBodyModel.body)else headBodyModel.message,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Right
        )
    }
}

@Composable
fun TeamCard(
    modifier: Modifier = Modifier,
    team: Bearer,
    onImageClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = team.header,
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

        FlowRow(
            modifier = Modifier
                .padding(SmallPadding)
                .fillMaxWidth(),
            crossAxisAlignment = FlowCrossAxisAlignment.Start,
            mainAxisAlignment = MainAxisAlignment.SpaceBetween,
            crossAxisSpacing = SmallPadding,
            mainAxisSpacing = SmallPadding
        ) {
            val members = team.body
            members.forEach { member ->
                SingleMember(
                    modifier = Modifier
                        .fillMaxWidth(0.48f),
                    member = member
                ) { img ->
                    onImageClick(img)
                }
            }
        }
    }
}

@Composable
fun SingleMember(
    modifier: Modifier = Modifier,
    member: Member,
    onImageClick: (String) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(SmallPadding),
        border = BorderStroke(
            width = BigStroke,
            color = SkyBlueLow
        )
    ) {
        Column(
            modifier = Modifier
                .padding(SmallPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(
                    data = member.image
                ),
                contentDescription = member.name,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.89f)
                    .clip(RoundedCornerShape(ExtraSmallPadding))
                    .noRippleClickable {
                        onImageClick(member.image)
                    }
            )
            Spacer(modifier = Modifier.size(SmallPadding))
            Text(
                text = member.name,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(ExtraSmallPadding))
            Text(
                text = member.position,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(SmallPadding))
            if (member.isSociallyInvisible()) {
                Text(
                    text = "Socially Invisible",
                    style = MaterialTheme.typography.body1,
                    color = Gray
                )
            } else {
                val state = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            state = state,
                            enabled = true
                        ),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_github),
                        contentDescription = "Github",
                        modifier = Modifier
                            .size(TeamSocialIconSize)
                            .padding(ExtraSmallPadding)
                            .clip(RoundedCornerShape(ExtraSmallPadding))
                            .clickable {
                                var github = member.github
                                if (!github.startsWith("https")) {
                                    github += "https"
                                }
                                uriHandler.openUri(github)
                            },
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.size(ExtraSmallPadding))
                    Image(
                        painter = painterResource(id = R.drawable.ic_linkedin),
                        contentDescription = "LinkedIn",
                        modifier = Modifier
                            .size(TeamSocialIconSize)
                            .padding(ExtraSmallPadding)
                            .clip(RoundedCornerShape(ExtraSmallPadding))
                            .clickable {
                                var linkedIn = member.linkedin
                                if (!linkedIn.startsWith("https")) {
                                    linkedIn += "https"
                                }
                                uriHandler.openUri(linkedIn)
                            },
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.size(ExtraSmallPadding))
                    Image(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email",
                        modifier = Modifier
                            .size(TeamSocialIconSize)
                            .padding(ExtraSmallPadding)
                            .clip(RoundedCornerShape(ExtraSmallPadding))
                            .clickable {
                                var email = member.email
                                try {
                                    uriHandler.openUri(email)
                                } catch (e: Exception) {
                                    Log.e("Nipun", e.message.toString())
                                }
                            },
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
            }
        }
    }
}