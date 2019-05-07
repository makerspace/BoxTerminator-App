package stockholm.makerspace.boxterminator.termination

import stockholm.makerspace.boxterminator.models.QrScanResult
import stockholm.makerspace.boxterminator.network.Member


interface TerminationContract {

    interface Presenter {
        fun validateBox(scanResult: QrScanResult)
    }

    interface View {
        fun showActiveStatus(member: Member)
        fun showExpiredStatus(member: Member)
        fun showTerminateStatus(member: Member)
        fun showError(message: String?)
    }
}