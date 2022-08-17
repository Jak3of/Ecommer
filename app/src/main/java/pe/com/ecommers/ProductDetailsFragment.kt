package pe.com.ecommers

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val  args: ProductDetailsFragmentArgs by navArgs()

    private val db = FirebaseFirestore.getInstance()

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
        val view =inflater.inflate(R.layout.fragment_product_details, container, false)

        val imgproduct = view.findViewById<ImageView>(R.id.imgproduct)
        val name = view.findViewById<TextView>(R.id.TXTNOMBRE)
        val descrition = view.findViewById<TextView>(R.id.TXTDESCRIPCION)
        val precio = view.findViewById<TextView>(R.id.TXTPRECIO)
        val numberpicker= view.findViewById<NumberPicker>(R.id.EDTCANTIDAD)
        val product : ProductModel = args.product

        numberpicker.minValue = 1
        numberpicker.maxValue = 10

        Glide.with(view.context).load(product.image).into(imgproduct)
        name.text = product.name
        descrition.text = product.description
        precio.text = product.price





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nav: NavController = Navigation.findNavController(view)

        val prefs = this.activity?.getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE)
        val email =prefs?.getString("email",null)
        val provider = prefs?.getString("provider",null)
        val addcart = view.findViewById<Button>(R.id.BTNCART2)
        val amount= view.findViewById<NumberPicker>(R.id.EDTCANTIDAD)







    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}