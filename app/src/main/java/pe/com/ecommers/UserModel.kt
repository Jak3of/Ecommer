package pe.com.ecommers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel: Parcelable {

    var email : String? = null
    var provider : String? = null
}