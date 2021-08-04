package well.keepitsimple.classes

class Room(_area: Double){

    public val area: Double = _area

    open protected var title = "Обычная комната"

    fun getDescription(): String{
        return "Название: $title, Площадь: $area"
    }

}

class Person(_name: String){
    public val name: String = _name
}