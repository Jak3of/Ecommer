package pe.com.ecommers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductModel : Parcelable{
    val createAt : Long? = null
    val name : String? = null
    val id_category : String? = null
    val image : String? = null
    val description : String? = null
    val price : String? = null
    val id : String? = null
}