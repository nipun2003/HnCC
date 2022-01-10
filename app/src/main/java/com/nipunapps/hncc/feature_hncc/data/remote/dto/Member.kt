package com.nipunapps.hncc.feature_hncc.data.remote.dto

data class Member(
    val email: String,
    val github: String,
    val image: String,
    val linkedin: String,
    val name: String,
    val position: String
){
    fun isSociallyInvisible() : Boolean{
        return (email == "") && (github == "") && (linkedin == "")
    }
}