package com.nipunapps.hncc.feature_hncc.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import com.nipunapps.hncc.feature_hncc.presentation.state.BearerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _teamState = mutableStateOf(BearerState())
    val teamState : State<BearerState> = _teamState

    init {
        getTeamInfo()
    }

    private fun getTeamInfo(){
        mainRepository.getTeamInfo().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _teamState.value = BearerState(
                        data = result.data?: emptyList()
                    )
                }
                is Resource.Error ->{
                    _teamState.value = BearerState(
                        message = result.message
                    )
                }
                is Resource.Loading ->{
                    _teamState.value = BearerState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}