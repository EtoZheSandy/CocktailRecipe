package su.afk.cocktailrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import su.afk.cocktailrecipe.data.local.entity.DrinkEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM drinkentity WHERE id = :id")
    suspend fun checkDrink(id: Int?): DrinkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDrink(drinkEntity: DrinkEntity)

    @Delete
    suspend fun deleteDrink(drinkEntity: DrinkEntity)

    @Query("SELECT * FROM drinkentity")
    fun queryAllDrink(): Flow<List<DrinkEntity>>
}