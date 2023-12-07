package ru.betel.app.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.betel.app.di.appModule
import ru.betel.app.di.daoModules
import ru.betel.app.di.dataModule
import ru.betel.app.di.databaseModule
import ru.betel.app.di.domainModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, dataModule, domainModule, databaseModule, daoModules)
        }
    }
}
