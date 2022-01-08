package com.nipunapps.hncc.feature_hncc.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.nipunapps.hncc.R
import com.nipunapps.hncc.ui.theme.SmallPadding
import com.nipunapps.hncc.ui.theme.SocialIconSize

data class SocialIcon(
    val icon : Int,
    val link : String
)


@Composable
fun SocialComponent(
    modifier: Modifier = Modifier
) {
    val social = listOf(
        SocialIcon(icon =R.drawable.ic_facebook,""),
        SocialIcon(icon =R.drawable.ic_github,""),
        SocialIcon(icon =R.drawable.ic_instagram,""),
        SocialIcon(icon =R.drawable.ic_linkedin,""),
    )
    LazyRow(modifier) {
        items(social.size) {
            if (it > 0) {
                Spacer(modifier = Modifier.size(SmallPadding))
            }
            SocialIcon(social = social[it])
        }
    }
}

@Composable
fun SocialIcon(
     social: SocialIcon
) {
    Box(
        modifier = Modifier
            .size(SocialIconSize)
            .padding(SmallPadding),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = social.icon
            ),
            contentDescription = "Social Icon",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(SmallPadding)),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
    }
}