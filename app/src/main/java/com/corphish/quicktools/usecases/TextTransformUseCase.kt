package com.corphish.quicktools.usecases

import com.corphish.quicktools.data.Result
import com.corphish.quicktools.repository.TextTransformRepository
import javax.inject.Inject

/**
 * Use case for text transformation.
 */
class TextTransformUseCase @Inject constructor(
    private val textTransformRepository: TextTransformRepository
) {
    /**
     * Executes the text transformation.
     * @return [Result.Success] with the transformed text, or [Result.Error] if the
     * transformation could not be applied.
     */
    fun execute(
        text: String,
        primaryIndex: Int,
        secondaryIndex: Int,
        secondaryText: String
    ): Result<String> {
        return textTransformRepository.transform(text, primaryIndex, secondaryIndex, secondaryText)
    }
}
