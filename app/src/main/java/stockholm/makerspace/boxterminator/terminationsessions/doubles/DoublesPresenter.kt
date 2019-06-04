package stockholm.makerspace.boxterminator.terminationsessions.doubles

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.models.DoubleBox
import stockholm.makerspace.boxterminator.network.Skynet
import stockholm.makerspace.boxterminator.utils.SkynetDatastore
import timber.log.Timber


class DoublesPresenter(private val view: DoublesContract.View) : DoublesContract.Presenter, KoinComponent {

    private val skynetDatastore: SkynetDatastore by inject()

    private val skynet: Skynet by inject()

    @SuppressLint("CheckResult")
    override fun getDoubles() {
        val token = skynetDatastore.skynetToken()
        skynet.getClient().getSessionList("Bearer $token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.d("Session list in doubles ${it.data}")
                    val allBoxes = it.data

                    val doublesList = mutableListOf<Box>()
                    allBoxes.forEach { box ->

                        val doubleBox = allBoxes.firstOrNull { otherBox ->
                            box.member_number == otherBox.member_number
                                    && box.box_label_id != otherBox.box_label_id
                        }

                        doubleBox?.let {
                            doublesList.add(it)
                        }
                    }
                    view.showDoublesList(doublesList)
                },
                { Timber.d("Error while getting box list ${it}") }
            )
    }
}