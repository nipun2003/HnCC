package com.nipunapps.hncc.feature_hncc.presentation.state

import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer

data class BearerState(
    val isLoading : Boolean = false,
    val data : List<Bearer> = emptyList(),
    val message : String? = null
)
