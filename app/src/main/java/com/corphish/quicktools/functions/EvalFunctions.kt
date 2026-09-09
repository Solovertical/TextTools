package com.corphish.quicktools.functions

import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition
import java.util.Locale
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Functions supporting inline mathematical expression evaluation.
 */
class EvalFunctions @Inject constructor() {

    /**
     * Normalizes numeric runs in [expression] from the device's default locale format to
     * the dot-decimal format expected by the expression parser, e.g. on a device whose
     * locale uses a comma as its decimal separator, "3,14 * 2" becomes "3.14 * 2".
     *
     * A run that isn't a valid number under the default locale (e.g. it has more than
     * one decimal separator) is left unchanged, which will surface as an evaluation error
     * rather than being silently misparsed.
     */
    fun normalizeNumberFormat(expression: String): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault())

        return numberTokenRegex.replace(expression) { match ->
            val token = match.value
            val position = ParsePosition(0)
            val number = formatter.parse(token, position)

            if (number == null || position.index != token.length) {
                token
            } else if (number.toDouble() == number.toLong().toDouble()) {
                number.toLong().toString()
            } else {
                number.toDouble().toString()
            }
        }
    }

    /**
     * Evaluates [expression], after normalizing its number formats.
     * @throws Exception if the expression is invalid.
     */
    fun evaluate(expression: String): Double =
        ExpressionBuilder(normalizeNumberFormat(expression)).build().evaluate()

    /**
     * Formats [result] as a whole number when it has no fractional part, otherwise to at
     * most [decimalPoints] decimal places, using [DecimalFormat]'s default (device locale)
     * separator.
     */
    fun formatResult(result: Double, decimalPoints: Int): String {
        return if (ceil(result) == floor(result)) {
            result.toInt().toString()
        } else {
            DecimalFormat("0.${"#".repeat(decimalPoints)}").format(result)
        }
    }

    companion object {
        private val numberTokenRegex = Regex("""\d+(?:[.,]\d+)*""")
    }
}
