package com.rsuly.expansetracker.db

import androidx.room.*
import com.rsuly.expansetracker.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    //used to insert new transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    //used to update existing transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    //used to remove transaction
    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    //used to get all transactions
    @Query("SELECT * FROM ALL_TRANSACTIONS")
    fun getAllTransactions(): Flow<List<Transaction>>

    //get all income or expense list
    @Query("SELECT * FROM ALL_TRANSACTIONS WHERE transactionType == :transactionType")
    fun getTransaction(transactionType: String): Flow<List<Transaction>>

    // get all income or expense list by transaction type param
    @Query("SELECT * FROM all_transactions WHERE transactionType == :transactionType ORDER by createdAt DESC")
    fun getAllSingleTransaction(transactionType: String): Flow<List<Transaction>>

    @Query("SELECT * FROM all_transactions WHERE id = :id")
    fun getTransactionByID(id: Int): Flow<Transaction>

    @Query("DELETE FROM all_transactions where id = :id")
    suspend fun deleteTransactionById(id: Int)
}