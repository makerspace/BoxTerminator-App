package stockholm.makerspace.boxterminator.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member(
    val box_label_id : Int, //unique timestamp for each QR sticker
    val member_number : Int,
    val name : String,
    val expire_date: String, //labb membership
    val terminate_date: String, //expire date + 45 days
    val status: TerminationStatus,
    var last_nag_at: String?,  // Last time the member was nagged
    val last_check_at: String? // Last time this QR code was scanned
) : Parcelable