package stockholm.makerspace.boxterminator.utils

import android.content.SharedPreferences
import org.joda.time.DateTime
import org.joda.time.Hours
import org.koin.core.KoinComponent

const val TOKENS = "skynetTokens"
const val CURRENT_TOKEN = "currentToken"
const val SESSION_TIMESTAMP = "sessionTimestamp"

class SkynetDatastore(private var sharedPreferences: SharedPreferences) : KoinComponent {

    val sessionTimeoutInHours = 2

    fun saveSkynetToken(accessToken: String) {
        sharedPreferences.edit().putString(CURRENT_TOKEN, accessToken).apply()
        addSkynetToken(accessToken)
    }

    private fun addSkynetToken(token: String) {
        val tokenSet = sharedPreferences.getStringSet(TOKENS, mutableSetOf())
        val timestamp = System.currentTimeMillis().toString()
        tokenSet.add("$timestamp;$token")
        sharedPreferences.edit().putStringSet(TOKENS, tokenSet).apply()
    }

    fun skynetToken(): String? = sharedPreferences.getString(CURRENT_TOKEN, null)

    fun isTokenActive(): Boolean {
        val now = DateTime.now()
        val lastSessionUse = getLastUsedSessionTimestamp()
        return Hours.hoursBetween(lastSessionUse, now).hours <= sessionTimeoutInHours
    }

    private fun getLastUsedSessionTimestamp(): DateTime {
        val timestamp = sharedPreferences.getLong(SESSION_TIMESTAMP, 0)
        return DateTime(timestamp)
    }

    fun refreshSession() {
        sharedPreferences.edit().putLong(SESSION_TIMESTAMP, System.currentTimeMillis()).apply()
    }

    fun terminationSessions(): Set<String> = sharedPreferences.getStringSet(TOKENS, mutableSetOf())
}