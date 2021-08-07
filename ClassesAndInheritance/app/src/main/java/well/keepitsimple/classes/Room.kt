package well.keepitsimple.classes

open class Room(_area: Double){

    open public val area: Double = _area

    open protected val title = "Обычная комната"

    fun getDescription(): String{
        return "Название: $title, Площадь: $area"
    }

}

class BedRoom      (_area: Double) : Room(_area) { override val title = "Bed room"; override val area = _area }
class LivingRoom   (_area: Double) : Room(_area) { override val title = "Living room"; override val area = _area }
class Kitchen      (_area: Double) : Room(_area) { override val title = "Kitchen"; override val area = _area }
class Bathroom     (_area: Double) : Room(_area) { override val title = "Bathroom"; override val area = _area }
class ChildrensRoom(_area: Double) : Room(_area) { override var title = "Children's room"; override val area = _area }
class Main         (_area: Double, _title: String) : Room(_area) { override var title = _title; override val area = _area }
//class MyRoom(_area: Double, _title: String) : Room(_area) { override val title = _title; override val area = _area }

//Создайте комнаты с характеристиками жилища своей мечты. Выведите информацию о получившихся комнатах на экран.