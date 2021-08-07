package well.keepitsimple.classes

import java.util.*

fun main() {
    val room = Room(50.0)
    val BedRoom       = BedRoom(15.0)
    val LivingRoom    = LivingRoom(15.0)
    val Kitchen       = Kitchen(15.0)
    val Bathroom      = Bathroom(15.0)
    val ChildrensRoom = ChildrensRoom(15.0)
    val Main          = Main(15.0, "Main room")
    println(room.getDescription() + "\n" +
            BedRoom.getDescription() + "\n" +
            LivingRoom.getDescription() + "\n" +
            Kitchen.getDescription() + "\n" +
            Bathroom.getDescription() + "\n" +
            ChildrensRoom.getDescription() + "\n" +
            Main.getDescription()
    )
}

