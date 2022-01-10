package com.nipunapps.hncc.feature_hncc.data.repository

import android.util.Log
import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.data.remote.FirebaseManager
import com.nipunapps.hncc.feature_hncc.data.remote.dto.About
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer
import com.nipunapps.hncc.feature_hncc.domain.model.JoinModel
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MainRepositoryImpl(
    private val firebaseManager: FirebaseManager
) : MainRepository {
    override fun getHomeImage(): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            emit(Resource.Success<String>(data = firebaseManager.getHomeImage()))
        } catch (e: Exception) {
            emit(Resource.Error<String>(message = "Something Went wrong"))
        }
    }

    override fun getTeamInfo(): Flow<Resource<List<Bearer>>> = flow {
        emit(Resource.Loading<List<Bearer>>())
        try {
            val team = firebaseManager.getTeamInfo()
            emit(Resource.Success<List<Bearer>>(data = team))
        } catch (e: Exception) {
            Log.e("Team", e.message.toString())
            emit(Resource.Error<List<Bearer>>(message = "Something Went wrong"))
        }
    }

    override fun getAboutInfo(): Flow<Resource<About>> = flow {
        emit(Resource.Loading<About>())
        try {
            emit(
                Resource.Success<About>(
                    data = firebaseManager.getAboutInfo() ?: About()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error<About>(message = "Something Went wrong"))
        }
    }

    override fun addResponse(join: JoinModel): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading<Boolean>())
        try {
            if(firebaseManager.addResponse(join)){
                emit(Resource.Success<Boolean>(data = true))
            }else{
                emit(Resource.Error<Boolean>(message = "Something Went Wrong"))
            }
        }catch (e : Exception){
            emit(Resource.Error<Boolean>(message = "Something Went wrong"))
        }
    }
}