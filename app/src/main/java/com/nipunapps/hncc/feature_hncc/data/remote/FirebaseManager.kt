package com.nipunapps.hncc.feature_hncc.data.remote

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseManager {
    private val storageReference = FirebaseStorage.getInstance().reference

    suspend fun getHomeImage() : String{
        val child = storageReference.child("gifs/design.gif")
        return try {
            child.downloadUrl.await().toString()
        }catch (e : Exception){
            ""
        }
    }
}