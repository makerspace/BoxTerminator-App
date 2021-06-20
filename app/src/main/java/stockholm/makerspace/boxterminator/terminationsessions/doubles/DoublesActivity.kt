package stockholm.makerspace.boxterminator.terminationsessions.doubles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.doubles_activity.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.models.Box
import stockholm.makerspace.boxterminator.models.DoubleBox
import stockholm.makerspace.boxterminator.utils.TerminationListAdapter

class DoublesActivity: AppCompatActivity(), DoublesContract.View {

    private val presenter : DoublesContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doubles_activity)
        presenter.getDoubles()
    }

    override fun showDoubles(doublesList: MutableList<DoubleBox>) {

    }

    override fun showDoublesList(doublesList: MutableList<Box>) {
        val sortedBoxes = doublesList.sortedBy { it.member_number }
        val adapter = TerminationListAdapter(sortedBoxes)
        doublesRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        doublesRecyclerView.adapter = adapter
    }
}
