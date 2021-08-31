package well.keepitsimple.dnevnik

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import well.keepitsimple.dnevnik.ui.tasks.TasksFragment

class TasksAdapter (var ctx:Context, var ressource:Int, var item:ArrayList<TaskItem>): ArrayAdapter<TaskItem>(ctx,ressource,item){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(ressource, null)

        val subject = view.findViewById<TextView>(R.id.i_subject)
        val deadline = view.findViewById<TextView>(R.id.i_deadline)
        val text = view.findViewById<TextView>(R.id.i_text)

        subject.text = item[position].subject
        deadline.text = item[position].deadline
        text.text = item[position].text

        return view
    }
}