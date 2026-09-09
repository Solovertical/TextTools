package com.corphish.quicktools.functions

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.util.Locale

class EvalFunctionsTest {

    private lateinit var evalFunctions: EvalFunctions
    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        evalFunctions = EvalFunctions()
        originalLocale = Locale.getDefault()
    }

    @After
    fun tearDown() {
        Locale.setDefault(originalLocale)
    }

    @Test
    fun testNormalizeNumberFormat_DotDecimalUnchanged_UsLocale() {
        Locale.setDefault(Locale.US)
        assertEquals("3.14 + 2", evalFunctions.normalizeNumberFormat("3.14 + 2"))
    }

    @Test
    fun testNormalizeNumberFormat_IntegerUnchanged() {
        Locale.setDefault(Locale.US)
        assertEquals("12 + 34", evalFunctions.normalizeNumberFormat("12 + 34"))
    }

    @Test
    fun testNormalizeNumberFormat_CommaDecimal_GermanLocale() {
        Locale.setDefault(Locale.GERMANY)
        assertEquals("3.14 * 2", evalFunctions.normalizeNumberFormat("3,14 * 2"))
    }

    @Test
    fun testNormalizeNumberFormat_MultipleCommaDecimals_GermanLocale() {
        Locale.setDefault(Locale.GERMANY)
        assertEquals("1.5 + 2.75", evalFunctions.normalizeNumberFormat("1,5 + 2,75"))
    }

    @Test
    fun testNormalizeNumberFormat_EuropeanThousandsWithDecimalComma_GermanLocale() {
        Locale.setDefault(Locale.GERMANY)
        assertEquals("1234.56", evalFunctions.normalizeNumberFormat("1.234,56"))
    }

    @Test
    fun testNormalizeNumberFormat_UsThousandsWithDecimalDot_UsLocale() {
        Locale.setDefault(Locale.US)
        assertEquals("1234.56", evalFunctions.normalizeNumberFormat("1,234.56"))
    }

    @Test
    fun testNormalizeNumberFormat_CommaAsThousandsGrouping_UsLocale() {
        // Under a US-locale device, repeated commas are grouping separators, not decimals.
        Locale.setDefault(Locale.US)
        assertEquals("12345678", evalFunctions.normalizeNumberFormat("12,345,678"))
    }

    @Test
    fun testNormalizeNumberFormat_MismatchedLocale_UsesDeviceConvention() {
        // A US-locale device has no way to know "3,14" was meant as a European decimal;
        // it parses the comma per its own (grouping) convention instead, giving 314. This
        // is the accepted tradeoff of trusting the device's locale rather than guessing
        // the input's origin.
        Locale.setDefault(Locale.US)
        assertEquals("314", evalFunctions.normalizeNumberFormat("3,14"))
    }

    @Test
    fun testEvaluate_CommaDecimal_GermanLocale() {
        Locale.setDefault(Locale.GERMANY)
        assertEquals(6.28, evalFunctions.evaluate("3,14 * 2"), 0.0001)
    }

    @Test
    fun testEvaluate_DotDecimal_UsLocale() {
        Locale.setDefault(Locale.US)
        assertEquals(6.28, evalFunctions.evaluate("3.14 * 2"), 0.0001)
    }

    @Test
    fun testEvaluate_InvalidExpressionThrows() {
        assertThrows(Exception::class.java) {
            evalFunctions.evaluate("invalid expression")
        }
    }

    @Test
    fun testFormatResult_WholeNumber() {
        assertEquals("4", evalFunctions.formatResult(4.0, 2))
    }

    @Test
    fun testFormatResult_Decimal() {
        Locale.setDefault(Locale.US)
        assertEquals("2.5", evalFunctions.formatResult(2.5, 2))
    }

    @Test
    fun testFormatResult_TruncatesToDecimalPoints() {
        Locale.setDefault(Locale.US)
        assertEquals("2.33", evalFunctions.formatResult(2.333333, 2))
    }
}
