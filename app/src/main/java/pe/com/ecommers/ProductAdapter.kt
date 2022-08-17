package pe.com.ecommers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter (private val products : List<ProductModel>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClick : ((ProductModel) -> Unit)? = null

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val productName = view.findViewById<TextView>(R.id.txtproduct)
        val productPrice = view.findViewById<TextView>(R.id.txtprice)
        val productImage = view.findViewById<ImageView>(R.id.imageproduct)


        fun bindProduct(product: ProductModel){
            productName.text = product.name
            productPrice.text = product.price
            Glide.with(itemView.context).load(product.image).into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bindProduct(products.get(position))
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(product)

        }
    }
}