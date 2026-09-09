package com.corphish.quicktools.repository

import com.corphish.quicktools.data.Result

/**
 * Repository for evaluating inline mathematical expressions.
 */
interface EvalRepository {
    /**
     * Evaluates [expression], formatting the result to at most [decimalPoints] decimal places.
     * @return [Result.Success] with the formatted result, or [Result.Error] if the expression is invalid.
     */
    fun evaluate(expression: String, decimalPoints: Int): Result<String>
}
