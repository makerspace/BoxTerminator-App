package stockholm.makerspace.boxterminator.koin

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import stockholm.makerspace.boxterminator.login.LoginContract
import stockholm.makerspace.boxterminator.login.LoginPresenter
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.utils.SkynetDatastore


val applicationModule = module(override = true) {
    factory<LoginContract.Presenter> { (view: LoginContract.View) -> LoginPresenter(view) }
    single { Skynet() }
    single { SkynetDatastore(androidContext().getSharedPreferences("Arnie", Context.MODE_PRIVATE)) }
}