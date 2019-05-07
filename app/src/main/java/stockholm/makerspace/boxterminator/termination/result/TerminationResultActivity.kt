package stockholm.makerspace.boxterminator.termination.result

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import stockholm.makerspace.boxterminator.network.Member
import stockholm.makerspace.boxterminator.network.TerminationStatus

const val TERMINATION_EXTRA = "termination_extra"

class TerminationResultActivity : AppCompatActivity(), TerminationResultContract.View {

    private val presenter: TerminationResultContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.termination_result_activity)
        val member: Member = intent.extras.getParcelable(TERMINATION_EXTRA)
        member_name.text = member.name
        member_number.text = member.member_number.toString()

        when (member.status) {
            TerminationStatus.ACTIVE -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_tick)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipValid, null)
                member_remaining_background.background = resources.getDrawable(R.color.memberBoxMembershipValid, null)
                terminationResultBtn.background= resources.getDrawable(R.color.memberBoxMembershipValidSecondary, null)
                member_remaining.text = "${member.expire_date}"
                member_remaining_sub.text = "Expiration date"
            }
            TerminationStatus.EXPIRED -> {
                member_validity.image = getDrawable(R.drawable.ic_noun_cross)
                member_validity.imageTintList = resources.getColorStateList(R.color.memberBoxMembershipInvalid, null)
                member_remaining_background.background = resources.getDrawable(R.color.memberBoxMembershipInvalid, null)
                terminationResultBtn.background= resources.getDrawable(R.color.memberBoxMembershipInvalidSecondary, null)

                val now = DateTime()
                val expireDate = DateTime(member.expire_date)
                val daysInBetween = Days.daysBetween(expireDate, now).days
                member_remaining.text = "$daysInBetween"
                member_remaining_sub.text = "Days since expiration"

                terminationResultBtn.apply {
                    setOnClickListener {
                        presenter.nag(
                            member.member_number,
                            member.box_label_id
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

                val now = DateTime()
                val expireDate = DateTime(member.expire_date)
                val daysInBetween = Days.daysBetween(expireDate, now).days
                member_remaining.text = "$daysInBetween"
                member_remaining_sub.text = "Days since expiration"

                terminationResultBtn.apply {
                    setOnClickListener {
                        presenter.nag(
                            member.member_number,
                            member.box_label_id
                        )
                    }
                    visibility = View.VISIBLE
                }
            }
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