package stockholm.makerspace.boxterminator.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.termination_session_item.view.*
import org.joda.time.DateTime
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.extensions.inflate
import stockholm.makerspace.boxterminator.extensions.simpleDateString


class TerminationSessionAdapter(private val dateList: List<Pair<String, String>>) :
    RecyclerView.Adapter<TerminationSessionAdapter.SessionViewHolder>() {

    private var clickListener: SessionClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder(parent.inflate(R.layout.termination_session_item), clickListener)
    }

    override fun getItemCount(): Int = dateList.size

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(dateList[position])
    }

    fun setSessionClickListener(listener: SessionClickListener) {
        clickListener = listener
    }

    class SessionViewHolder(val view: View, private val clickListener: SessionClickListener?) : RecyclerView.ViewHolder(view) {
        fun bind(tokenDatePair: Pair<String, String>) {
            val date = DateTime(tokenDatePair.first.toLong())
            view.sessionDateText.text = date.simpleDateString()
            view.setOnClickListener { clickListener?.onSessionClicked(tokenDatePair.second) }
        }
    }

    interface SessionClickListener {
        fun onSessionClicked(sessionToken: String)
    }
}