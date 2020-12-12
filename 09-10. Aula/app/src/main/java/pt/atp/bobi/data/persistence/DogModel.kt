package pt.atp.bobi.data.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dog")
data class DogModel(
    @ColumnInfo(name = "bred_for")
    val bredFor: String,
    @ColumnInfo(name = "bred_group")
    val bredGroup: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "life_span")
    val lifeSpan: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "origin")
    val origin: String?,
    @ColumnInfo(name = "temperament")
    val temperament: String,
)