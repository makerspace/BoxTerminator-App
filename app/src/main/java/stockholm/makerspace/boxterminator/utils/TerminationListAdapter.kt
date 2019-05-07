package stockholm.makerspace.boxterminator.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.termination_list_item.view.*
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.extensions.inflate
import stockholm.makerspace.boxterminator.models.Box

class TerminationListAdapter(private val terminations: List<Box>) : RecyclerView.Adapter<TerminationListAdapter.TerminationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerminationViewHolder {
        return TerminationViewHolder(parent.inflate(R.layout.termination_list_item))
    }

    override fun getItemCount() = terminations.size

    override fun onBindViewHolder(holder: TerminationViewHolder, position: Int) {
        val termination = terminations[position]
        holder.bind(termination)
    }

    class TerminationViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(box: Box) {
            view.apply {
                boxName.text = box.name
                boxMemberId.text = box.member_number.toString()
                boxStatus.text = box.status.status
                boxExpireDate.text = box.expire_date
                boxLastNag.text = box.last_nag_at
                boxLastScan.text = box.last_check_at
            }
        }
    }
}