package com.example.androidpractice.kotlintraining

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/*
    Создать enum Type с константами DEMO и FULL.
 */
enum class Type {
    DEMO,
    FULL
}


/*
    Реализовать класс данных User с полями id, name, age и type.
    У класса User создать ленивое свойство startTime, в котором получаем текущее время.
 */
data class User(
    val id: String,
    val name: String,
    val age: Int,
    val type: Type
) {
    val startType by lazy {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}


/*
    Создать объект класса User, вывести в лог startTime данного юзера,
    после вызвать Thread.sleep(1000) и повторно вывести в лог startTime.
 */
fun createUser() {
    val user = User("id", "John", 11, Type.DEMO)
    println(user.startType)
    Thread.sleep(1000)
    println(user.startType)
}