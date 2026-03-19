package com.jetbrains.kmpapp.viewmodel

import com.jetbrains.kmpapp.data.Profile

data class ProfileUiState(
    val profile: Profile = Profile(
        name = "Muhamad Rafi Ilham",
        nim = "123140173",
        bio = "Saya adalah mahasiswa ITERA yang sedang mengambil mata kuliah Pengembangan Aplikasi Mobile dan tertarik pada mobile development.",
        email = "muhamad.123140173@student.itera.ac.id",
        phone = "+62 813-1924-6278",
        location = "Metro, Lampung"
    ),
    val isEditing: Boolean = false,
    val isDarkMode: Boolean = false
)