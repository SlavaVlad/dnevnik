package well.keepitsimple.dnevnik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class AddHomework : AppCompatActivity() {

    lateinit var cg_subject: ChipGroup
    lateinit var cg_type: ChipGroup
    lateinit var btn_complete: Button
    lateinit var calendar: CalendarView
    lateinit var et_text: EditText
    var tags = ArrayList<String>()
    var db = FirebaseFirestore.getInstance()
    val F = "Firebase"
    lateinit var globalDoc: DocumentSnapshot
    var data = hashMapOf<String, Any>()
    var time: Timestamp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_homework)

        cg_subject = findViewById(R.id.cg_subject)
        cg_type = findViewById(R.id.cg_type)
        btn_complete = findViewById(R.id.btn_complete)
        calendar = findViewById(R.id.calendar)
        et_text = findViewById(R.id.et_text)

        getTags()

        cg_subject.setOnCheckedChangeListener { group, id ->
            if (id != -1){addChipData(group, id)}
        }

        cg_type.setOnCheckedChangeListener { group, id ->
            if (id != -1){addChipData(group, id)}
        }

        btn_complete.setOnClickListener {
            complete()
        }

    }

    private fun addChipData(group: ChipGroup?, id: Int) {

        val c:Chip = findViewById(id)

        if (group == cg_subject){
            data["subject"] = c.text.toString()
        }
        if (group == cg_type){
            data["type"] = c.text.toString()
        }
    }

    private fun getTags() {
        val docRef = db.collection("tags").document("tags")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    addChips(document)
                    globalDoc = document

                    Log.d(F, "DocumentSnapshot data: ${document.data}")
                } else {



                    Log.d(F, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(F, "get failed with ", exception)
            }
    }

    private fun addChips(document: DocumentSnapshot) {

        val subjects: List<String> = document.get("subjects") as List<String>
        val types: List<String> = document.get("typesofwork") as List<String>

        for (element in subjects) {
            val c = Chip(this)
            c.text = element
            c.isCheckable = true
            cg_subject.addView(c)
        }

        for (element in types){
            val c = Chip(this)
            c.text = element
            c.isCheckable = true
            cg_type.addView(c)
        }

        btn_complete.isEnabled = true

    }

    private fun complete() {
        if (et_text.text.toString() != "" && data.contains("subject") && data.contains("type")) {
            time = Timestamp(calendar.date)
            data["deadline"] = time!!
            data["text"] = et_text.text.toString()
            db.collection("notifications").add(data).addOnCompleteListener {
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
                Toast.makeText(this, "Уведомление создано", Toast.LENGTH_SHORT).show()
            }
        }

    }

}