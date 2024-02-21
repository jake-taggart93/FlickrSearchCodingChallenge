package com.example.cvscodechallenge

import com.example.cvscodechallenge.domain.util.formatDate
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun formatter_is_right() {
        assertEquals("2024-02-17T09:04:49Z".formatDate(), "02/17/2024")
    }
}