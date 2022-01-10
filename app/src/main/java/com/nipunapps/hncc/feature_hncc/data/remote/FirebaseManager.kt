package com.nipunapps.hncc.feature_hncc.data.remote

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer
import com.nipunapps.hncc.feature_hncc.data.remote.dto.TeamDto
import kotlinx.coroutines.tasks.await

class FirebaseManager {
    private val storageReference = FirebaseStorage.getInstance().reference

    private val remoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }
    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
        initialiseRemoteConfig()
    }

    private fun initialiseRemoteConfig(){
        remoteConfig.fetchAndActivate()
    }

    fun getTeamInfo() : List<Bearer> {
        return try {
            val gson = Gson()
            val team = remoteConfig.getString("Team")
            val teamDto = gson.fromJson(team,TeamDto::class.java)
            if(teamDto.error) emptyList()
            else teamDto.body
        }catch (e : Exception){
            emptyList()
        }
    }

    suspend fun getHomeImage() : String{
        val child = storageReference.child("gifs/design.gif")
        return try {
            child.downloadUrl.await().toString()
        }catch (e : Exception){
            ""
        }
    }
}