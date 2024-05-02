package su.afk.cocktailrecipe.data

import dagger.hilt.android.scopes.ActivityScoped
import su.afk.cocktailrecipe.data.models.MainCoctails
import su.afk.cocktailrecipe.data.models.ThecocktaildbModels
import su.afk.cocktailrecipe.data.retrofit.ApiCocktail
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@ActivityScoped
class DrinkRepository @Inject constructor(
    private val api: ApiCocktail
){

    suspend fun getDrinkRandom(): Resource<ThecocktaildbModels> {
        val response =  try {
            api.getRandom()
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }


    suspend fun getDrinkHome(limit: Int, offset: Int): Resource<MainCoctails> {
        val response =  try {
            val alcoholicDrinks = api.getMainDrink(name = "Alcoholic")
            val nonAlcoholicDrinks = api.getMainDrink(name = "Non_Alcoholic")
            MainCoctails(
                drinks = alcoholicDrinks.drinks + nonAlcoholicDrinks.drinks
            )
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    suspend fun getDrinkDetail(id: Int): Resource<ThecocktaildbModels> {
        val response =  try {
            api.getIdDrink(id)
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    suspend fun getDrinkName(name: String): Resource<ThecocktaildbModels> {
        val response =  try {
            api.getNameDrink(name)
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }

    suspend fun getIngridient(name: String): Resource<ThecocktaildbModels> {
        val response =  try {
            api.getIngridientDrink(name)
        } catch(e: Exception) {
            return Resource.Error(message = "Произошла неизвестная ошибка")
        }
        return Resource.Success(data = response)
    }
}
