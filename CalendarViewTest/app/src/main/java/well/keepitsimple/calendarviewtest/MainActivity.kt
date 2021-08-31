package well.keepitsimple.calendarviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar: CalendarView? = findViewById(R.id.calendarept)
        val button: Button? = findViewById(R.id.ok)

        button!!.setOnClickListener {
            ok(calendar!!)
        }

    }

    private fun ok(calendar: CalendarView) {
        val date1 = calendar.date
        val date2 = calendar.getDate()
    }
}