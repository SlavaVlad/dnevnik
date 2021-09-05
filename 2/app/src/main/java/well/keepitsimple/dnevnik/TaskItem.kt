package well.keepitsimple.dnevnik

import com.google.firebase.firestore.DocumentSnapshot

data class TaskItem(
    val subject: String,
    val deadline: Double,
    val text: String,
    val id: String,
    val doc: DocumentSnapshot,
    val type: String,
        )
