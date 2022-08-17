package pe.com.ecommers

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val  args: ProfileFragmentArgs by navArgs()

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
        val view =inflater.inflate(R.layout.fragment_profile, container, false)
        val user = args.user
        val txtemail = view.findViewById<TextView>(R.id.txtemail)
        val btnlogout = view.findViewById<Button>(R.id.logout)
        txtemail.text = user.email

        btnlogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val nav: NavController = Navigation.findNavController(view)
            val action = ProfileFragmentDirections.actionProfileFragmentToAuthFragment()
            nav.navigate(action)
            val prefs = this.activity?.getSharedPreferences(getString(R.string.pref_file),Context.MODE_PRIVATE)?.edit()
            prefs?.clear()
            prefs?.apply()

        }

        //guardado de datos
        val prefs = this.activity?.getSharedPreferences(getString(R.string.pref_file),Context.MODE_PRIVATE)?.edit()
        prefs?.putString("email",user.email)
        prefs?.putString("provider",user.provider)
        prefs?.apply()


        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewCart = view.findViewById<RecyclerView>(R.id.recicly_viewcar)
        recyclerViewCart.layoutManager = LinearLayoutManager(context)
        recyclerViewCart.setHasFixedSize(true)
        val prefs = this.activity?.getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE)
        val email: String? =prefs?.getString("email",null)
        val provider = prefs?.getString("provider",null)

        if (email!=null&& provider!=null ){
            val dbCart = db.collection("user").document(email).collection("cart")



            dbCart.get().addOnCompleteListener(object: OnCompleteListener<QuerySnapshot> {
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    if(p0.isSuccessful){
                        val listm: MutableList<String> = ArrayList()
                        val productcategory: MutableList<Productcart> = arrayListOf()


                        for (document in p0.result) {
                            var product = Productcart()
                            var products= ProductModel()
                            product.product = products
                            product.product?.id = document.id
                            product.product?.name = document.get("name") as String?
                            product.product?.price = document.get("price") as String?
                            product.product?.image = document.get("image") as String?
                            product.amount =document.get("amount") as Long?
                            productcategory.add(product)
                        }
                        val list: List<Productcart> =productcategory.toList()
                        val adaptadorcart = ProductCartAdapter(list)
                        adaptadorcart.onbuttonClick={
                            dbCart.document(it.product?.id.toString()).delete()

                        }
                        recyclerViewCart.adapter = adaptadorcart

                        Log.d("huella", list.toString())
                    } else {
                        Log.d("huella", "Error en obtener documentos", p0.exception)
                    }
                }
            })



        }







    }

    private fun agregardatos(){

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}