package stockholm.makerspace.boxterminator.terminationsessions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.termination_sessions_activity.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.terminationsessions.sessionlist.SESSION_TOKEN_EXTRA
import stockholm.makerspace.boxterminator.terminationsessions.sessionlist.ScanSessionListActivity
import stockholm.makerspace.boxterminator.utils.TerminationListAdapter
import stockholm.makerspace.boxterminator.utils.TerminationSessionAdapter
import timber.log.Timber


class TerminationSessionsActivity : AppCompatActivity(), TerminationSessionsContract.View,
    TerminationSessionAdapter.SessionClickListener {

    private val presenter : TerminationSessionsContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.termination_sessions_activity)
        presenter.getTerminations()
    }

    override fun showScans(scans : List<Box>) {
        val sortedScans = scans.sortedBy { it.expire_date }
        val sessionsAdapter = TerminationListAdapter(sortedScans)
        scanSessionRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        scanSessionRecyclerView.adapter = sessionsAdapter
    }

    override fun onSessionClicked(sessionToken: String) {
        startActivity(intentFor<ScanSessionListActivity>(SESSION_TOKEN_EXTRA to sessionToken))
        Timber.d("Previous termination session token $sessionToken")
    }
}