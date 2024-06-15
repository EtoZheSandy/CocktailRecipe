package su.afk.cocktailrecipe.domain.repository

import su.afk.cocktailrecipe.data.network.dto.ListDrinkResponseDto
import su.afk.cocktailrecipe.domain.model.Drink
import su.afk.cocktailrecipe.util.Resource

interface NetworkDrink {

    suspend fun getDrinkRandom(): Resource<ListDrinkResponseDto>

    suspend fun getDrinkHome(): Resource<List<Drink>>

    suspend fun getDrinkDetail(id: Int): Resource<ListDrinkResponseDto>

    suspend fun getDrinkName(name: String): Resource<List<Drink>>

    suspend fun getIngredient(name: String): Resource<ListDrinkResponseDto>
}