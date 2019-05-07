package stockholm.makerspace.boxterminator.terminationsessions.sessionlist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.network.Skynet
import timber.log.Timber


class ScanSessionListPresenter(private val view: ScanSessionListContract.View) : ScanSessionListContract.Presenter, KoinComponent {

    private val skynet: Skynet by inject()

    override fun getScans(token: String?) {
        token?.let {
            skynet.getClient().getSessionList("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Timber.d("Session list $it")
                        view.showScans(listOf())
                    },
                    { Timber.d("Error while getting session list ${it.message}") }
                )
        }
    }
}