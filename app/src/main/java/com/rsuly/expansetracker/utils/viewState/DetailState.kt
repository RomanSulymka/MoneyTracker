package com.rsuly.expansetracker.utils.viewState

import com.rsuly.expansetracker.model.Transaction
import java.lang.Exception

sealed class DetailState {
    object Loading : DetailState()
    object Empty : DetailState()
    data class Success(val transaction: Transaction) : DetailState()
    data class Error(val exception: Exception) : DetailState()
}
