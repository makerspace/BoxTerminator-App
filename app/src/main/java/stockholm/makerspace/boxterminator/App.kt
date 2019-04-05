package stockholm.makerspace.boxterminator

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import stockholm.makerspace.boxterminator.koin.applicationModule


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(applicationModule))
        }
    }
}