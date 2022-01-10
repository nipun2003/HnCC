package com.nipunapps.hncc.feature_hncc.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.data.remote.dto.About
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import com.nipunapps.hncc.feature_hncc.presentation.state.AboutState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    private val _aboutState = mutableStateOf(AboutState())
    val aboutState: State<AboutState> = _aboutState

    init {
        getAbout()
    }

    private fun getAbout() {
        mainRepository.getAboutInfo().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _aboutState.value = AboutState(
                        data = result.data ?: About()
                    )
                }
                is Resource.Error -> {
                    _aboutState.value = AboutState(
                        message = result.message
                    )
                }
                is Resource.Loading -> {
                    _aboutState.value = AboutState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}