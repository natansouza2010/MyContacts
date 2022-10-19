package ads.pdm.mycontacts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int,
    var name: String,
    var adress: String,
    var phone: String,
    var email: String,
):Parcelable
