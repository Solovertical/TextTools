package com.corphish.quicktools.viewmodels

import androidx.lifecycle.ViewModel
import com.corphish.quicktools.data.Result
import com.corphish.quicktools.repository.SettingsRepository
import com.corphish.quicktools.usecases.ClipboardUseCase
import com.corphish.quicktools.usecases.EvalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EvalViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val evalUseCase: EvalUseCase,
    private val clipboardUseCase: ClipboardUseCase,
) : ViewModel() {
    private val _evalMode = MutableStateFlow(settingsRepository.getEvaluateResultMode())
    val evalMode: StateFlow<Int> = _evalMode

    // To be populated when user finally selects a mode, if at all they select
    private var _userSelectedMode = _evalMode.value

    fun denoteModeSelectionByUser(selectedMode: Int) {
        this._userSelectedMode = selectedMode
    }

    fun denoteUserRememberChoice(choice: Boolean) {
        if (choice) {
            settingsRepository.setEvaluateResultMode(_userSelectedMode)
        }
    }

    suspend fun shouldForceCopy(choice: Boolean) {
        if (choice) {
            _userSelectedMode = EVAL_RESULT_COPY_TO_CLIPBOARD
            _evalMode.emit(EVAL_RESULT_COPY_TO_CLIPBOARD)
        }
    }

    private val _evalResult = MutableStateFlow<Result<EvaluateResult>>(Result.Initial)
    val evalResult: StateFlow<Result<EvaluateResult>> = _evalResult

    fun evaluate(text: String) {
        val decimalPoints = settingsRepository.getDecimalPoints()

        when (val result = evalUseCase.execute(text, decimalPoints)) {
            is Result.Success -> {
                val evalResult = EvaluateResult(
                    resultString = result.value,
                    finalMode = _userSelectedMode
                )

                if (_userSelectedMode == EVAL_RESULT_COPY_TO_CLIPBOARD) {
                    clipboardUseCase.copyToClipboard(evalResult.resultString)
                }

                _evalResult.value = Result.Success(evalResult)
            }

            is Result.Error -> _evalResult.value = Result.Error
            is Result.Initial -> Unit
        }
    }

    companion object {
        // Eval result mode choices will be shown to user next time
        const val EVAL_RESULT_MODE_ASK_NEXT_TIME = 0

        // Eval result will be replaced by the selected text
        const val EVAL_RESULT_REPLACE = 1

        // Eval result will be appended after the selected text with = sign
        const val EVAL_RESULT_APPEND = 2

        // Eval result will be copied to the clipboard
        const val EVAL_RESULT_COPY_TO_CLIPBOARD = 3
    }
}

data class EvaluateResult(
    val resultString: String,
    val finalMode: Int,
)