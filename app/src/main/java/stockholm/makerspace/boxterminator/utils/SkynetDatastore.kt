package stockholm.makerspace.boxterminator.utils

import android.content.SharedPreferences
import org.koin.core.KoinComponent

const val TOKEN = "token"
const val EXPIRY_DATE = "expiryDate"

class SkynetDatastore(private var sharedPreferences: SharedPreferences) : KoinComponent {

    fun saveSkynetToken(accessToken: String) {
        sharedPreferences.edit().putString(TOKEN, accessToken).apply()
    }

    fun saveTokenExpiryDate(expiryDate: String) {
        sharedPreferences.edit().putString(EXPIRY_DATE, expiryDate).apply()
    }


}