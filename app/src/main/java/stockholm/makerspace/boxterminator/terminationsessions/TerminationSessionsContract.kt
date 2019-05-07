package stockholm.makerspace.boxterminator.terminationsessions

import stockholm.makerspace.boxterminator.models.Box


interface TerminationSessionsContract {

    interface Presenter {
        fun getTerminations()
    }

    interface View {
        fun showScans(scans: List<Box>)
    }
}