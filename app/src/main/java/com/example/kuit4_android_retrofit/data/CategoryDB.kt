package com.example.kuit4_android_retrofit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryData::class], version = 1)
abstract class CategoryDB : RoomDatabase() {
    abstract fun menuDao(): CategoryDao

    companion object {
        private var instance: CategoryDB? = null

        @Synchronized
        fun getInstance(context: Context): CategoryDB {
            if (instance == null) {
                instance =
                    Room
                        .databaseBuilder(
                            context,
                            CategoryDB::class.java,
                            "category_database",
                        ).build()
            }

            return instance!!
        }
    }
}
