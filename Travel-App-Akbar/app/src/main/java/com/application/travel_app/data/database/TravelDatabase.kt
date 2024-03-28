package com.application.travel_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Travel::class],
    version = 1
)
abstract class TravelDatabase : RoomDatabase() {

    abstract fun travelsDao(): TravelDao

    companion object {
        @Volatile
        private var INSTANCE: TravelDatabase? = null

        fun getInstance(context: Context): TravelDatabase {
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelDatabase::class.java,
                    "travel_db"
                ).build()
                INSTANCE = instance
                instance
            }
            return INSTANCE as TravelDatabase
        }
    }
}