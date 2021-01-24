package com.jerryhanks.farimoneytest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jerryhanks.farimoneytest.data.db.converters.DateConverter
import com.jerryhanks.farimoneytest.data.db.daos.UserDao
import com.jerryhanks.farimoneytest.data.db.entities.UserEntity

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "app.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, useInMemory: Boolean = false): AppDatabase {
            val dbBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                    .allowMainThreadQueries()
            } else {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
            }


            //This feature caused DAO test to fail so it is not used in testing
            return synchronized(this) {
                INSTANCE ?: dbBuilder.build()
                    .also {
                        INSTANCE = it
                    }
            }

        }
    }
}