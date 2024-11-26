package com.example.bachelorv1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val locationId: Int = 0,
    val locationName: String,
    val locationBookCount: Int = 0,
)
