package com.nipunapps.hncc.feature_hncc.presentation.state

data class HomeImageState(
    val isLoading : Boolean =false,
    val data : String = "",
    val message : String? = null
)
