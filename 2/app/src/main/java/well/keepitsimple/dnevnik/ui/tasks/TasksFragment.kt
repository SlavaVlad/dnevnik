package well.keepitsimple.dnevnik.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import well.keepitsimple.dnevnik.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import well.keepitsimple.dnevnik.AddHomework
import well.keepitsimple.dnevnik.TaskItem
import well.keepitsimple.dnevnik.TasksAdapter


class TasksFragment : Fragment() {

    val F = "Firebase"
    lateinit var lv_tasks: ListView

    val db = FirebaseFirestore.getInstance()

    val tasks = ArrayList<TaskItem>() // динамический массив - список из полей документов в коллекции

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        lv_tasks = view.findViewById(R.id.lv_tasks)

        getTasks()

        return view
    }

    private fun getTasks() {
        db.collection("tasks")
            //.whereEqualTo("group", "-")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(F, "Listen failed.", e) // е - ошибка
                    return@addSnapshotListener
                }

                    for (doc in value!!) { // проходим по каждому документу
                        tasks.add(
                            TaskItem
                                (
                                doc.getString("subject").toString(),
                                getDeadlineInDays(doc.get("deadline") as Timestamp?).toString(),
                                doc.getString("text")!!, doc.id
                            )
                        )
                    }
                    setList(tasks)
                }

    }

    private fun getDeadlineInDays(timestamp: Timestamp?): Int {
        val time = System.currentTimeMillis()
        val doc_time = timestamp!!.seconds
        return ((time - doc_time)/60/60/24).toInt()
    }

    private fun setList(list: ArrayList<TaskItem>) {

        val tasksAdapter = TasksAdapter(requireActivity().baseContext, R.layout.task_item_layout, list)

        lv_tasks.adapter = tasksAdapter

        lv_tasks.setOnItemClickListener { parent, view, position, id ->
            openDoc(tasks[position].id)
        }

    }

    fun openDoc(id: String) {
        val intent_edit:Intent = Intent(requireActivity().applicationContext, AddHomework::class.java)
            .putExtra("action", "edit")
            .putExtra("id", id)
        startActivity(intent_edit)
    }

}