package stockholm.makerspace.boxterminator.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member(
    val box_label_id : Int,
    val member_number : Int,
    val name : String,
    val expire_date: String, //labb membership
    val terminate_date: String, //expire date + 45 days
    val status: TerminationStatus
) : Parcelable

@Parcelize
enum class TerminationStatus(val status: String) : Parcelable {
    @SerializedName("active")
    ACTIVE("active"),
    @SerializedName("expired")
    EXPIRED("expired"),
    @SerializedName("terminate")
    TERMINATE("terminate")
}
