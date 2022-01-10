package com.nipunapps.hncc.feature_hncc.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class ImageFullViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _image = mutableStateOf("")
    val image: State<String> = _image

    private val _lock = mutableStateOf(false)
    val lock: State<Boolean> = _lock

    private val _lockVisibility = mutableStateOf(false)
    val lockVisibility: State<Boolean> = _lockVisibility
    private var job: Job? = null

    init {
        savedStateHandle.get<String>("image")?.let { img->
            val decodeImage = URLDecoder.decode(
                img, StandardCharsets.UTF_8.toString()
            )
            _image.value = decodeImage
        }
        showLock()
    }

    fun toggleLock() {
        _lock.value = !lock.value
    }

    fun showLock() {
        if(lockVisibility.value){
            _lockVisibility.value = false
            return
        }
        _lockVisibility.value = true
        job?.cancel()
        job = viewModelScope.launch {
            delay(3000L)
            _lockVisibility.value = false
        }
    }
}