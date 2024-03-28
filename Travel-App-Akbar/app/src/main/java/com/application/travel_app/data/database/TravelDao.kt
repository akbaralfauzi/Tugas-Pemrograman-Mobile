package com.application.travel_app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TravelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTravel(travel: Travel)

    @Query("SELECT * FROM travel WHERE id = :id")
    fun getTravel(id: Int): Travel

    @Query("SELECT * FROM travel")
    fun getAllTravels(): List<Travel>

    @Delete
    fun deleteTravel(travel: Travel)

    @Query("UPDATE travel SET placeName = :placeName, location = :location, review = :review, price = :price WHERE id = :id")
    fun updateTravel(id: Int, placeName: String, location: String, review: String, price: String)
}