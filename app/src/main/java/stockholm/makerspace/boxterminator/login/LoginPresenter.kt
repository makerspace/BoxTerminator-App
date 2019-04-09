package stockholm.makerspace.boxterminator.login

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.network.LoginRequest
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.utils.SkynetDatastore


class LoginPresenter(private var view: LoginContract.View) : LoginContract.Presenter, KoinComponent {

    private val skynet: Skynet by inject()
    private val datastore: SkynetDatastore by inject()

    @SuppressLint("CheckResult")
    override fun login(username: String, pass: String) {
        val loginRequest = LoginRequest(username = username, password = pass)
        skynet.getClient().login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    datastore.saveSkynetToken(it.access_token)
                    datastore.saveTokenExpiryDate(it.expires)
                    view.allowAccess()
                },
                { view.showError(it.message) }
            )
    }
}