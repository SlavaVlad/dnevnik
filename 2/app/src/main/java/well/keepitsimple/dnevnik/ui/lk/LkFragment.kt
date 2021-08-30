package well.keepitsimple.dnevnik.ui.lk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import well.keepitsimple.dnevnik.MainActivity
import well.keepitsimple.dnevnik.R

class LkFragment : Fragment() {

    lateinit var et_last_name: EditText
    lateinit var et_name: EditText
    lateinit var et_patronymic: EditText

    val F: String = "Firebase"
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val activity: MainActivity? = activity as MainActivity?
        val uid: String? = activity!!.uid
        getData(uid!!)

        val view = inflater.inflate(R.layout.fragment_lk, container, false)

        et_last_name = view.findViewById(R.id.et_last_name)
        et_name = view.findViewById(R.id.et_name)
        et_patronymic = view.findViewById(R.id.et_patronymic)

        return view

    }

    private fun getData(uid: String) {
        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { doc ->
                et_last_name.setText(doc.getString("last_name"))
                et_name.setText(doc.getString("name"))
                et_patronymic.setText(doc.getString("patronymic"))
            }
            .addOnFailureListener { exception ->
                Log.d(F, "get failed with ", exception)
            }
    }
}