package well.keepitsimple.dnevnik

data class TaskItem(
    val subject: String,
    val deadline: Double,
    val text: String,
    val id: String,
    val type: String,
        )
