package su.afk.cocktailrecipe.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.afk.cocktailrecipe.data.ConnectivityRepository
import su.afk.cocktailrecipe.data.NetworkDrinkRepository
import su.afk.cocktailrecipe.data.network.service.ApiCocktail
import su.afk.cocktailrecipe.data.local.MainDB
import su.afk.cocktailrecipe.data.local.dao.CocktailDao
import su.afk.cocktailrecipe.di.Constants.BASE_URL
import su.afk.cocktailrecipe.domain.repository.LocalDrink
import su.afk.cocktailrecipe.domain.repository.NetworkDrink
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNetworkDrinkRepository(api: ApiCocktail): NetworkDrink {
        return NetworkDrinkRepository(api)
    }

    @Singleton
    @Provides
    fun provideLocalDrinkRepository(dao: CocktailDao): LocalDrink {
        return su.afk.cocktailrecipe.data.LocalDrinkRepository(daoCocktail = dao)
    }

    @Singleton
    @Provides
    fun provideConnectivityRepository(@ApplicationContext app: Context): ConnectivityRepository {
        return ConnectivityRepository(context = app)
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

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        context = app,
        MainDB::class.java,
        "MyDataBase.db"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: MainDB) = db.daoCocktail()
}