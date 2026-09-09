package com.corphish.quicktools.repository

import com.corphish.quicktools.data.Result

/**
 * Repository interface for text transformations.
 */
interface TextTransformRepository {
    /**
     * Transforms the given text based on the primary and secondary indices.
     * @param text Original text.
     * @param primaryIndex Primary transformation index.
     * @param secondaryIndex Secondary transformation index.
     * @param secondaryText Optional secondary text for transformation.
     * @return [Result.Success] with the transformed text, or [Result.Error] if the
     * transformation could not be applied (e.g. an out-of-range index).
     */
    fun transform(
        text: String,
        primaryIndex: Int,
        secondaryIndex: Int,
        secondaryText: String
    ): Result<String>
}
