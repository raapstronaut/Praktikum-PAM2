package com.rapi.pocketwise.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rapi.pocketwise.data.model.ExpenseCategory
import com.rapi.pocketwise.utils.formatRupiah
import kotlinx.coroutines.launch

@Composable
fun PocketWiseScreen(
    viewModel: PocketWiseViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "PocketWise",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Catat pengeluaranmu dan dapatkan insight hemat dari AI.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Tambah Pengeluaran",
                        style = MaterialTheme.typography.titleMedium
                    )

                    OutlinedTextField(
                        value = uiState.amountInput,
                        onValueChange = viewModel::onAmountChanged,
                        label = { Text("Nominal") },
                        placeholder = { Text("Contoh: 25000") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Text(
                        text = "Kategori",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ExpenseCategory.values().forEach { category ->
                            FilterChip(
                                selected = uiState.selectedCategory == category,
                                onClick = {
                                    viewModel.onCategoryChanged(category)
                                },
                                label = {
                                    Text(category.label)
                                }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = uiState.noteInput,
                        onValueChange = viewModel::onNoteChanged,
                        label = { Text("Catatan") },
                        placeholder = { Text("Contoh: makan siang") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = viewModel::addExpense,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tambah Pengeluaran")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Ringkasan",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Total Pengeluaran: ${formatRupiah(uiState.summary.totalExpense)}"
                    )

                    Text(
                        text = "Jumlah Transaksi: ${uiState.summary.expenseCount}"
                    )

                    Text(
                        text = "Kategori Terbesar: ${uiState.summary.highestCategory}"
                    )
                }
            }
        }

        if (uiState.expenses.isNotEmpty()) {
            item {
                Text(
                    text = "Daftar Pengeluaran",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(uiState.expenses) { expense ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = expense.category.label,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = formatRupiah(expense.amount),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        if (expense.note.isNotBlank()) {
                            Text(
                                text = expense.note,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        viewModel.analyzeWithAI()
                    }
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Analisis dengan Gemini AI")
            }
        }

        if (uiState.isLoading) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()

                        Text(
                            text = "AI sedang menganalisis pengeluaranmu..."
                        )
                    }
                }
            }
        }

        uiState.errorMessage?.let { message ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Terjadi Kesalahan",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        if (uiState.aiAnalysis.isNotBlank()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AssistChip(
                            onClick = {},
                            label = {
                                Text("Gemini AI Insight")
                            }
                        )

                        Text(
                            text = "Analisis PocketWise",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = uiState.aiAnalysis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        item {
            OutlinedButton(
                onClick = viewModel::clearAll,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Data")
            }
        }
    }
}