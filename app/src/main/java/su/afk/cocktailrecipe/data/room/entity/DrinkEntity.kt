package su.afk.cocktailrecipe.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DrinkEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val nameDrink: String,
    val urlDrink: String
)
