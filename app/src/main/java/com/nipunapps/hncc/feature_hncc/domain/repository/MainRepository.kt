package com.nipunapps.hncc.feature_hncc.domain.repository

import com.nipunapps.hncc.core.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getHomeImage() : Flow<Resource<String>>
}