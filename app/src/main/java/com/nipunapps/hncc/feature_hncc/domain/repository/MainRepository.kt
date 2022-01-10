package com.nipunapps.hncc.feature_hncc.domain.repository

import com.nipunapps.hncc.core.Resource
import com.nipunapps.hncc.feature_hncc.data.remote.dto.Bearer
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getHomeImage() : Flow<Resource<String>>

    fun getTeamInfo() : Flow<Resource<List<Bearer>>>
}