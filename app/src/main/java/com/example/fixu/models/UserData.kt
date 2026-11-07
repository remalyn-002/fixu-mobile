package com.example.fixu.models

data class UserData(
    val fullName: String = "",
    val email: String = "",
    val studentId: String = "",
    val program: String = "",
    val yearLevel: String = "",
    val role: String = "Student" // Added role parameter
)