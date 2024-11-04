package com.example.bachelorv1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    //Queries
    @Query("SELECT * FROM location ORDER BY locationName ASC")
    fun getAllLocationsOrderedByName(): LiveData<List<Location>>

    @Query("SELECT * FROM location ORDER BY locationBookCount ASC")
    fun getAllLocationsOrderedByBookCount(): LiveData<List<Location>>

    @Query("SELECT * FROM location WHERE locationName = :locationName ORDER BY locationName ASC")
    fun getLocationByName(locationName: String): LiveData<Location>

    @Query("SELECT * FROM location WHERE parentLocationId = :parentLocationId ORDER BY locationName ASC")
    fun getLocationsByParent(parentLocationId: Int): LiveData<List<Location>>

}