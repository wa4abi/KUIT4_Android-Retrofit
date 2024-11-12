package com.example.kuit4_android_retrofit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MenuCategoryData::class], version = 1)
abstract class MenuCategoryDB : RoomDatabase() {
    abstract fun menuDao(): MenuCategoryDao

    companion object {
        private var instance: MenuCategoryDB? = null

        @Synchronized
        fun getInstance(context: Context): MenuCategoryDB {
            if (instance == null) {
                instance =
                    Room
                        .databaseBuilder(
                            context,
                            MenuCategoryDB::class.java,
                            "menu_database",
                        ).build()
            }

            return instance!!
        }
    }
}
