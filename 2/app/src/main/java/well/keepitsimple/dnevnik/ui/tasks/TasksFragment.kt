package well.keepitsimple.dnevnik.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.MainScope
import well.keepitsimple.dnevnik.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.round


class TasksFragment : Fragment() {

    val F = "Firebase"
    private lateinit var lv_tasks: ListView

    private val db = FirebaseFirestore.getInstance()

    private val tasks = ArrayList<TaskItem>() // динамический массив - список из полей документов в коллекции

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        lv_tasks = view.findViewById(R.id.lv_tasks)

        getTasks()

        return view
    }

    private fun getTasks() {
        db.collection("tasks").orderBy("deadline")
            //.whereEqualTo("group", "-")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(F, "Listen failed.", e) // е - ошибка
                    return@addSnapshotListener
                }

                    for (doc in value!!) { // проходим по каждому документу
                        if (getDeadlineInDays(doc.getTimestamp("deadline")) > 0 && !tasks.contains(TaskItem(doc.getString("subject").toString(), (getDeadlineInDays(doc.getTimestamp("deadline"))), doc.getString("text")!!, doc.id, doc, doc.getString("type")!!))) {
                            tasks.add(
                                TaskItem
                                    (
                                    doc.getString("subject").toString(),
                                    (getDeadlineInDays(doc.getTimestamp("deadline"))),
                                    doc.getString("text")!!, doc.id, doc, doc.getString("type")!!
                                )
                            )
                        }
                    }
                    setList(tasks)
                }

    }

    private fun getDeadlineInDays(timestamp: Timestamp?): Double {
        return ceil(((timestamp!!.seconds.toDouble()) - System.currentTimeMillis() / 1000) / 86400)
    }

    private fun setList(list: ArrayList<TaskItem>) {

        val tasksAdapter = TasksAdapter(requireContext().applicationContext, R.layout.task_item_layout, list)

        lv_tasks.adapter = tasksAdapter

        lv_tasks.setOnItemClickListener { parent, view, position, id ->
            openDoc(tasks[position].id)
        }

    }

    fun openDoc(id: String) {
        val intent_edit:Intent = Intent(requireContext().applicationContext, AddHomework::class.java)
            .putExtra("action", "edit")
            .putExtra("id", id)
        startActivity(intent_edit)
    }

}