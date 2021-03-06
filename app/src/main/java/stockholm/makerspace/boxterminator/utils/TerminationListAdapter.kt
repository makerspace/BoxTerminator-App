package stockholm.makerspace.boxterminator.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.termination_list_item.view.*
import org.jetbrains.anko.backgroundColorResource
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.extensions.inflate
import stockholm.makerspace.boxterminator.extensions.simpleDateStringWithTime
import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.models.TerminationStatus

class TerminationListAdapter(private val terminations: List<Box>) : androidx.recyclerview.widget.RecyclerView.Adapter<TerminationListAdapter.TerminationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerminationViewHolder {
        return TerminationViewHolder(parent.inflate(R.layout.termination_list_item), parent.context)
    }

    override fun getItemCount() = terminations.size

    override fun onBindViewHolder(holder: TerminationViewHolder, position: Int) {
        val termination = terminations[position]
        holder.bind(termination)
    }

    class TerminationViewHolder(private val view: View, val context: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bind(box: Box) {
            view.apply {
                boxName.text = box.name
                boxMemberId.text = "#${box.member_number}"
                boxStatus.text = box.status.status
                boxExpireDate.text = String.format(context.getString(R.string.expiry_date), box.expire_date)
                box.last_nag_at?.let { boxLastNag.text = String.format(context.getString(R.string.last_nag), it) }
                    ?: run { boxLastNag.text = String.format(context.getString(R.string.last_nag), "-") }
                boxLastScan.text = String.format(context.getString(R.string.last_scan), box.last_check_at.simpleDateStringWithTime())

                when(box.status){

                    TerminationStatus.ACTIVE -> backgroundColorResource = R.color.memberBoxMembershipValidSecondary
                    TerminationStatus.EXPIRED -> backgroundColorResource = R.color.memberBoxMembershipInvalidSecondary
                    TerminationStatus.TERMINATE -> backgroundColorResource = R.color.memberBoxMembershipVeryOldSecondary
                }
            }
        }
    }
}
