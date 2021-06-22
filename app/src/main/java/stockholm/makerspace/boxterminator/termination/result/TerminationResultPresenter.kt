package stockholm.makerspace.boxterminator.termination.result

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.Days
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.models.Member
import stockholm.makerspace.boxterminator.network.NagRequest
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.utils.SkynetDatastore
import timber.log.Timber

class TerminationResultPresenter(private val view: TerminationResultContract.View) :
    TerminationResultContract.Presenter, KoinComponent {

    private val skynet: Skynet by inject()
    private val skynetDatastore: SkynetDatastore by inject()

    override fun nag(member: Member, nagType: String) {
        val skynetToken = skynetDatastore.skynetToken()
        var okToSend:Boolean = true
        val now = DateTime()
        if (member.last_nag_at != null) {
            val daysInBetween = Days.daysBetween(DateTime(member.last_nag_at), now).days
            if (daysInBetween < 1) {
                okToSend = false
            }
        }

        if(okToSend) {
            skynetToken?.let { token ->
                val nagRequest = NagRequest(member.member_number, member.box_label_id, nagType)
                member.last_nag_at = now.toString()
                skynet.getClient().nag("Bearer $token", nagRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Timber.d("Success")
                            view.showNagSuccess(nagType)
                        },
                        {
                            Timber.d("Error ${it.message}")
                            view.showNagError(it.message)
                        }
                    )
            }
        } else {
            view.showNagError("Error, member already nagged recently")
        }
    }
}