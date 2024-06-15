package su.afk.cocktailrecipe.data

import su.afk.cocktailrecipe.data.network.dto.ListDrinkResponseDto
import su.afk.cocktailrecipe.data.network.service.ApiCocktail
import su.afk.cocktailrecipe.domain.repository.NetworkDrink
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

class NetworkDrinkRepository @Inject constructor(
    private val api: ApiCocktail
): NetworkDrink {

    override suspend fun getDrinkRandom(): Resource<ListDrinkResponseDto> {
        val response =  try {
            api.getRandomDrink()
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    override suspend fun getDrinkHome(limit: Int, offset: Int): Resource<ListDrinkResponseDto> {
        val response =  try {
            val alcoholicDrinks = api.getMainDrink(name = "Alcoholic")
            val nonAlcoholicDrinks = api.getMainDrink(name = "Non_Alcoholic")
            ListDrinkResponseDto(
                drinks = alcoholicDrinks.drinks + nonAlcoholicDrinks.drinks
            )
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    override suspend fun getDrinkDetail(id: Int): Resource<ListDrinkResponseDto> {
        val response =  try {
            api.getIdDrinkDetail(id)
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    override suspend fun getDrinkName(name: String): Resource<ListDrinkResponseDto> {
        val response =  try {
            api.getNameDrinkDetail(name)
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    override suspend fun getIngredient(name: String): Resource<ListDrinkResponseDto> {
        val response =  try {
            api.getIngredientDrink(name)
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }
}
