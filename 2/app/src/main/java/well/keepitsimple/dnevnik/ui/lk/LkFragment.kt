package well.keepitsimple.dnevnik.ui.lk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import well.keepitsimple.dnevnik.MainActivity
import well.keepitsimple.dnevnik.R

class LkFragment : Fragment() {

    lateinit var et_last_name: EditText
    lateinit var et_name: EditText
    lateinit var et_patronymic: EditText
    lateinit var rg_student: RadioGroup

    val F: String = "Firebase"
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_lk, container, false)

        rg_student = view.findViewById(R.id.rg_student)

        val activity: MainActivity? = activity as MainActivity?
        val uid: String? = activity!!.uid
        getData(uid!!)

        return view

    }

    private fun getData(uid: String) {
        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { doc ->
                setCabinet(doc)
            }
            .addOnFailureListener { exception ->
                Log.d(F, "get failed with ", exception)
            }
    }

    private fun setCabinet(doc: DocumentSnapshot) {

    }
}