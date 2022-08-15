package pe.com.ecommers

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth

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