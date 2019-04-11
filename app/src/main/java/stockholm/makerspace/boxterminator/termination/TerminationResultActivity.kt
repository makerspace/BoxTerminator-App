package stockholm.makerspace.boxterminator.termination

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.termination_result_activity.*
import org.joda.time.DateTime
import org.joda.time.Days
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.network.Member
import stockholm.makerspace.boxterminator.network.TerminationStatus

const val TERMINATION_EXTRA = "termination_extra"

class TerminationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.termination_result_activity)
        val terminationResult: Member = intent.extras.getParcelable(TERMINATION_EXTRA)
        when (terminationResult.status) {

            TerminationStatus.ACTIVE -> {
                terminationResultText.text = "I'LL BE BACK!"
                terminationResultImg.setImageResource(R.drawable.terminator)
                terminationDate.text = terminationResult.expire_date

            }
            TerminationStatus.EXPIRED -> {
                terminationResultText.text = "I'LL BE BACK! MAYBE!"
                terminationResultImg.setImageResource(R.drawable.terminator_hand)
                val now = DateTime()
                val terminateIn = DateTime(terminationResult.terminate_date)
                val daysInBetween = Days.daysBetween(now, terminateIn).days
                terminationDate.text = "${terminationResult.expire_date} Terminate in $daysInBetween days"

            }
            TerminationStatus.TERMINATE -> {
                terminationResultText.text = "HASTA LA VISTA, BABY!"
                terminationResultImg.setImageResource(R.drawable.terminator_hand)

            }
        }
    }
}