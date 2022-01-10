package com.nipunapps.hncc.feature_hncc.data.remote.dto

data class About(
    val content: List<Content> = emptyList(),
    val header: String = "",
    val header_message: String = ""
)