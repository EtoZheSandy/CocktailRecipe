package su.afk.cocktailrecipe.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import su.afk.cocktailrecipe.data.room.entity.DrinkEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM drinkentity WHERE id = :id")
    suspend fun checkItem(id: Int?): DrinkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(drinkEntity: DrinkEntity)

    @Delete
    suspend fun deleteItem(drinkEntity: DrinkEntity)

}
//    @Delete
//    suspend fun deleteItem(notepad: Notepad)
//
//    @Update
//    suspend fun updateItem(notepad: Notepad)
//
//    @Query("SELECT * FROM notepad WHERE id = :id")
//    suspend fun queryItem(id: Long): Notepad
//
////    @Query("SELECT * FROM notepad")
////    suspend fun queryAll(): List<Notepad>
//
//    @Query("SELECT * FROM notepad")
//    fun queryAll(): Flow<List<Notepad>>
    //flow сам следит за записями и запускается при изменение а еще он state одновременно
