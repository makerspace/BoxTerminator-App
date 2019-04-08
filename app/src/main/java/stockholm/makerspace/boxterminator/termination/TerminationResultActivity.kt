package stockholm.makerspace.boxterminator.termination

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.termination_result_activity.*
import stockholm.makerspace.boxterminator.R

const val TERMINATION_EXTRA = "termination_extra"

const val TERMINATE = 0
const val KEEP = 1

class TerminationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.termination_result_activity)
        val terminationResult = intent.extras.getInt(TERMINATION_EXTRA)
        when (terminationResult) {
            KEEP -> {
                terminationResultText.text = "I'LL BE BACK!"
                terminationResultImg.setImageResource(R.drawable.terminator)

            }
            TERMINATE -> {
                terminationResultText.text = "HASTA LA VISTA, BABY!"
                terminationResultImg.setImageResource(R.drawable.terminator_hand)
            }
        }
    }
}