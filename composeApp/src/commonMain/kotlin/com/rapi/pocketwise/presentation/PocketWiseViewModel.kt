package com.rapi.pocketwise.presentation

import com.rapi.pocketwise.data.model.Expense
import com.rapi.pocketwise.data.model.ExpenseCategory
import com.rapi.pocketwise.data.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PocketWiseViewModel(
    private val repository: FinanceRepository
) {
    private val _uiState = MutableStateFlow(PocketWiseUiState())
    val uiState: StateFlow<PocketWiseUiState> = _uiState.asStateFlow()

    fun onAmountChanged(value: String) {
        val filteredValue = value.filter { it.isDigit() }

        _uiState.update { currentState ->
            currentState.copy(
                amountInput = filteredValue,
                errorMessage = null
            )
        }
    }

    fun onNoteChanged(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                noteInput = value,
                errorMessage = null
            )
        }
    }

    fun onCategoryChanged(category: ExpenseCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = category,
                errorMessage = null
            )
        }
    }

    fun addExpense() {
        val currentState = _uiState.value
        val amount = currentState.amountInput.toIntOrNull()

        if (amount == null || amount <= 0) {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "Nominal pengeluaran harus lebih dari 0."
                )
            }
            return
        }

        val newExpense = Expense(
            id = (currentState.expenses.size + 1).toString(),
            amount = amount,
            category = currentState.selectedCategory,
            note = currentState.noteInput,
            date = "Hari ini"
        )

        val updatedExpenses = currentState.expenses + newExpense
        val updatedSummary = repository.calculateSummary(updatedExpenses)

        _uiState.update { state ->
            state.copy(
                amountInput = "",
                noteInput = "",
                expenses = updatedExpenses,
                summary = updatedSummary,
                errorMessage = null
            )
        }
    }

    suspend fun analyzeWithAI() {
        val currentState = _uiState.value

        if (currentState.expenses.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "Tambahkan minimal satu pengeluaran terlebih dahulu."
                )
            }
            return
        }

        _uiState.update { state ->
            state.copy(
                isLoading = true,
                aiAnalysis = "",
                errorMessage = null
            )
        }

        repository.analyzeExpenses(currentState.expenses)
            .onSuccess { analysis ->
                _uiState.update { state ->
                    state.copy(
                        aiAnalysis = analysis,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .onFailure { error ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Gagal menganalisis pengeluaran."
                    )
                }
            }
    }

    fun clearAll() {
        _uiState.update {
            PocketWiseUiState()
        }
    }
}