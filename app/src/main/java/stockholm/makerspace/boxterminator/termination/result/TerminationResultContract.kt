package stockholm.makerspace.boxterminator.termination.result


interface TerminationResultContract {

    interface Presenter {
        fun nag(member_number: Int, box_label_id: Int, nagType: String)
    }

    interface View {
        fun showNagSuccess()
        fun showNagError(message: String?)
    }
}

const val NAG_TYPE_WARNING = "nag-warning"
const val NAG_TYPE_LAST_WARNING = "nag-last-warning"
const val NAG_TYPE_TERMINATED = "nag-terminated"