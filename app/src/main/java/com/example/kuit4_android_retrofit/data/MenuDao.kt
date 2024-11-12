package com.example.kuit4_android_retrofit.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuDao {
    @Insert
    suspend fun insert(menu: MenuData)

    @Query("SELECT * FROM MenuData")
    fun getAll(): List<MenuData>
}
