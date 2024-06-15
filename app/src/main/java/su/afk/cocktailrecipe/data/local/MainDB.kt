package su.afk.cocktailrecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import su.afk.cocktailrecipe.data.local.dao.CocktailDao
import su.afk.cocktailrecipe.data.local.entity.DrinkEntity

@Database(
    entities = [DrinkEntity::class],
    version = 1
)
abstract class MainDB : RoomDatabase() {

    abstract fun daoCocktail(): CocktailDao
}