package stockholm.makerspace.boxterminator.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Box(val box_label_id : Int, //unique timestamp for each QR sticker
               val member_number : Int,
               val name : String,
               val expire_date: String, //labb membership
               val terminate_date: String, //expire date + 45 days
               val status: TerminationStatus,
               val last_nag_at: String?,
               val last_check_at : String
) : Parcelable