package com.apkdownloader

import org.junit.Assert.assertEquals
import org.junit.Test

class MaxRevenueUnitTest {

    @Test
    fun maxRevenue_isCorrect() {
        assertEquals(3, calculateMaxRevenue(listOf(4, 2, 2, 4, 5, 1)))
        assertEquals(865, calculateMaxRevenue(listOf(100, 180, 260, 310, 40, 535, 695)))
    }

    private fun calculateMaxRevenue(arr: List<Int>): Int {
        var min = arr.first()
        var rev = 0
        arr.forEach {
            if (it > min) rev += it.minus(min)
            min = it
        }

        return rev
    }
}