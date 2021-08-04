package well.keepitsimple.collections

var abonents = mutableListOf<String>()
var filteredAbonents = mutableListOf<String>()

fun main() {
    print("Введите количество номеров - ")
    val amount = readLine()?.toInt()
    println("\n")

    if (amount != null) {
        abonents = addAbonent(amount)
    }

    filteredAbonents = abonents.filter { it.startsWith("+7") }.toMutableList()

    for(i in 0 until filteredAbonents.size){
        println(filteredAbonents[i])
    }

    val unique = setOf(filteredAbonents)

    println("Уникальных номеров: " + unique.size)

    val length = filteredAbonents.sumBy { it.length }

    println("Сумма длин номеров: $length")

    var phones = mutableMapOf<String, String>()

    for (i in 0 until filteredAbonents.size){
        print("Введите имя человека с номером телефона "+filteredAbonents[i]+" ")
        var name = readLine().toString()
        phones.put(filteredAbonents[i], name)
    }

    for ((key, value) in phones){
        println("Человек: $key. Номер телефона: $value")
    }

}


fun addAbonent(amount: Int): MutableList<String> {

    var list = mutableListOf<String>()

    for (i in 1..amount) {
        print("Введите номер телефона с именем человека - ")
        list.add(readLine().toString())
    }

    return list
}
