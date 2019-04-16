package stockholm.makerspace.boxterminator.terminationsessions

import org.koin.core.KoinComponent
import org.koin.core.inject
import stockholm.makerspace.boxterminator.utils.SkynetDatastore


class TerminationSessionsPresenter(private val view: TerminationSessionsContract.View) : TerminationSessionsContract.Presenter, KoinComponent {

    private val skynetDatastore : SkynetDatastore by inject()

    override fun getSessions() {
        val timestamps = skynetDatastore.terminationSessions()
        val timestampTokenPairList = timestamps.map {
            val timestampToken = it.split(";")
            val timestampTokenPair = Pair(timestampToken[0], timestampToken[1])
            timestampTokenPair }
        view.loadSessionList(timestampTokenPairList)
    }
}