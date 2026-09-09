package com.corphish.quicktools.repository

import com.corphish.quicktools.functions.FileFunctions
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TextRepositoryImplTest {

    private lateinit var repository: TextRepositoryImpl
    private val fileFunctions: FileFunctions = mockk()

    @Before
    fun setUp() {
        repository = TextRepositoryImpl(fileFunctions)
    }

    @Test
    fun testWriteText_Success() = runTest {
        val uriString = "content://test"
        val text = "hello world"
        coEvery { fileFunctions.saveTextToUri(uriString, text) } returns true

        val result = repository.writeText(uriString, text)

        assertTrue(result)
    }

    @Test
    fun testWriteText_Failure() = runTest {
        val uriString = "content://test"
        val text = "hello world"
        coEvery { fileFunctions.saveTextToUri(uriString, text) } returns false

        val result = repository.writeText(uriString, text)

        assertFalse(result)
    }
}
