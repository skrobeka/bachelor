package com.example.bachelorv1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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
    fun getAllLocationsOrderedByName(): Flow<List<Location>>

    @Query("SELECT * FROM location WHERE locationName LIKE '%' || :locationName || '%' ORDER BY locationName ASC")
    fun getLocationByName(locationName: String): List<Location>

    @Query("SELECT locationId FROM location WHERE locationName = :locationName LIMIT 1")
    fun getLocationIdByName(locationName: String): Int

    @Query("SELECT * FROM location WHERE locationId = :locationId")
    fun getLocationById(locationId: Int): Location

    @Query("SELECT locationName FROM location WHERE locationId = :locationId")
    fun getLocationNameById(locationId: Int): String

    @Query("SELECT COUNT (*) FROM book WHERE locationId = :locationId")
    fun getLocationBookCount(locationId: Int): Int

    //Import
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertLocations(locations: List<Location>)

}