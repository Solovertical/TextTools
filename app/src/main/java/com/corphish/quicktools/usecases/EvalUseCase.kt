package com.corphish.quicktools.usecases

import com.corphish.quicktools.data.Result
import com.corphish.quicktools.repository.EvalRepository
import javax.inject.Inject

/**
 * Use case for evaluating inline mathematical expressions.
 */
class EvalUseCase @Inject constructor(
    private val evalRepository: EvalRepository
) {
    /**
     * Executes the expression evaluation.
     * @return [Result.Success] with the formatted result, or [Result.Error] if the expression is invalid.
     */
    fun execute(expression: String, decimalPoints: Int): Result<String> =
        evalRepository.evaluate(expression, decimalPoints)
}
