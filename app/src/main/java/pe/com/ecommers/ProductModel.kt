package pe.com.ecommers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductModel : Parcelable{
    val createAt : Long? = null
    var name : String? = null
    val id_category : String? = null
    var image : String? = null
    val description : String? = null
    var price : String? = null
    var id : String? = null
}