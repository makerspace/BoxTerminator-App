package stockholm.makerspace.boxterminator.termination

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.burger_menu_activity.*
import kotlinx.android.synthetic.main.termination_activity.*
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
import stockholm.makerspace.boxterminator.termination.result.TERMINATION_EXTRA
import stockholm.makerspace.boxterminator.termination.result.TerminationResultActivity
import timber.log.Timber

const val CAMERA_REQUEST = 3333

class TerminationActivity : AppCompatActivity(), TerminationContract.View, NavigationView.OnNavigationItemSelectedListener {

    private val presenter : TerminationContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.burger_menu_activity)
        terminationBtn.setOnClickListener { startActivityForResult(intentFor<CameraActivity>(), CAMERA_REQUEST) }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.burger_open, R.string.burger_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                val qrCodeResult = data?.getStringExtra(QR_EXTRA)
                Timber.d("QR  scan result $qrCodeResult")
                val scanResult : QrScanResult? = Gson().fromJson(qrCodeResult, QrScanResult::class.java)
                scanResult?.let {
                    presenter.validateBox(scanResult)
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_termination -> { }
            R.id.nav_sessions -> { }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showActiveStatus(member: Member?) {
        startActivity(intentFor<TerminationResultActivity>(
            TERMINATION_EXTRA to member))
    }

    override fun showExpiredStatus(member: Member?) {
        startActivity(intentFor<TerminationResultActivity>(
            TERMINATION_EXTRA to member))
    }

    override fun showTerminateStatus(member: Member?) {
        startActivity(intentFor<TerminationResultActivity>(
            TERMINATION_EXTRA to member))
    }

    override fun loginToSkynet() {
        startActivity(intentFor<LoginActivity>().clearTask().newTask())
    }

    override fun showError(message: String?) {
        qrCodeResultText.text = "Skynet returned an error $message"
    }
}
