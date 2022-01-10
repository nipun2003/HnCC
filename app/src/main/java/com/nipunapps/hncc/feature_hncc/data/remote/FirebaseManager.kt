package com.nipunapps.hncc.feature_hncc.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.nipunapps.hncc.feature_hncc.data.remote.dto.About
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer
import com.nipunapps.hncc.feature_hncc.data.remote.dto.TeamDto
import com.nipunapps.hncc.feature_hncc.domain.model.JoinModel
import kotlinx.coroutines.tasks.await

class FirebaseManager {
    private val storageReference = FirebaseStorage.getInstance().reference
    private val fireStore = FirebaseFirestore.getInstance()
    private val responseRef = fireStore.collection("Contacts")

    private val remoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 600
    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
    }

    suspend fun addResponse(join: JoinModel): Boolean {
        return try {
            responseRef.document(join.name).set(join).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getTeamInfo(): List<Bearer> {
        return try {
            val gson = Gson()
            val team = remoteConfig.getString("Team")
            val teamDto = gson.fromJson(team, TeamDto::class.java)
            if (teamDto.error) emptyList()
            else teamDto.body
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getAboutInfo(): About? {
        return try {
            val gson = Gson()
            val about = remoteConfig.getString("About")
            gson.fromJson(about, About::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getHomeImage(): String {
        val child = storageReference.child("gifs/design.gif")
        return try {
            child.downloadUrl.await().toString()
        } catch (e: Exception) {
            ""
        }
    }
}