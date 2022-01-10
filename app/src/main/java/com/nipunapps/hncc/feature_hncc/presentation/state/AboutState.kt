package com.nipunapps.hncc.feature_hncc.presentation.state

import com.nipunapps.hncc.feature_hncc.data.remote.dto.About
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer

data class AboutState(
    val isLoading : Boolean = false,
    val data : About = About(),
    val message : String? = null
)
