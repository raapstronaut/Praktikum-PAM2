package com.example.demop4app.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val theme: StateFlow<String> =
        repository.themeFlow.stateIn(scope, SharingStarted.WhileSubscribed(5000), "system")

    val sortOrder: StateFlow<String> =
        repository.sortOrderFlow.stateIn(scope, SharingStarted.WhileSubscribed(5000), "newest")

    fun updateTheme(theme: String) {
        scope.launch {
            repository.setTheme(theme)
        }
    }

    fun updateSortOrder(sortOrder: String) {
        scope.launch {
            repository.setSortOrder(sortOrder)
        }
    }
}