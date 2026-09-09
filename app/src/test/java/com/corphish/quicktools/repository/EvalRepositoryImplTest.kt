package com.corphish.quicktools.repository

import com.corphish.quicktools.data.Result
import com.corphish.quicktools.functions.EvalFunctions
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EvalRepositoryImplTest {

    private lateinit var repository: EvalRepositoryImpl
    private val evalFunctions: EvalFunctions = mockk()

    @Before
    fun setUp() {
        repository = EvalRepositoryImpl(evalFunctions)
    }

    @Test
    fun testEvaluate_Success() {
        every { evalFunctions.evaluate("3,14 * 2") } returns 6.28
        every { evalFunctions.formatResult(6.28, 2) } returns "6.28"

        val result = repository.evaluate("3,14 * 2", 2)

        assertEquals("6.28", (result as Result.Success).value)
    }

    @Test
    fun testEvaluate_InvalidExpression_ReturnsError() {
        every { evalFunctions.evaluate("invalid expression") } throws IllegalArgumentException()

        val result = repository.evaluate("invalid expression", 2)

        assertTrue(result is Result.Error)
    }
}
