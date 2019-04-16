package stockholm.makerspace.boxterminator.terminationsessions.sessionlist


interface ScanSessionListContract {

    interface Presenter {
        fun getScans(token: String?)

    }

    interface View {

    }
}