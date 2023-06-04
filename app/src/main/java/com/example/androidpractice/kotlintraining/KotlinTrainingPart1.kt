package com.example.androidpractice.kotlintraining

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

class Book(override val price: Int, override val wordCount: Int) : Publication {


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

class Magazine(override val price: Int, override val wordCount: Int) : Publication {

    override fun getType() = "Magazine"
}
