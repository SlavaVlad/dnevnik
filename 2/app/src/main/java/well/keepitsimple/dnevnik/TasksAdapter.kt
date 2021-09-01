package well.keepitsimple.dnevnik

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.XmlResourceParser
import android.graphics.Color
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
        text.text = item[position].text
        deadline.text = (item[position].deadline).toString()
        //if (item[position].deadline >= 1){
        //    deadline.text = ("Сдача через: " + (item[position].deadline).toString() + "дн.")
        //} else {
        //    deadline.setTextColor(context.getColor(R.color.design_default_color_error))
        //    deadline.text = "Сдача сегодня!"
        //}

        return view
    }
}