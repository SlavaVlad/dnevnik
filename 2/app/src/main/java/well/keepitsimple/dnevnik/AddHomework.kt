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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.time.Instant
import java.util.*

class AddHomework : AppCompatActivity() {

    var gyear: Int? = null
    var gmonth: Int? = null
    var gday: Int? = null
    lateinit var cg_subject: ChipGroup
    lateinit var cg_type: ChipGroup
    lateinit var btn_complete: Button
    lateinit var calendar_i: CalendarView
    lateinit var et_text: EditText
    var db = FirebaseFirestore.getInstance()
    val F = "Firebase"
    lateinit var globalDoc: DocumentSnapshot
    var data = hashMapOf<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_homework)

        cg_subject = findViewById(R.id.cg_subject)
        cg_type = findViewById(R.id.cg_type)
        btn_complete = findViewById(R.id.btn_complete)
        calendar_i = findViewById(R.id.calendar)
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

        calendar_i.setOnDateChangeListener { view, year, month, dayOfMonth ->
            gyear = year-1900
            gmonth = month
            gday = dayOfMonth
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

                addChips(document)
                globalDoc = document

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
            if (gyear != null){
                data["deadline"] = Timestamp(gyear!!, gmonth!!, gday!!, 12, 0, 0, 0)
            } else {
                data["deadline"] = Timestamp(System.currentTimeMillis())
            }
            data["text"] = et_text.text.toString()
            db.collection("tasks").add(data).addOnCompleteListener {
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
                Toast.makeText(this, "Уведомление создано", Toast.LENGTH_SHORT).show()
            }
        }

    }

}