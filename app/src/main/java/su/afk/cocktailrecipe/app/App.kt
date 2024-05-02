package su.afk.cocktailrecipe.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import su.afk.cocktailrecipe.data.room.MainDB
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

//    val database by lazy { MainDB.createDataBase(this) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}