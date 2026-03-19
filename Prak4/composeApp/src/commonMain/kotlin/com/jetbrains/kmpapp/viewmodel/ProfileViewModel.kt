package com.jetbrains.kmpapp.viewmodel

import com.jetbrains.kmpapp.data.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun setEditing(editing: Boolean) {
        _uiState.update { current ->
            current.copy(isEditing = editing)
        }
    }

    fun toggleDarkMode() {
        _uiState.update { current ->
            current.copy(isDarkMode = !current.isDarkMode)
        }
    }

    fun saveProfile(
        newName: String,
        newNim: String,
        newBio: String,
        newEmail: String,
        newPhone: String,
        newLocation: String
    ) {
        _uiState.update { current ->
            current.copy(
                profile = current.profile.copy(
                    name = newName,
                    nim = newNim,
                    bio = newBio,
                    email = newEmail,
                    phone = newPhone,
                    location = newLocation
                ),
                isEditing = false
            )
        }
    }

    fun getProfile(): Profile = _uiState.value.profile
}