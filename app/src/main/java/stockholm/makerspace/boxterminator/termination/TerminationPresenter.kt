package stockholm.makerspace.boxterminator.termination

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.models.QrScanResult
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.models.TerminationStatus
import stockholm.makerspace.boxterminator.network.ValidateBoxRequest
import stockholm.makerspace.boxterminator.utils.SkynetDatastore
import timber.log.Timber


class TerminationPresenter(private val view: TerminationContract.View) : TerminationContract.Presenter, KoinComponent {

    private val skynet: Skynet by inject()
    private val dataStore: SkynetDatastore by inject()

    override fun validateBox(scanResult: QrScanResult) {
        val token = dataStore.skynetToken()
        token?.let {
            val validateRequest = ValidateBoxRequest(scanResult.member_number, scanResult.unix_timestamp)
            skynet.getClient().getMember("Bearer $token", validateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                    {
                        val member = it.data
                        when (member.status) {
                            TerminationStatus.ACTIVE -> view.showActiveStatus(member)
                            TerminationStatus.EXPIRED -> view.showExpiredStatus(member)
                            TerminationStatus.TERMINATE -> view.showTerminateStatus(member)
                        }
                        Timber.d("response success ${member.name} expire date ${member.expire_date}, terimante date ${member.terminate_date}, status ${member.status}")
                    },
                    {
                        view.showError(it.message)
                        Timber.d("Error while getting member ${it.message}")
                    }
                )
        }
    }
}