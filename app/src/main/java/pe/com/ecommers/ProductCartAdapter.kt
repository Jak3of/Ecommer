package pe.com.ecommers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductCartAdapter (private val products : List<Productcart>
) : RecyclerView.Adapter<ProductCartAdapter.ProductViewHolder>() {

    var onbuttonClick : ((Productcart) -> Unit)? = null

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val productName = view.findViewById<TextView>(R.id.txtproductcart)
        val productPrice = view.findViewById<TextView>(R.id.txtpricecart)
        val productImage = view.findViewById<ImageView>(R.id.imageproductcart)
        val productAmount = view.findViewById<TextView>(R.id.txtamount)
        val buttonx = view.findViewById<Button>(R.id.btndeletecart)

        fun bindProduct(product: Productcart){
            productName.text = product.product?.name
            productPrice.text = product.product?.price
            productAmount.text = product.amount.toString()
            Glide.with(itemView.context).load(product.product?.image).into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_productcart, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bindProduct(products.get(position))
        holder.buttonx.setOnClickListener {
            onbuttonClick?.invoke(product)
        }

    }
}