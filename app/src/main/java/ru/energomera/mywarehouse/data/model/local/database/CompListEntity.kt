package ru.energomera.mywarehouse.data.model.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rustrncomplect")
class CompListEntity {
    @PrimaryKey val compNum: String,
    val trnNum: String,
    val item: String,
    val qtyShip: Double
}