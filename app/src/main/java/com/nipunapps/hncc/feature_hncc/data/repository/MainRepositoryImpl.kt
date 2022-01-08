package com.nipunapps.hncc.feature_hncc.data.repository

import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.data.remote.FirebaseManager
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MainRepositoryImpl(
    private val firebaseManager: FirebaseManager
) : MainRepository{
    override fun getHomeImage(): Flow<Resource<String>> = flow{
        emit(Resource.Loading<String>())
        try {
            emit(Resource.Success<String>(data = firebaseManager.getHomeImage()))
        }catch (e : Exception){
            emit(Resource.Error<String>(message = "Something Went wrong"))
        }
    }
}