package com.example.androidpractice.kotlintraining

import java.text.NumberFormat
import java.util.Currency

/*
   Необходимо создать интерфейс Publication, у которого должно быть свойства – price и wordCount,
   а также метод getType, возвращающий строку. Создать два класса,
   реализующих данный интерфейс – Book и Magazine. В методе getType для класса
   Book возвращаем строку с зависимости от количества слов. Если количество слов
   не превышает 1000, то вывести “Flash Fiction”, 7,500 –“Short Story”, всё, что выше – “Novel”.
   Для класса Magazine возвращаем строку “Magazine”.
 */
interface Publication {
    val price: Int
    val wordCount: Int

    fun getType(): String
}

data class Book(override val price: Int, override val wordCount: Int) : Publication {

    override fun getType(): String {
        val bookType = when {
            wordCount <= 1000 -> {
                "Flash Fiction"
            }

            wordCount in 1001..7500 -> {
                "Short Story"
            }

            else -> {
                "Novel"
            }
        }
        return bookType
    }
}

data class Magazine(override val price: Int, override val wordCount: Int) : Publication {
    override fun getType() = "Magazine"
}

/*
   Создать два объекта класса Book и один объект Magazine. Вывести в лог для каждого объекта тип,
   количество строк и цену в евро в отформатированном виде. У класса Book переопределить метод
   equals и произвести сравнение сначала по ссылке, затем используя метод equals.
   Результаты сравнений вывести в лог.
*/
fun publications() {
    val publications = listOf(
        Book(1000, 7000),
        Book(1000, 7000),
        Magazine(200, 1000)
    )

    val printPublicationInfo: Publication.() -> Unit = {
        val maxWordsInLine = 10
        val lines = wordCount / maxWordsInLine
        val format = NumberFormat.getCurrencyInstance().apply {
            currency = Currency.getInstance("EUR")
        }
        val price = format.format(price)
        println(
            "Тип публикации: ${getType()}\nКоличество строк: $lines\nЦена:$price"
        )
    }

    publications.forEach(printPublicationInfo)

    println(
        "Сравнение ${publications[0]} и ${publications[1]}: \n" +
            "По ссылке: ${publications[0] === publications[1]}\n" +
            "Через equals: ${publications[0] == publications[1]}"
    )
}

/*
   Создать метод buy, который в качестве параметра принимает Publication (notnull - значения)
   и выводит в лог “The purchase is complete. The purchase amount was [цена издания]”.
   Создать две переменных класса Book, в которых могут находиться null значения. Присвоить
   одной null, а второй любое notnull значение. Используя функцию let, попробуйте вызвать
   метод buy с каждой из переменных.
 */
fun buy(publication: Publication) {
    val format = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("EUR")
    }
    println("The purchase is complete. The purchase amount was ${format.format(publication.price)}")
}

fun letBuy() {
    val firstBook: Book? = null
    val secondBook: Book = Book(100, 4000)
    @Suppress("KotlinConstantConditions")
    firstBook?.let {
        buy(it)
    } ?: println("The publication is null")
    buy(secondBook)
}

/*
   Создать переменную sum и присвоить ей лямбда-выражение, которое будет складывать два переданных
   ей числа и выводить результат в лог. Вызвать данное лямбда-выражение с произвольными параметрами.
 */
fun someLambda() {
    val sum: (Int, Int) -> Unit = { first, second ->
        val result = first + second
        println(result)
    }
    sum(1, 10)
    sum(10, -10)
    sum(-1, -2)
}
