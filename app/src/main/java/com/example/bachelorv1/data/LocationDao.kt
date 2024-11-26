package com.example.bachelorv1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    @Update
    fun updateLocation(location: Location)

    //Queries
    @Query("SELECT * FROM location ORDER BY locationName ASC")
    fun getAllLocationsOrderedByName(): List<Location>

    @Query("SELECT * FROM location ORDER BY locationBookCount ASC")
    fun getAllLocationsOrderedByBookCount(): List<Location>

    @Query("SELECT * FROM location WHERE locationName LIKE '%' || :locationName || '%' ORDER BY locationName ASC")
    fun getLocationByName(locationName: String): List<Location>

    @Query("SELECT locationId FROM location WHERE locationName = :locationName LIMIT 1")
    fun getLocationIdByName(locationName: String): Int

    @Query("SELECT locationName FROM location WHERE locationId = :locationId LIMIT 1")
    fun getLocationNameById(locationId: Int): String

    @Query("SELECT locationBookCount FROM location WHERE locationId = :locationId LIMIT 1")
    fun getLocationBookCountById(locationId: Int): Int
}