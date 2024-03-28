package com.application.travel_app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel")
data class Travel(
    @ColumnInfo(name = "placeName")
    var placeName: String,

    @ColumnInfo(name = "location")
    var location: String,

    @ColumnInfo(name = "review")
    var review: String,

    @ColumnInfo(name = "price")
    var price: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)