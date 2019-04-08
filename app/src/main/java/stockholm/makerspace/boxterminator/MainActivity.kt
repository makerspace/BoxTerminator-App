package stockholm.makerspace.boxterminator

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import stockholm.makerspace.boxterminator.camera.CameraActivity
import stockholm.makerspace.boxterminator.camera.QR_EXTRA
import stockholm.makerspace.boxterminator.termination.TERMINATION_EXTRA
import stockholm.makerspace.boxterminator.termination.TerminationResultActivity

const val CAMERA_REQUEST = 3333

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        terminationBtn.setOnClickListener { startActivityForResult(intentFor<CameraActivity>(), CAMERA_REQUEST) }
        goodBtn.setOnClickListener { startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to 1)) }
        badBtn.setOnClickListener { startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to 0)) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                qrCodeResultText.text = data?.getStringExtra(QR_EXTRA)
            }
        }
    }
}
