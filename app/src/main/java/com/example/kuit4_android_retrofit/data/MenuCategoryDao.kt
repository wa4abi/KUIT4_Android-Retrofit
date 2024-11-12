package com.example.kuit4_android_retrofit.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuCategoryDao {
    @Insert
    suspend fun insert(menu: MenuCategoryData)

    @Query("SELECT * FROM MenuCategoryData")
    fun getAll(): List<MenuCategoryData>
}
