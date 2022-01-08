package com.nipunapps.hncc.feature_hncc

import com.nipunapps.hncc.feature_hncc.data.remote.FirebaseManager
import com.nipunapps.hncc.feature_hncc.data.repository.MainRepositoryImpl
import com.nipunapps.hncc.feature_hncc.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainRepository():MainRepository = MainRepositoryImpl(FirebaseManager())
}