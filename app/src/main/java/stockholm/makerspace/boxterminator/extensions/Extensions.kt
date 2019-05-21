package stockholm.makerspace.boxterminator.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun DateTime.simpleDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(this.toDate())
}

fun Date.simpleDateStringWithTime(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK).format(this)
}