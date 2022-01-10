package com.nipunapps.hncc.feature_hncc.domain.repository

import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.data.remote.dto.About
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer
import com.nipunapps.hncc.feature_hncc.domain.model.JoinModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getHomeImage() : Flow<Resource<String>>

    fun getTeamInfo() : Flow<Resource<List<Bearer>>>

    fun getAboutInfo() : Flow<Resource<About>>

    fun addResponse(join : JoinModel) : Flow<Resource<Boolean>>
}