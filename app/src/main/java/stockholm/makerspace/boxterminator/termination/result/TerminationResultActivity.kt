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
        member_number.text = member.member_number.toString()
        val now = DateTime()

        when (member.status) {
            TerminationStatus.ACTIVE -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_tick)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipValid, null)
                member_remaining_background.background = resources.getDrawable(R.color.memberBoxMembershipValid, null)
                terminationResultBtn.background= resources.getDrawable(R.color.memberBoxMembershipValidSecondary, null)
                terminationResultBtn.visibility = View.INVISIBLE
                member_last_nag.visibility = View.INVISIBLE
                termiante_button.visibility = View.GONE
                member_remaining.text = "${member.expire_date}"
                member_remaining_sub.text = "Expiration date"
            }
            TerminationStatus.EXPIRED -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_cross)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipInvalid, null)
                member_remaining_background.background = resources.getDrawable(R.color.memberBoxMembershipInvalid, null)
                terminationResultBtn.background= resources.getDrawable(R.color.memberBoxMembershipInvalidSecondary, null)
                terminationResultBtn.visibility = View.VISIBLE
                termiante_button.visibility = View.GONE
                member_last_nag.visibility = View.VISIBLE

                val expireDate = DateTime(member.expire_date)
                val daysInBetween = Days.daysBetween(expireDate, now).days
                member_remaining.text = "$daysInBetween"
                member_remaining_sub.text = "Days since expiration"

                terminationResultBtn.apply {
                    setOnClickListener {
                        presenter.nag(
                            member,
                            NAG_TYPE_WARNING
                        )
                    }
                    visibility = View.VISIBLE
                }
            }
            TerminationStatus.TERMINATE -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_cross)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipVeryOld, null)
                member_remaining_background.background = resources.getDrawable(R.color.memberBoxMembershipVeryOld, null)
                terminationResultBtn.background = resources.getDrawable(R.color.memberBoxMembershipVeryOldSecondary, null)
                member_last_nag.visibility = View.VISIBLE

                val expireDate = DateTime(member.expire_date)
                val daysInBetween = Days.daysBetween(expireDate, now).days
                member_remaining.text = "$daysInBetween"
                member_remaining_sub.text = "Days since expiration"

                terminationResultBtn.apply {
                    setOnClickListener {
                        var nagType = NAG_TYPE_LAST_WARNING
                        presenter.nag(
                            member,
                            nagType
                        )
                    }
                    visibility = View.VISIBLE
                }
                termiante_button.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        var nagType = NAG_TYPE_TERMINATED
                        presenter.nag(
                            member,
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

        terminationBackBtn.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun showNagError(message: String?) {
        alert("Failed to nag - $message", "Makeradmin") {
            okButton { }
        }.show()
    }

    override fun showNagSuccess() {
        alert("Nag message sent to member!", "Makeradmin") {
            okButton { }
        }.show()
    }
}