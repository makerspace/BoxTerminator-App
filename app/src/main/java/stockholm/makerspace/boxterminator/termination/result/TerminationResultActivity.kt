package stockholm.makerspace.boxterminator.termination.result

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.termination_result_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.joda.time.DateTime
import org.joda.time.Days
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.network.Member
import stockholm.makerspace.boxterminator.network.TerminationStatus

const val TERMINATION_EXTRA = "termination_extra"

class TerminationResultActivity : AppCompatActivity(), TerminationResultContract.View {

    private val presenter: TerminationResultContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.termination_result_activity)
        val terminationResult: Member = intent.extras.getParcelable(TERMINATION_EXTRA)
        when (terminationResult.status) {

            TerminationStatus.ACTIVE -> {
                terminationResultText.text = "I'LL BE BACK!"
                terminationResultImg.setImageResource(R.drawable.terminator)
                terminationDate.text = "Expires ${terminationResult.expire_date}"

            }
            TerminationStatus.EXPIRED -> {
                terminationResultText.text = "I'LL BE BACK! MAYBE.."
                terminationResultImg.setImageResource(R.drawable.terminator_back)
                val now = DateTime()
                val terminateIn = DateTime(terminationResult.terminate_date)
                val daysInBetween = Days.daysBetween(now, terminateIn).days
                terminationDate.text = "Expired ${terminationResult.expire_date} Terminate in $daysInBetween days"
                terminationResultBtn.apply {
                    setOnClickListener {
                        presenter.nag(
                            terminationResult.member_number,
                            terminationResult.box_label_id
                        )
                    }
                    visibility = View.VISIBLE
                }
            }
            TerminationStatus.TERMINATE -> {
                terminationResultText.text = "HASTA LA VISTA, BABY!"
                terminationResultImg.setImageResource(R.drawable.terminator_hand)
                terminationDate.text = "Expired ${terminationResult.expire_date}"
                terminationResultBtn.apply {
                    setOnClickListener {
                        presenter.nag(
                            terminationResult.member_number,
                            terminationResult.box_label_id
                        )
                    }
                    visibility = View.VISIBLE
                }
            }
        }
        terminationDoneBtn.setOnClickListener {
            finish()
        }
    }

    override fun showNagError(message: String?) {
        alert("Failed to nag - $message", "Skynet message") {
            okButton { }
        }.show()
    }

    override fun showNagSuccess() {
        alert("Member successfully nagged!", "Skynet message") {
            okButton { finish() }
        }.show()
    }
}