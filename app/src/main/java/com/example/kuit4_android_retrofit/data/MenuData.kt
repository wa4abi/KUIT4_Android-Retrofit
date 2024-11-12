package com.example.kuit4_android_retrofit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuData(
    val menuName: String,
    val menuImg: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
