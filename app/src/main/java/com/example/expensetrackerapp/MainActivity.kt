package com.example.expensetrackerapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Expense(val name: String, val amount: String)

class MainActivity : AppCompatActivity() {

    private lateinit var listViewExpenses: ListView
    private lateinit var expensesAdapter: ExpenseAdapter
    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewExpenses = findViewById(R.id.listViewExpenses)
        expensesAdapter = ExpenseAdapter(this, expenses)
        listViewExpenses.adapter = expensesAdapter

        loadExpenses()

        val buttonAddExpense: Button = findViewById(R.id.buttonAddExpense)
        buttonAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val newExpenseName = data?.getStringExtra("new_expense_name")
            val newExpenseAmount = data?.getStringExtra("new_expense_amount")
            if (newExpenseName != null && newExpenseAmount != null) {
                expenses.add(Expense(newExpenseName, newExpenseAmount))
                expensesAdapter.notifyDataSetChanged()
                saveExpenses()
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            val position = data?.getIntExtra("position", -1) ?: -1
            val updatedExpenseName = data?.getStringExtra("new_expense_name")
            val updatedExpenseAmount = data?.getStringExtra("new_expense_amount")
            if (position >= 0 && updatedExpenseName != null && updatedExpenseAmount != null) {
                expenses[position] = Expense(updatedExpenseName, updatedExpenseAmount)
                expensesAdapter.notifyDataSetChanged()
                saveExpenses()
            }
        }
    }

    private fun saveExpenses() {
        val sharedPreferences = getSharedPreferences("expenses_prefs", Context.MODE_PRIVATE)
        val expenseStrings = expenses.map { "${it.name},${it.amount}" }.toSet()
        with(sharedPreferences.edit()) {
            putStringSet("expenses", expenseStrings)
            apply()
        }
    }

    private fun loadExpenses() {
        val sharedPreferences = getSharedPreferences("expenses_prefs", Context.MODE_PRIVATE)
        val storedExpenses = sharedPreferences.getStringSet("expenses", emptySet())
        expenses.clear()
        if (storedExpenses != null) {
            for (expense in storedExpenses) {
                val parts = expense.split(",")
                if (parts.size == 2) {
                    expenses.add(Expense(parts[0], parts[1]))
                }
            }
            expensesAdapter.notifyDataSetChanged()
        }
    }

    inner class ExpenseAdapter(context: Context, private var expenses: MutableList<Expense>) :
        ArrayAdapter<Expense>(context, 0, expenses) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.list_item_expense, parent, false)

            val expense = expenses[position]

            val textViewExpense: TextView = view.findViewById(R.id.textViewExpense)
            textViewExpense.text = "${expense.name}: $${expense.amount}"

            val buttonEdit: Button = view.findViewById(R.id.buttonEdit)
            buttonEdit.setOnClickListener {
                val intent = Intent(context, EditExpenseActivity::class.java)
                intent.putExtra("expense_name", expense.name)
                intent.putExtra("expense_amount", expense.amount)
                intent.putExtra("position", position)
                startActivityForResult(intent, 2)
            }

            val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
            buttonDelete.setOnClickListener {
                expenses.remove(expense)
                notifyDataSetChanged() // Notify adapter that dataset has changed
                saveExpenses()
            }

            return view
        }
    }
}

