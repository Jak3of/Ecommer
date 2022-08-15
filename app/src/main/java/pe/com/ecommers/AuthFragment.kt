package pe.com.ecommers

import android.app.AlertDialog
import android.app.DirectAction
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        // Inflate the layout for this fragment



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.edtemail)
        val password = view.findViewById<EditText>(R.id.edtpassword)
        val btnlogin = view.findViewById<Button>(R.id.signin)
        val btnregister = view.findViewById<Button>(R.id.register)

        btnregister.setOnClickListener {

            if (email.text.isNotEmpty()|| password.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            val nav: NavController = Navigation.findNavController(view)
                            var user : UserModel = UserModel()
                            user.email = email.text.toString()
                            user.provider = "BASIC"
                            val action = AuthFragmentDirections.actionAuthFragmentToProfileFragment(user)
                            nav.navigate(action)
                        } else{
                            showAlert("El usuario ya ah sido creado.")
                        }
                    }
            } else{
                showAlert("Ingrese sus credenciales")
            }
        }

        btnlogin.setOnClickListener {
            if (email.text.isNotEmpty()|| password.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            val nav: NavController = Navigation.findNavController(view)
                            var user : UserModel = UserModel()
                            user.email = email.text.toString()
                            user.provider = "BASIC"
                            val action = AuthFragmentDirections.actionAuthFragmentToProfileFragment(user)
                            nav.navigate(action)

                        }

                        else{
                            showAlert("El email y la contraseña no coinciden")
                        }
                    }
            } else{
                showAlert("Ingrese sus credenciales")
            }
        }

        session(view)



    }


    private fun session(view: View){
        val prefs = this.activity?.getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE)
        val email =prefs?.getString("email",null)
        val provider = prefs?.getString("provider",null)
        if (email!=null&& provider!=null ){

            val nav: NavController = Navigation.findNavController(view)
            var user : UserModel = UserModel()
            user.email = email
            user.provider = provider
            val action = AuthFragmentDirections.actionAuthFragmentToProfileFragment(user)
            nav.navigate(action)
        }

    }

    private fun showAlert(message: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error al ingresar")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog :AlertDialog = builder.create()
        dialog.show()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AuthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}