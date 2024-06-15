package su.afk.cocktailrecipe.domain.repository

import su.afk.cocktailrecipe.data.network.dto.ListDrinkResponseDto
import su.afk.cocktailrecipe.util.Resource

interface NetworkDrink {

    suspend fun getDrinkRandom(): Resource<ListDrinkResponseDto>

    suspend fun getDrinkHome(): Resource<ListDrinkResponseDto>

    suspend fun getDrinkDetail(id: Int): Resource<ListDrinkResponseDto>

    suspend fun getDrinkName(name: String): Resource<ListDrinkResponseDto>

    suspend fun getIngredient(name: String): Resource<ListDrinkResponseDto>
}