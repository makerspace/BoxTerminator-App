package stockholm.makerspace.boxterminator.termination

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.burger_menu_activity.*
import kotlinx.android.synthetic.main.termination_activity.*
import org.jetbrains.anko.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.R
import stockholm.makerspace.boxterminator.camera.CameraActivity
import stockholm.makerspace.boxterminator.camera.QR_EXTRA
import stockholm.makerspace.boxterminator.login.LoginActivity
import stockholm.makerspace.boxterminator.models.QrScanResult
import stockholm.makerspace.boxterminator.models.Member
import stockholm.makerspace.boxterminator.termination.result.TERMINATION_EXTRA
import stockholm.makerspace.boxterminator.termination.result.TerminationResultActivity
import stockholm.makerspace.boxterminator.terminationsessions.TerminationSessionsActivity
import stockholm.makerspace.boxterminator.terminationsessions.doubles.DoublesActivity
import timber.log.Timber

const val CAMERA_REQUEST = 3333
const val SCAN_RESULT_REQUEST = 3334

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

                val scanResult : QrScanResult? = try {
                    Gson().fromJson(qrCodeResult, QrScanResult::class.java)
                } catch (e: JsonSyntaxException) {
                    null
                }

                scanResult?.let {
                    presenter.validateBox(scanResult)
                } ?: run {
                    showError("Gick inte att läsa QR koden. Skanna rätt nästa gång!!!")
                }
            }
        }
        if(requestCode == SCAN_RESULT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Start scanning again
                startActivityForResult(intentFor<CameraActivity>(), CAMERA_REQUEST);
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_termination -> { }
            R.id.nav_sessions -> { startActivity(intentFor<TerminationSessionsActivity>()) }
            R.id.nav_doubles -> { startActivity(intentFor<DoublesActivity>()) }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showActiveStatus(member: Member) {
        startActivityForResult(intentFor<TerminationResultActivity>(
            TERMINATION_EXTRA to member), SCAN_RESULT_REQUEST)
    }

    override fun showExpiredStatus(member: Member) {
        startActivityForResult(intentFor<TerminationResultActivity>(
            TERMINATION_EXTRA to member), SCAN_RESULT_REQUEST)
    }

    override fun showTerminateStatus(member: Member) {
        startActivityForResult(intentFor<TerminationResultActivity>(
            TERMINATION_EXTRA to member), SCAN_RESULT_REQUEST)
    }

    override fun showError(message: String?) {
        alert("$message", "Error") {
            okButton { }
        }.show()
    }
}
