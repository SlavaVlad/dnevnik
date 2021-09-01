package well.keepitsimple.dnevnik

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import android.R





class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {

        val longText: String = ""

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext)
            .setSmallIcon(R.drawable.ic_dialog_alert)
            .setContentTitle("Домашкаааа!")
            .setContentText("Вам заданы новые задания")
            .setStyle(NotificationCompat.BigTextStyle().bigText(longText))

        return Result.success()
    }
}