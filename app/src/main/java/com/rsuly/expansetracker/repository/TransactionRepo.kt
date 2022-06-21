package com.rsuly.expansetracker.repository

import com.rsuly.expansetracker.db.AppDatabase
import com.rsuly.expansetracker.model.Transaction
import javax.inject.Inject

class TransactionRepo @Inject constructor(private val db: AppDatabase) {

    //insert transaction
    suspend fun insert(transaction: Transaction) =
        db.getTransactionDao().insertTransaction(transaction)

    //update transaction
    suspend fun update(transaction: Transaction) =
        db.getTransactionDao().updateTransaction(transaction)

    //delete transaction
    suspend fun delete(transaction: Transaction) =
        db.getTransactionDao().deleteTransaction(transaction)

    //get all transactions
    fun getAllTransactions() = db.getTransactionDao().getAllTransactions()

    //get single transaction type
    fun getAllSingleTransaction(transactionType: String) = if (transactionType == "Overall") {
        getAllTransactions()
    } else {
        db.getTransactionDao().getAllSingleTransaction(transactionType)
    }

    fun getById(id: Int) = db.getTransactionDao().getTransactionByID(id)

    suspend fun deleteById(id: Int) = db.getTransactionDao().deleteTransactionById(id)
}