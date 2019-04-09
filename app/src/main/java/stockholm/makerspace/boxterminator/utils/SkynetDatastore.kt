package stockholm.makerspace.boxterminator.utils

import android.content.SharedPreferences
import org.joda.time.DateTime
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

    fun hasSessionExpired(): Boolean {
        val expiryDate = DateTime(sharedPreferences.getString(EXPIRY_DATE, null))
        val now = DateTime()
        return expiryDate.isEqual(now) || expiryDate.isAfter(now)
    }
}