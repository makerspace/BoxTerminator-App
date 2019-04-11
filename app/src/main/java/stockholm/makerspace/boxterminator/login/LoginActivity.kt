package stockholm.makerspace.boxterminator.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import stockholm.makerspace.boxterminator.termination.TerminationActivity
import stockholm.makerspace.boxterminator.R


class LoginActivity : AppCompatActivity(), LoginContract.View {

    private val presenter: LoginContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val pass = passwordInput.text.toString()
            presenter.login(username, pass)
        }
    }

    override fun showError(message: String?) {
        statusText.text = "Skynet data failure $message"
    }

    override fun allowAccess() {
        startActivity(intentFor<TerminationActivity>().clearTask().newTask())
    }
}