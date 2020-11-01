package lesson1

import org.junit.jupiter.api.Tag
import kotlin.test.Test

class TaskTestsJava : AbstractTaskTests() {

    @Test
    @Tag("3")
    fun testSortTimesJava() {
        sortTimes { inputName, outputName -> JavaTasks.sortTimes(inputName, outputName) }
    }

    @Test
    @Tag("4")
    fun testSortAddressesJava() {
        sortAddresses { inputName, outputName -> JavaTasks.sortAddresses(inputName, outputName) }
    }

    @Test
    @Tag("4")
    fun testSortTemperaturesJava() {
        sortTemperatures { inputName, outputName -> JavaTasks.sortTemperatures(inputName, outputName) }
    }

    @Test
    @Tag("4")
    fun testSortSequenceJava() {
        sortSequence { inputName, outputName -> JavaTasks.sortSequence(inputName, outputName) }
    }

    @Test
    @Tag("2")
    fun testMergeArraysJava() {
        //mergeArrays { first, second -> JavaTasks.mergeArrays(first, second) } Здесь идея почему-то подчеркивает second красным,
        // так как видит в нем несоответствие ожидаемого и реального типа
    }
}
