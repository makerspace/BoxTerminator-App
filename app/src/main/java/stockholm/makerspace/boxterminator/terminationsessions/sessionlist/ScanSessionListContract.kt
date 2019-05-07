package stockholm.makerspace.boxterminator.terminationsessions.sessionlist

import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.models.Member


interface ScanSessionListContract {

    interface Presenter {
        fun getScans(token: String?)

    }

    interface View {
        fun showScans(terminations: List<Box>)
    }
}