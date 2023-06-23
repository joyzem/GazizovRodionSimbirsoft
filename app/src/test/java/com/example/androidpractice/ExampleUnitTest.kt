package com.example.androidpractice

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.time.format.ResolverStyle
import java.time.format.TextStyle
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
