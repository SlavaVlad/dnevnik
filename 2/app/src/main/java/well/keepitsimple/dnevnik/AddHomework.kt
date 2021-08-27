package well.keepitsimple.dnevnik

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore

class AddHomework : AppCompatActivity() {

    var cg_subject: ChipGroup? = null
    var tags = ArrayList<String>()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_homework)

        cg_subject = findViewById(R.id.cg_subject)

        getTags()

    }

    private fun getTags() {
        TODO("Not yet implemented")
    }

    private fun addTag(id: Int) {

        var chip = findViewById<Chip>(id)

        if (!tags.contains(chip.text.toString())){

            tags.add(chip.text.toString())
            chip.setBackgroundColor(R.color.teal_200.toInt())

        } else {

            tags.remove(chip.text.toString())

        }

        Log.e("Array!", tags.toString())

    }

}