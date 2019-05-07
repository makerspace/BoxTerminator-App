package stockholm.makerspace.boxterminator.terminationsessions.sessionlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.scan_session_list_activity.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.utils.TerminationListAdapter

const val SESSION_TOKEN_EXTRA = "sessionTokenExtra"

class ScanSessionListActivity : AppCompatActivity(), ScanSessionListContract.View {

    private val presenter : ScanSessionListContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_session_list_activity)
        val token = intent.extras.getString(SESSION_TOKEN_EXTRA)
        presenter.getScans(token)
    }

    override fun showScans(terminations: List<Box>) {
        val adapter = TerminationListAdapter(terminations)
        scansRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        scansRecyclerView.adapter = adapter
    }
}