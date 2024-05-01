package su.afk.cocktailrecipe.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import su.afk.cocktailrecipe.data.room.dao.CocktailDao
import su.afk.cocktailrecipe.data.room.entity.DrinkEntity


@Database(
    entities = [DrinkEntity::class],
    version = 1
)
abstract class MainDB : RoomDatabase() {

    abstract fun daoCocktail(): CocktailDao
//    // CO - синглтон, при обращение к нему он вернет MainDB либо создаст ее
//    companion object{
//        fun createDataBase(context: Context): MainDB {
//            return Room.databaseBuilder(
//                context = context,
//                MainDB::class.java,
//                name = "MyDataBase.db"
//            ).build()
//        }
//    }
}