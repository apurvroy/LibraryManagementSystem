open class Person(firstName:String,lastName:String){
    val firstName=firstName.replaceFirstChar{it.uppercase()}
    val lastName=lastName.replaceFirstChar { it.uppercase() }
}