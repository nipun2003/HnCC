package com.nipunapps.hncc.feature_hncc.domain.model

data class JoinModel(
    val name : String,
    val branch : String,
    val batch : Int,
    val email : String,
    val phone : String,
    val known : String,
    val about : String
)
