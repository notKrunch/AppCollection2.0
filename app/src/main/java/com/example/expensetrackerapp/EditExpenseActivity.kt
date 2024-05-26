package com.example.expensetrackerapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_expense)

        val editTextExpenseName: EditText = findViewById(R.id.editTextExpenseName)
        val editTextExpenseAmount: EditText = findViewById(R.id.editTextExpenseAmount)
        val buttonUpdateExpense: Button = findViewById(R.id.buttonUpdateExpense)

        val expenseName = intent.getStringExtra("expense_name")
        val expenseAmount = intent.getStringExtra("expense_amount")
        val position = intent.getIntExtra("position", -1)

        editTextExpenseName.setText(expenseName)
        editTextExpenseAmount.setText(expenseAmount)

        buttonUpdateExpense.setOnClickListener {
            val updatedExpenseName = editTextExpenseName.text.toString()
            val updatedExpenseAmount = editTextExpenseAmount.text.toString()
            val resultIntent = intent
            resultIntent.putExtra("new_expense_name", updatedExpenseName)
            resultIntent.putExtra("new_expense_amount", updatedExpenseAmount)
            resultIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
