package stockholm.makerspace.boxterminator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.koin.android.ext.android.inject
import stockholm.makerspace.boxterminator.login.LoginActivity
import stockholm.makerspace.boxterminator.termination.TerminationActivity
import stockholm.makerspace.boxterminator.utils.SkynetDatastore


class ProxyActivity : AppCompatActivity() {

    private val skynetDatastore : SkynetDatastore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isSessionAlive = skynetDatastore.isTokenActive()
        if(isSessionAlive) {
            startActivity(intentFor<TerminationActivity>().clearTask().newTask())
        } else {
            startActivity(intentFor<LoginActivity>().clearTask().newTask())
        }
    }
}