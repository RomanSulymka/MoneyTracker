package com.rsuly.expansetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rsuly.expansetracker.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao

    companion object {
        @Volatile
        private var instance : AppDatabase? = null
        private var LOCK = Any()

        //check for db instance if not get it or else we need to create new database instance
        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {

            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "transaction.db"
        ).build()
    }
}