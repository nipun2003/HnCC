package com.nipunapps.hncc.feature_hncc.presentation.state

data class ResponseState(
    val isLoading : Boolean = false,
    val success : String? = null,
    val error : String? = null
)
