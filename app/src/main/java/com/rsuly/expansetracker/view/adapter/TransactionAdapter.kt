package com.rsuly.expansetracker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rsuly.expansetracker.R
import com.rsuly.expansetracker.databinding.ItemTransactionLayoutBinding
import com.rsuly.expansetracker.model.Transaction
import com.rsuly.expansetracker.utils.usDollar

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    inner class TransactionViewHolder(val binding: ItemTransactionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.TransactionViewHolder {
        val binding =
            ItemTransactionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.TransactionViewHolder, position: Int) {

        val item = differ.currentList[position]
        holder.binding.apply {

            transactionName.text = item.title
            transactionCategory.text = item.tag

            when (item.transactionType) {
                "Income" -> {
                    transactionAmount.setTextColor(
                        ContextCompat.getColor(
                            transactionAmount.context,
                            R.color.income
                        )
                    )

                    transactionAmount.text = "+ ".plus(usDollar(item.amount))
                }
                "Expense" -> {
                    transactionAmount.setTextColor(
                        ContextCompat.getColor(
                            transactionAmount.context,
                            R.color.expense
                        )
                    )
                }
            }

            when (item.tag) {
                "Housing" -> {
                    transactionIconView.setImageResource(R.drawable.ic_food)
                }
                "Transportation" -> {
                    transactionIconView.setImageResource(R.drawable.ic_transport)
                }
                "Food" -> {
                    transactionIconView.setImageResource(R.drawable.ic_food)
                }
                "Utilities" -> {
                    transactionIconView.setImageResource(R.drawable.ic_utilities)
                }
                "Insurance" -> {
                    transactionIconView.setImageResource(R.drawable.ic_insurance)
                }
                "Healthcare" -> {
                    transactionIconView.setImageResource(R.drawable.ic_medical)
                }
                "Saving & Debts" -> {
                    transactionIconView.setImageResource(R.drawable.ic_savings)
                }
                "Personal Spending" -> {
                    transactionIconView.setImageResource(R.drawable.ic_personal_spending)
                }
                "Entertainment" -> {
                    transactionIconView.setImageResource(R.drawable.ic_entertainment)
                }
                "Miscellaneous" -> {
                    transactionIconView.setImageResource(R.drawable.ic_others)
                }
                else -> {
                    transactionIconView.setImageResource(R.drawable.ic_others)
                }
            }

            //on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //get on item click listener
    private var onItemClickListener: ((Transaction) -> Unit)? = null
    fun setOnItemClickListener(listener: (Transaction) -> Unit) {
        onItemClickListener = listener
    }
}