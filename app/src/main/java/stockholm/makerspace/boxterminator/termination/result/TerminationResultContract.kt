package stockholm.makerspace.boxterminator.termination.result

import org.joda.time.DateTime
import stockholm.makerspace.boxterminator.models.Member


interface TerminationResultContract {

    interface Presenter {
        fun nag(member: Member, nagType: String)
    }

    interface View {
        fun showNagSuccess()
        fun showNagError(message: String?)
    }
}

const val NAG_TYPE_WARNING = "nag-warning"
const val NAG_TYPE_LAST_WARNING = "nag-last-warning"
const val NAG_TYPE_TERMINATED = "nag-terminated"