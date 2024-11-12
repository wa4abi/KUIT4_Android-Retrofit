package com.example.kuit4_android_retrofit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MenuData::class], version = 1)
abstract class MenuDB : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        private var instance: MenuDB? = null

        @Synchronized
        fun getInstance(context: Context): MenuDB {
            if (instance == null) {
                instance =
                    Room
                        .databaseBuilder(
                            context,
                            MenuDB::class.java,
                            "menu_database",
                        ).build()
            }

            return instance!!
        }
    }
}
