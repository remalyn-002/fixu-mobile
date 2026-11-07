package com.example.fixu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixu.models.UserData
import com.example.fixu.ui.theme.*

@Composable
fun AuthScreen(
    onLoginSuccess: (isAdmin: Boolean, userData: UserData) -> Unit
) {
    var selectedRole by remember { mutableStateOf("Student") } // "Student", "Faculty", "Admin"
    var isRegisterMode by remember { mutableStateOf(false) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var yearLevel by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCharcoal)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 🟦 Correct Cyan Box Logo with "F" Inside
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(HighContrastCyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "F",
                    color = DarkCharcoal,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "FixU",
                color = TextWhite,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Campus Facility Issue Reporting",
                color = TextMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // 1. Role Selector Tab Component
            RoleSelectorTab(
                selectedRole = selectedRole,
                onRoleSelected = { role ->
                    selectedRole = role
                    if (role == "Admin") isRegisterMode = false
                    errorMessage = ""
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Dynamic Input Fields for Registration
            if (isRegisterMode && selectedRole != "Admin") {
                AuthTextField(
                    label = "FULL NAME",
                    value = fullName,
                    placeholder = if (selectedRole == "Faculty") "e.g. Dr. Juan Dela Cruz" else "e.g. Remalyn Abad",
                    onValueChange = { fullName = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (selectedRole == "Student") {
                    AuthTextField(
                        label = "STUDENT ID",
                        value = studentId,
                        placeholder = "e.g. 202210492",
                        onValueChange = { studentId = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthTextField(
                        label = "PROGRAM / COURSE",
                        value = program,
                        placeholder = "e.g. BS Information Technology",
                        onValueChange = { program = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthTextField(
                        label = "YEAR LEVEL",
                        value = yearLevel,
                        placeholder = "e.g. 3rd Year",
                        onValueChange = { yearLevel = it }
                    )
                } else {
                    AuthTextField(
                        label = "EMPLOYEE ID",
                        value = studentId,
                        placeholder = "e.g. 2021013256",
                        onValueChange = { studentId = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthTextField(
                        label = "DEPARTMENT / OFFICE",
                        value = program,
                        placeholder = "e.g. Electronics Department",
                        onValueChange = { program = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthTextField(
                        label = "FACULTY POSITION",
                        value = yearLevel,
                        placeholder = "e.g. Professor / Instructor",
                        onValueChange = { yearLevel = it }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 3. Email Input
            AuthTextField(
                label = "SCHOOL EMAIL",
                value = email,
                placeholder = if (selectedRole == "Faculty") "faculty@adamson.edu.ph" else "username@adamson.edu.ph",
                keyboardType = KeyboardType.Email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Password Input
            PasswordTextField(
                value = password,
                onValueChange = { password = it }
            )

            // Forgot Password Link (Login Mode Only)
            if (!isRegisterMode && selectedRole != "Admin") {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = HighContrastCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            // Error Feedback Message Display
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMessage,
                    color = StatusPending,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Submit Action Button
            Button(
                onClick = {
                    when (selectedRole) {
                        "Admin" -> {
                            if (email.trim() == "admin@adamson.edu.ph" && password == "TestPass123") {
                                onLoginSuccess(
                                    true,
                                    UserData(
                                        fullName = "Admin User",
                                        email = email.trim(),
                                        studentId = "201345001",
                                        program = "System Administration",
                                        yearLevel = "System Admin",
                                        role = "Admin"
                                    )
                                )
                            } else {
                                errorMessage = "Invalid Admin credentials."
                            }
                        }
                        else -> {
                            if (isRegisterMode) {
                                when {
                                    fullName.isBlank() -> errorMessage = "Please enter your full name."
                                    studentId.isBlank() -> errorMessage = "Please enter your ID number."
                                    program.isBlank() -> errorMessage = "Please enter your ${if (selectedRole == "Student") "Program" else "Department"}."
                                    yearLevel.isBlank() -> errorMessage = "Please enter your ${if (selectedRole == "Student") "Year Level" else "Position"}."
                                    !email.trim().endsWith("@adamson.edu.ph") -> errorMessage = "Must use an @adamson.edu.ph email."
                                    password.length < 6 -> errorMessage = "Password must be at least 6 characters."
                                    else -> onLoginSuccess(
                                        false,
                                        UserData(
                                            fullName = fullName.trim(),
                                            email = email.trim(),
                                            studentId = studentId.trim(),
                                            program = program.trim(),
                                            yearLevel = yearLevel.trim(),
                                            role = selectedRole
                                        )
                                    )
                                }
                            } else {
                                if (!email.trim().endsWith("@adamson.edu.ph")) {
                                    errorMessage = "Must use an @adamson.edu.ph email."
                                } else if (password.isEmpty()) {
                                    errorMessage = "Please enter your password."
                                } else {
                                    val nameFromEmail = email.substringBefore("@").replace(".", " ")
                                    val formattedName = if (nameFromEmail.isNotBlank()) nameFromEmail else "$selectedRole User"
                                    onLoginSuccess(
                                        false,
                                        UserData(
                                            fullName = formattedName,
                                            email = email.trim(),
                                            studentId = if (selectedRole == "Student") "202210492" else "201013256",
                                            program = if (selectedRole == "Student") "BS Information Technology" else "Electronics Department",
                                            yearLevel = if (selectedRole == "Student") "3rd Year" else "Instructor",
                                            role = selectedRole
                                        )
                                    )
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = HighContrastCyan),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = when {
                        selectedRole == "Admin" -> "LOG IN AS ADMIN"
                        isRegisterMode -> "REGISTER $selectedRole ACCOUNT"
                        else -> "LOG IN AS $selectedRole"
                    },
                    color = DarkCharcoal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mode Switching Link (Login vs Register)
            if (selectedRole != "Admin") {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isRegisterMode) "Already have an account? " else "Don't have an account? ",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                    Text(
                        text = if (isRegisterMode) "Login" else "Register",
                        color = HighContrastCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            isRegisterMode = !isRegisterMode
                            errorMessage = ""
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// --- SUB-COMPONENTS ---

@Composable
private fun RoleSelectorTab(
    selectedRole: String,
    onRoleSelected: (String) -> Unit
) {
    Surface(
        color = DarkSurface,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            listOf("Student", "Faculty", "Admin").forEach { role ->
                val isSelected = selectedRole == role
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) HighContrastCyan else Color.Transparent)
                        .clickable { onRoleSelected(role) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = role,
                        color = if (isSelected) DarkCharcoal else TextMuted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthTextField(
    label: String,
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedBorderColor = HighContrastCyan,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("PASSWORD", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedBorderColor = HighContrastCyan,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}