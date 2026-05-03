package com.rapi.pocketwise

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.rapi.pocketwise.data.remote.GeminiService
import com.rapi.pocketwise.data.repository.FinanceRepositoryImpl
import com.rapi.pocketwise.presentation.PocketWiseScreen
import com.rapi.pocketwise.presentation.PocketWiseViewModel

@Composable
fun App(
    geminiApiKey: String
) {
    MaterialTheme {
        val viewModel = remember {
            val geminiService = GeminiService(
                apiKey = geminiApiKey
            )

            val financeRepository = FinanceRepositoryImpl(
                geminiService = geminiService
            )

            PocketWiseViewModel(
                repository = financeRepository
            )
        }

        PocketWiseScreen(
            viewModel = viewModel
        )
    }
}