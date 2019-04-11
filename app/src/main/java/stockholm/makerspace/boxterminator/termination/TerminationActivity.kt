package stockholm.makerspace.boxterminator.termination

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.camera.CameraActivity
import stockholm.makerspace.boxterminator.camera.QR_EXTRA
import stockholm.makerspace.boxterminator.login.LoginActivity
import stockholm.makerspace.boxterminator.models.QrScanResult
import stockholm.makerspace.boxterminator.network.Member
import stockholm.makerspace.boxterminator.network.MemberResponse
import timber.log.Timber

const val CAMERA_REQUEST = 3333

class TerminationActivity : AppCompatActivity(), TerminationContract.View {

    private val presenter : TerminationContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        terminationBtn.setOnClickListener { startActivityForResult(intentFor<CameraActivity>(),
            CAMERA_REQUEST
        ) }
        //goodBtn.setOnClickListener { startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to 1)) }
        //badBtn.setOnClickListener { startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to 0)) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                val qrCodeResult = data?.getStringExtra(QR_EXTRA)
                qrCodeResultText.text = qrCodeResult
                val scanResult : QrScanResult? = Gson().fromJson(qrCodeResult, QrScanResult::class.java)
                scanResult?.let {
                    Timber.d("Scan result member number ${scanResult.member_number}, version ${scanResult.v}")
                    presenter.validateBox(scanResult)
                }
            }
        }
    }

    override fun showActiveStatus(member: Member?) {
        startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to member))
    }

    override fun showExpiredStatus(member: Member?) {
        startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to member))
    }

    override fun showTerminateStatus(member: Member?) {
        startActivity(intentFor<TerminationResultActivity>(TERMINATION_EXTRA to member))
    }

    override fun loginToSkynet() {
        startActivity(intentFor<LoginActivity>().clearTask().newTask())
    }
}
