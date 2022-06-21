package com.rsuly.expansetracker.di

import android.content.Context
import androidx.room.Room
import com.rsuly.expansetracker.db.AppDatabase
import com.rsuly.expansetracker.db.datastore.UIModeIml
import com.rsuly.expansetracker.db.datastore.UiModeDatastore
import com.rsuly.expansetracker.service.ExportCsvService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): UIModeIml {
        return UiModeDatastore(context)
    }

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "transaction.db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideExportCSV(@ApplicationContext context: Context): ExportCsvService {
        return ExportCsvService(appContext = context)
    }
}