package com.nipunapps.hncc.feature_hncc.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.nipunapps.hncc.feature_hncc.domain.model.Architect
import com.nipunapps.hncc.feature_hncc.domain.model.ArchitectInfo
import com.nipunapps.hncc.feature_hncc.domain.model.HeadBodyModel
import com.nipunapps.hncc.feature_hncc.presentation.screen.TextWithHead
import com.nipunapps.hncc.ui.theme.ClipNavigation
import com.nipunapps.hncc.ui.theme.SmallPadding

@Composable
fun ArchitectComp(
    modifier: Modifier = Modifier,
    architect: Architect
) {
    Column(modifier = modifier) {
        TextWithHead(
            modifier = Modifier.fillMaxWidth(),
            headBodyModel = HeadBodyModel(architect.head, architect.body)
        )
        LazyRow(contentPadding = PaddingValues(SmallPadding)) {
            items(architect.infos.size) { index ->
                SingleArchitect(
                    modifier = Modifier
                        .width(ClipNavigation)
                        .padding(SmallPadding),
                    architectInfo = architect.infos[index]
                )
            }
        }
    }
}

@Composable
fun SingleArchitect(
    modifier: Modifier = Modifier,
    architectInfo: ArchitectInfo
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = architectInfo.head,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        Text(
            text = stringResource(id = architectInfo.body),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start
        )
    }
}