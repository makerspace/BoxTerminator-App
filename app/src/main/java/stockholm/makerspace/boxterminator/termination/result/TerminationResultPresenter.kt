package stockholm.makerspace.boxterminator.termination.result

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.network.NagRequest
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.utils.SkynetDatastore
import timber.log.Timber


class TerminationResultPresenter(private val view: TerminationResultContract.View) :
    TerminationResultContract.Presenter, KoinComponent {

    private val skynet: Skynet by inject()
    private val skynetDatastore: SkynetDatastore by inject()

    override fun nag(member_number: Int, box_label_id: Int) {
        val skynetToken = skynetDatastore.skynetToken()
        skynetToken?.let { token ->
            val nagRequest = NagRequest(member_number, box_label_id)
            skynet.getClient().nag("Bearer $token", nagRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Timber.d("Success")
                        view.showNagSuccess()
                    },
                    {
                        Timber.d("Error ${it.message}")
                        view.showNagError(it.message)
                    }
                )
        }
    }
}