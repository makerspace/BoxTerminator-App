package stockholm.makerspace.boxterminator.terminationsessions


interface TerminationSessionsContract {

    interface Presenter {
        fun getSessions()
    }

    interface View {
        fun loadSessionList(timestampTokenList: List<Pair<String, String>>)
    }
}