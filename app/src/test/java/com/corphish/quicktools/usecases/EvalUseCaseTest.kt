package com.corphish.quicktools.usecases

import com.corphish.quicktools.data.Result
import com.corphish.quicktools.repository.EvalRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EvalUseCaseTest {

    private lateinit var useCase: EvalUseCase
    private val repository: EvalRepository = mockk()

    @Before
    fun setUp() {
        useCase = EvalUseCase(repository)
    }

    @Test
    fun testExecute() {
        every { repository.evaluate("3,14 * 2", 2) } returns Result.Success("6.28")

        val result = useCase.execute("3,14 * 2", 2)

        assertEquals("6.28", (result as Result.Success).value)
        verify { repository.evaluate("3,14 * 2", 2) }
    }
}
