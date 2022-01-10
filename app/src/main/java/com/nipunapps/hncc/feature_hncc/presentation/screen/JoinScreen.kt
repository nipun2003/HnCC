package com.nipunapps.hncc.feature_hncc.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.nipunapps.hncc.feature_hncc.domain.model.JoinModel
import com.nipunapps.hncc.feature_hncc.presentation.viewmodels.JoinUsViewModel
import com.nipunapps.hncc.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun JoinUsScreen(
    viewModel: JoinUsViewModel = hiltViewModel()
) {
    val responseState = viewModel.response.value
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is JoinUsViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(SmallPadding)
                        .fillMaxWidth(),
                    elevation = ExtraSmallPadding,
                    backgroundColor = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(SmallPadding),
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        }
    ) {
        ResponseForm(
            modifier = Modifier
                .padding(BigPadding)
                .fillMaxWidth(),
            viewModel = viewModel
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (responseState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ResponseForm(
    modifier: Modifier = Modifier,
    viewModel: JoinUsViewModel
) {
    val state = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(
                state = state,
                enabled = true
            )
    ) {
        SingleTextField(state = viewModel.nameState, placeHolder = "Name")
        Spacer(modifier = Modifier.size(SmallPadding))
        SingleTextField(
            state = viewModel.emailState, placeHolder = "Email",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        SingleTextField(
            state = viewModel.phoneState,
            placeHolder = "Phone",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        SingleTextField(state = viewModel.branchState, placeHolder = "Branch")
        Spacer(modifier = Modifier.size(SmallPadding))
        SingleTextField(
            state = viewModel.batchState,
            placeHolder = "Batch",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        SingleTextField(
            state = viewModel.knownState,
            placeHolder = "Know Coding Language (Optional)"
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        SingleTextField(
            state = viewModel.aboutState,
            placeHolder = "About You (Optional)",
            isField = true
        )
        Spacer(modifier = Modifier.size(SmallPadding))
        Button(
            onClick = {
                viewModel.joinClick()
            },
            border = BorderStroke(
                width = SmallStroke,
                color = GenreColor
            ),
            shape = RoundedCornerShape(SmallPadding),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(ExtraSmallPadding)
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(ExtraSmallPadding)
            )
        }
    }
}

@Composable
fun SingleTextField(
    state: MutableState<String>,
    placeHolder: String,
    isField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        label = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.body2
            )
        },
        maxLines = if (!isField) 1 else 5,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        modifier = Modifier.fillMaxWidth()
    )
}