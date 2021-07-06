package well.keepitsimple.a1_variables

fun main() {
    val firstName: String = "Vladislav"
    val lastName: String = "Vladimirov"
    var height: Int = 192
    var weight: Float = 60F

    var isChild: Boolean = height<150 || weight<40F
    var info = "$firstName $lastName $height $weight $isChild"

    println(info)

    height = 170
    weight = 39F
 
    isChild = height<150 || weight<40F
    info = "$firstName $lastName $height $weight $isChild"

    println(info)
}
