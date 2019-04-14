package stockholm.makerspace.boxterminator.termination.result


interface TerminationResultContract {

    interface Presenter {
        fun nag(member_number: Int, box_label_id: Int)
    }

    interface View {
        fun showNagSuccess()
        fun showNagError(message: String?)
    }
}