package stockholm.makerspace.boxterminator.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
enum class TerminationStatus(val status: String) : Parcelable {
    @SerializedName("active")
    ACTIVE("active"),
    @SerializedName("expired")
    EXPIRED("expired"),
    @SerializedName("terminate")
    TERMINATE("terminate")
}