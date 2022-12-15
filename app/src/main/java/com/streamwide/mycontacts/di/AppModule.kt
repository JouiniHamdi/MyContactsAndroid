package com.streamwide.mycontacts.di

import android.content.Context
import com.streamwide.mycontacts.data.local.AppDatabase
import com.streamwide.mycontacts.data.local.dao.UserContactDao
import com.streamwide.mycontacts.data.repository.UserContactRepositoryImpl
import com.streamwide.mycontacts.domain.repository.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideUserContactDao(appDatabase: AppDatabase): UserContactDao {
        return appDatabase.userContactDao()
    }

    @Provides
    @Singleton
    fun provideContactRepository(contactDao : UserContactDao): ContactRepository {
        return UserContactRepositoryImpl(contactDao)
    }
}