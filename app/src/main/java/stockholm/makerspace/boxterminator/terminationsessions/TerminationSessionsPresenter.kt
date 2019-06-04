package stockholm.makerspace.boxterminator.terminationsessions

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.utils.SkynetDatastore
import timber.log.Timber


class TerminationSessionsPresenter(private val view: TerminationSessionsContract.View) : TerminationSessionsContract.Presenter, KoinComponent {

    private val skynetDatastore : SkynetDatastore by inject()

    private val skynet : Skynet by inject()

    @SuppressLint("CheckResult")
    override fun getTerminations() {
        val token = skynetDatastore.skynetToken()
        skynet.getClient().getSessionList("Bearer $token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Timber.d("Session list $it")
                    view.showScans(it.data)
                },
                { Timber.d("Error while getting session list ${it.message}") }
            )
    }
}