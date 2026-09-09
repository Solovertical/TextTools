package com.corphish.quicktools.repository

import com.corphish.quicktools.data.Result
import com.corphish.quicktools.functions.EvalFunctions
import javax.inject.Inject

class EvalRepositoryImpl @Inject constructor(
    private val evalFunctions: EvalFunctions
) : EvalRepository {
    override fun evaluate(expression: String, decimalPoints: Int): Result<String> {
        return try {
            val result = evalFunctions.evaluate(expression)
            Result.Success(evalFunctions.formatResult(result, decimalPoints))
        } catch (e: Exception) {
            Result.Error
        }
    }
}
