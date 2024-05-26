package com.example.expensetrackerapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val editTextExpenseName: EditText = findViewById(R.id.editTextExpenseName)
        val editTextExpenseAmount: EditText = findViewById(R.id.editTextExpenseAmount)
        val buttonSaveExpense: Button = findViewById(R.id.buttonSaveExpense)

        buttonSaveExpense.setOnClickListener {
            val newExpenseName = editTextExpenseName.text.toString()
            val newExpenseAmount = editTextExpenseAmount.text.toString()
            val resultIntent = intent
            resultIntent.putExtra("new_expense_name", newExpenseName)
            resultIntent.putExtra("new_expense_amount", newExpenseAmount)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
