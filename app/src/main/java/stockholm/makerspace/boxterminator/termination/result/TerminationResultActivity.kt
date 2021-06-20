package stockholm.makerspace.boxterminator.termination.result

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.termination_result_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.image
import org.jetbrains.anko.okButton
import org.joda.time.DateTime
import org.joda.time.Days
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.models.Member
import stockholm.makerspace.boxterminator.models.TerminationStatus

const val TERMINATION_EXTRA = "termination_extra"

class TerminationResultActivity : AppCompatActivity(), TerminationResultContract.View {

    private val presenter: TerminationResultContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.termination_result_activity)
        val member: Member = intent.extras.getParcelable(TERMINATION_EXTRA)
        member_name.text = member.name
        member_number.text = "#" + member.member_number.toString()
        val now = DateTime()

        when (member.status) {
            TerminationStatus.ACTIVE -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_tick)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipValid, null)

                warning_button.backgroundTintList = resources.getColorStateList(R.color.memberBoxMembershipValidSecondary, null)
                warning_button.visibility = View.INVISIBLE

                member_last_nag.visibility = View.INVISIBLE

                terminate_button.visibility = View.GONE

                member_remaining_text.text = "expires ${member.expire_date}"
            }
            TerminationStatus.EXPIRED -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_cross)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipInvalid, null)

                warning_button.backgroundTintList= resources.getColorStateList(R.color.memberBoxMembershipInvalidSecondary, null)
                warning_button.visibility = View.VISIBLE
                warning_button_text.text = "Nag"

                terminate_button.visibility = View.GONE
                member_last_nag.visibility = View.VISIBLE

                val expireDate = DateTime(member.expire_date)
                val daysInBetween = Days.daysBetween(expireDate, now).days
                member_remaining_text.text = "${daysInBetween} days since expiration"

                warning_button.apply {
                    setOnClickListener {
                        presenter.nag(
                            member.member_number,
                            member.box_label_id,
                            NAG_TYPE_WARNING
                        )
                    }
                    visibility = View.VISIBLE
                }
            }
            TerminationStatus.TERMINATE -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_cross)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipVeryOld, null)

                member_last_nag.visibility = View.VISIBLE

                warning_button.backgroundTintList = resources.getColorStateList(R.color.memberBoxMembershipVeryOld, null)
                warning_button_text.text = "Final Warning"

                val expireDate = DateTime(member.expire_date)
                val daysInBetween = Days.daysBetween(expireDate, now).days
                member_remaining_text.text = "${daysInBetween} days since expiration"

                warning_button.apply {
                    setOnClickListener {
                        var nagType = NAG_TYPE_LAST_WARNING
                        presenter.nag(
                            member.member_number,
                            member.box_label_id,
                            nagType
                        )
                    }
                    visibility = View.VISIBLE
                }
                terminate_button.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        var nagType = NAG_TYPE_TERMINATED
                        presenter.nag(
                            member.member_number,
                            member.box_label_id,
                            nagType
                        )
                    }
                }
            }
        }

        if (member.last_nag_at != null) {
            val lastNagDate = DateTime(member.last_nag_at)
            val daysSinceNag = Days.daysBetween(lastNagDate, now).days
            if (daysSinceNag == 0) {
                member_last_nag.text = "Last nagged earlier today"
            } else if (daysSinceNag == 1) {
                member_last_nag.text = "Last nagged yesterday"
            } else {
                member_last_nag.text = "Last nagged $daysSinceNag days ago"
            }
        } else {
            member_last_nag.text = "Never nagged before"
        }

        back_button.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun showNagError(message: String?) {
        alert("Failed to nag - $message", "Makeradmin") {
            okButton { }
        }.show()
    }

    override fun showNagSuccess(nagType: String) {
        var message = ""
        when (nagType) {
            NAG_TYPE_WARNING -> {
                message = "Nag message sent to member!"
            }
            NAG_TYPE_LAST_WARNING -> {
                message = "Final warning message sent to member!"
            }
            NAG_TYPE_TERMINATED -> {
                message = "Box terminated message sent to member!"
            }
        }
        alert(message, "Makeradmin") {
            okButton { }
        }.show()
    }
}