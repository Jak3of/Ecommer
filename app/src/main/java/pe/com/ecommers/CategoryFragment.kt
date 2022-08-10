package pe.com.ecommers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val recyclerViewProduct = view.findViewById<RecyclerView>(R.id.recicly_view)
        recyclerViewProduct.layoutManager = LinearLayoutManager(context)
        recyclerViewProduct.setHasFixedSize(true)


        val spn = view.findViewById<Spinner>(R.id.spinner_category)

        spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val entero :String= position.toString()
                if (position.equals(0)){
                    getProductData { products : List<ProductModel> -> recyclerViewProduct.adapter = ProductAdapter(products) }
                } else{
                    getProductDataCategory ({ products : List<ProductModel> -> recyclerViewProduct.adapter = ProductAdapter(products) },entero)
                }

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {
                getProductData { products : List<ProductModel> -> recyclerViewProduct.adapter = ProductAdapter(products) }
            }
        }

        return view
    }

    private fun getProductData(callback: (List<ProductModel> ) -> Unit){
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getProducts()
        call.enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                val products = response.body()
                return callback(products!!)
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Log.d("HomeFragment", "Error: ${t.message}")
            }
        })
    }

    private fun getProductDataCategory(callback: (List<ProductModel> ) -> Unit,cate:String){
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getProducts()
        call.enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                var products = response.body()
                val productcategory: MutableList<ProductModel> = arrayListOf()
                products!!.forEach{
                    if (it.id_category.equals(cate)){
                        productcategory.add(it)
                    }
                }
                products = productcategory.toList()
                return callback(products!!)
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Log.d("CategoryFragment", "Error: ${t.message}")
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}