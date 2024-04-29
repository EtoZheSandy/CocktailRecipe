package su.afk.cocktailrecipe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.retrofit.ApiCocktail
import su.afk.cocktailrecipe.di.Constans.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
//    fun provideRepositoryDrink(api: ApiCocktail) = DrinkRepository(api)
    fun provideRepositoryDrink(api: ApiCocktail): DrinkRepository {
        return DrinkRepository(api)
    }

    @Singleton
    @Provides
    fun provideApiCocktail(): ApiCocktail {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiCocktail::class.java)
    }
}