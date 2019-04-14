package stockholm.makerspace.boxterminator.utils

import android.content.SharedPreferences
import org.koin.core.KoinComponent

const val TOKEN = "skynetToken"

class SkynetDatastore(private var sharedPreferences: SharedPreferences) : KoinComponent {

    fun saveSkynetToken(accessToken: String) {
        sharedPreferences.edit().putString(TOKEN, accessToken).apply()
    }

    fun skynetToken(): String? = sharedPreferences.getString(TOKEN, null)
}