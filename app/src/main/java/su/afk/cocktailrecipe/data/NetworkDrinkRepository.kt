package su.afk.cocktailrecipe.data

import su.afk.cocktailrecipe.data.mappers.toDrink
import su.afk.cocktailrecipe.data.mappers.toDrinkDetail
import su.afk.cocktailrecipe.data.network.dto.ListDrinkResponseDto
import su.afk.cocktailrecipe.data.network.service.ApiCocktail
import su.afk.cocktailrecipe.domain.model.Drink
import su.afk.cocktailrecipe.domain.model.DrinkDetail
import su.afk.cocktailrecipe.domain.repository.NetworkDrink
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeListViewModel.Companion.ERROR_CODE_102
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

class NetworkDrinkRepository @Inject constructor(
    private val api: ApiCocktail
): NetworkDrink {

    override suspend fun getDrinkRandom(): Resource<ListDrinkResponseDto> {
        val response =  try {
            api.getRandomDrink()
        } catch(e: Exception) {
            return Resource.Error(message = ERROR_CODE_102.toString())
        }
        return Resource.Success(data = response)
    }

    override suspend fun getDrinkHome(): Resource<List<Drink>> {
        val response =  try {
            val alcoholicDrinks = api.getMainDrink(name = "Alcoholic")
            val nonAlcoholicDrinks = api.getMainDrink(name = "Non_Alcoholic")
            val combinedDrinks = ListDrinkResponseDto(
                drinks = alcoholicDrinks.drinks + nonAlcoholicDrinks.drinks
            )

            // Map the response DTOs to domain models
            combinedDrinks.drinks.map { it.toDrink() }
        } catch(e: Exception) {
            return Resource.Error(message = ERROR_CODE_102.toString())
        }

        return Resource.Success(data = response)
    }

    override suspend fun getDrinkDetail(id: Int): Resource<DrinkDetail> {
        val response =  try {
            api.getIdDrinkDetail(id)
                .drinks.first()
                .toDrinkDetail()
        } catch(e: Exception) {
            return Resource.Error(message = ERROR_CODE_102.toString())
        }
        return Resource.Success(data = response)
    }

    override suspend fun getDrinkName(name: String): Resource<List<Drink>> {
        val response =  try {
            api.getNameDrinkDetail(name)
                .drinks?.let {
                    it.map {
                        it.toDrink()
                    }
                } ?: emptyList()
        } catch(e: Exception) {
            return Resource.Error(message = ERROR_CODE_102.toString())
        }
        return Resource.Success(data = response)
    }

    override suspend fun getIngredient(name: String): Resource<ListDrinkResponseDto> {
        val response =  try {
            api.getIngredientDrink(name)
        } catch(e: Exception) {
            return Resource.Error(message = ERROR_CODE_102.toString())
        }
        return Resource.Success(data = response)
    }
}
