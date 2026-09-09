package com.corphish.quicktools.usecases

import com.corphish.quicktools.repository.TextRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SaveTextUseCaseTest {

    private val textRepository: TextRepository = mockk()
    private val useCase = SaveTextUseCase(textRepository)

    @Test
    fun testExecute() = runTest {
        val uriString = "content://test"
        val text = "hello"
        coEvery { textRepository.writeText(uriString, text) } returns true

        val result = useCase.execute(uriString, text)

        assertTrue(result)
        coVerify { textRepository.writeText(uriString, text) }
    }
}
