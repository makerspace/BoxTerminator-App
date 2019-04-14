package stockholm.makerspace.boxterminator.koin

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import stockholm.makerspace.boxterminator.login.LoginContract
import stockholm.makerspace.boxterminator.login.LoginPresenter
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.termination.TerminationContract
import stockholm.makerspace.boxterminator.termination.TerminationPresenter
import stockholm.makerspace.boxterminator.termination.result.TerminationResultContract
import stockholm.makerspace.boxterminator.termination.result.TerminationResultPresenter
import stockholm.makerspace.boxterminator.utils.SkynetDatastore


val applicationModule = module(override = true) {
    factory<LoginContract.Presenter> { (view: LoginContract.View) -> LoginPresenter(view) }
    factory<TerminationContract.Presenter> { (view: TerminationContract.View) -> TerminationPresenter(view) }
    factory<TerminationResultContract.Presenter> { (view: TerminationResultContract.View) -> TerminationResultPresenter(view) }
    single { Skynet() }
    single { SkynetDatastore(androidContext().getSharedPreferences("Arnie", Context.MODE_PRIVATE)) }
}