package com.nipunapps.hncc.feature_hncc.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.data.remote.FirebaseManager
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import com.nipunapps.hncc.feature_hncc.presentation.state.HomeImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _url = mutableStateOf(HomeImageState())
    val url: State<HomeImageState> = _url

    init {
        getHomeImage()
    }

    private fun getHomeImage(){
        mainRepository.getHomeImage().onEach { result->
            when(result){
                is Resource.Success -> {
                    _url.value = HomeImageState(
                        data = result.data?:""
                    )
                }
                is Resource.Error ->{
                    _url.value = HomeImageState(
                        message = result.message
                    )
                }
                is Resource.Loading ->{
                    _url.value = HomeImageState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}