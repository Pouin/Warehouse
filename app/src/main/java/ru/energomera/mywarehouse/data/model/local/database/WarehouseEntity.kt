package ru.energomera.mywarehouse.data.model.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rustrncomplect")
data class CompListEntity (
    @PrimaryKey val compNum: String,
    val trnNum: String,
    val item: String?,
    val qtyShip: Double
)

@Entity(tableName = "grnline")
data class GrnListEntity (
    @PrimaryKey val grnNum: String,
    val vendorName: String,
    val dateShip: String
)

