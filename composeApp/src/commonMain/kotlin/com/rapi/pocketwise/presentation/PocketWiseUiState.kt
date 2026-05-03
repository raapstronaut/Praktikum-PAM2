package com.rapi.pocketwise.presentation

import com.rapi.pocketwise.data.model.Expense
import com.rapi.pocketwise.data.model.ExpenseCategory
import com.rapi.pocketwise.data.model.FinanceSummary

data class PocketWiseUiState(
    val amountInput: String = "",
    val noteInput: String = "",
    val selectedCategory: ExpenseCategory = ExpenseCategory.FOOD,
    val expenses: List<Expense> = emptyList(),
    val summary: FinanceSummary = FinanceSummary(
        totalExpense = 0,
        highestCategory = "-",
        categoryBreakdown = "-",
        expenseCount = 0
    ),
    val aiAnalysis: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)