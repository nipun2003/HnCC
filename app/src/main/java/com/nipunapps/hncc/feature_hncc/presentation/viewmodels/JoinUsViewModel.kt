package com.nipunapps.hncc.feature_hncc.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.domain.model.JoinModel
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import com.nipunapps.hncc.feature_hncc.presentation.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinUsViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {
    private val _response = mutableStateOf(
        ResponseState()
    )
    val response: State<ResponseState> = _response

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val nameState =
        mutableStateOf(
            ""
        )

    val emailState =
        mutableStateOf(
            ""
        )

    val phoneState =
        mutableStateOf(
            ""
        )

    val branchState =
        mutableStateOf(
            ""
        )

    val batchState =
        mutableStateOf(
            ""
        )

    val knownState =
        mutableStateOf(
            ""
        )

    val aboutState =
        mutableStateOf(
            ""
        )

    fun joinClick() {
        if (
            nameState.value == "" ||
            emailState.value == "" ||
            phoneState.value == "" ||
            branchState.value == "" ||
            batchState.value == ""
        ) {
            viewModelScope.launch {
                _eventFlow.emit(
                    UIEvent.ShowSnackbar(
                        "Required fields should not empty"
                    )
                )
            }
            return
        }
        val joinModel = JoinModel(
            name = nameState.value,
            branch = branchState.value,
            batch = batchState.value.toInt(),
            email = emailState.value,
            phone = phoneState.value,
            known = knownState.value,
            about = aboutState.value
        )
        addResponse(joinModel)
        reset()
    }

    private fun reset() {
        nameState.value = ""
        branchState.value = ""
        emailState.value = ""
        batchState.value = ""
        phoneState.value = ""
        knownState.value = ""
        aboutState.value = ""
    }

    private fun addResponse(join: JoinModel) {
        mainRepository.addResponse(join).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _response.value = ResponseState(
                        success = "Response Added Successfully, We will contact you soon...."
                    )
                    _eventFlow.emit(
                        UIEvent.ShowSnackbar(
                            "Response Added Successfully"
                        )
                    )
                }
                is Resource.Error -> {
                    _response.value = ResponseState(
                        error = result.message
                    )
                    _eventFlow.emit(
                        UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        )
                    )
                }
                is Resource.Loading -> {
                    _response.value = ResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}