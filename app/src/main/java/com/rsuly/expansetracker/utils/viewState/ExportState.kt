package com.rsuly.expansetracker.utils.viewState

import android.net.Uri
import java.lang.Exception

sealed class ExportState {
    object Loading : ExportState()
    object Empty : ExportState()
    data class Success(val fileUrl: Uri) : ExportState()
    data class Error(val exception: Throwable) : ExportState()
}