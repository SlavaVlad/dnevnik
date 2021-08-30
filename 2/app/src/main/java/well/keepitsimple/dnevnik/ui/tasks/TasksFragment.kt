package well.keepitsimple.dnevnik.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import well.keepitsimple.dnevnik.AddHomework
import well.keepitsimple.dnevnik.MainActivity
import well.keepitsimple.dnevnik.R
import android.widget.Toast.makeText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TasksFragment : Fragment() {

    val F = "Firebase"
    lateinit var lay_items: LinearLayout

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lk, container, false)

        

        return view
    }

}