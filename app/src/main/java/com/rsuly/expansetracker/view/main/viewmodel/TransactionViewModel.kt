package com.rsuly.expansetracker.view.main.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsuly.expansetracker.db.datastore.UIModeIml
import com.rsuly.expansetracker.model.Transaction
import com.rsuly.expansetracker.repository.TransactionRepo
import com.rsuly.expansetracker.service.ExportCsvService
import com.rsuly.expansetracker.service.toCsv
import com.rsuly.expansetracker.utils.viewState.DetailState
import com.rsuly.expansetracker.utils.viewState.ExportState
import com.rsuly.expansetracker.utils.viewState.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepo: TransactionRepo,
    private val exportService: ExportCsvService,
    private val uiModeDataStore: UIModeIml
) : ViewModel() {

    private val _exportCsvState = MutableStateFlow<ExportState>(ExportState.Empty)
    val exportCsvState: StateFlow<ExportState> = _exportCsvState

    private val _transactionFilter = MutableStateFlow<String>("Overall")
    val transactionFilter: StateFlow<String> = _transactionFilter

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)

    // UI collect from this stateFlow to get the state updates
    val uiState: StateFlow<ViewState> = _uiState
    val detailState: StateFlow<DetailState> = _detailState

    //get ui mode
    val getUIMode = uiModeDataStore.uiMode

    fun setDarkMode(isNightMode: Boolean) {
        viewModelScope.launch(IO) {
            uiModeDataStore.saveToDataStore(isNightMode)
        }
    }

    //save ui mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(IO) {
            uiModeDataStore.saveToDataStore(isNightMode)
        }
    }

    fun exportTransactionsToCsv(csvFileUri: Uri) = viewModelScope.launch {
        _exportCsvState.value = ExportState.Loading
        transactionRepo
            .getAllTransactions()
            .flowOn(IO)
            .map { it.toCsv() }
            .flatMapMerge { exportService.writeToCSV(csvFileUri, it) }
            .catch { error ->
                _exportCsvState.value = ExportState.Error(error)
            }.collect { uriString ->
                _exportCsvState.value = ExportState.Success(uriString)
            }
    }

    //insert transaction
    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.insert(transaction)
    }

    //update transaction
    fun updateTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.update(transaction)
    }

    //delete transaction
    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.delete(transaction)
    }

    fun getAllTransactions(type: String) = viewModelScope.launch {
        transactionRepo.getAllSingleTransaction(type).collect { result ->
            if (result.isEmpty()) {
                _uiState.value = ViewState.Empty
            } else {
                _uiState.value = ViewState.Success(result)
                Log.i("Filter", "Transaction filter is ${transactionFilter.value}")
            }
        }
    }

    fun getById(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading
        transactionRepo.getById(id).collect { result ->
            if (result != null) {
                _detailState.value = DetailState.Success(result)
            }
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        transactionRepo.deleteById(id)
    }

    fun allIncome() {
        _transactionFilter.value = "Income"
    }

    fun allExpense() {
        _transactionFilter.value = "Expense"
    }

    fun overall(){
        _transactionFilter.value = "Overall"
    }
}