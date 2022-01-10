package com.nipunapps.hncc.feature_hncc.data.remote.dto

data class Content(
    val events: List<Any>,
    val header: String,
    val messages: List<Message>
)