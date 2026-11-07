package com.example.fixu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixu.models.UserData
import com.example.fixu.ui.theme.*

@Composable
fun ProfileScreen(
    userData: UserData,
    selectedTheme: String,
    onThemeChanged: (String) -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()
    var currentUser by remember(userData) { mutableStateOf(userData) }

    // Dialog state controllers
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showAppearanceDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    var notificationsEnabled by remember { mutableStateOf(true) }

    // Dynamic role title calculation based on explicit UserData role
    val roleSubtitle = when (currentUser.role) {
        "Admin" -> "Adamson University Administrator"
        "Faculty" -> "Adamson University Faculty"
        else -> "Adamson University Student"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCharcoal)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Avatar Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(HighContrastCyan),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Avatar",
                    tint = DarkCharcoal,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = currentUser.fullName,
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = roleSubtitle,
                color = TextMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Profile Details Card with Edit Trigger
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ACCOUNT & PROFILE DETAILS",
                            color = HighContrastCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { showEditProfileDialog = true },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Details",
                                tint = HighContrastCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileDetailRow(label = "FULL NAME", value = currentUser.fullName)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileDetailRow(label = "SCHOOL EMAIL", value = currentUser.email)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileDetailRow(label = "ID NUMBER", value = currentUser.studentId)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileDetailRow(label = "PROGRAM / DEPARTMENT", value = currentUser.program)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileDetailRow(label = "YEAR / POSITION", value = currentUser.yearLevel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ⚙️ Settings Options Suite
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SETTINGS",
                        color = HighContrastCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = TextWhite)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Report Notifications", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                Text("Enable/disable report updates", color = TextMuted, fontSize = 11.sp)
                            }
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DarkCharcoal,
                                checkedTrackColor = HighContrastCyan
                            )
                        )
                    }

                    HorizontalDivider(color = DarkCharcoal, modifier = Modifier.padding(vertical = 4.dp))

                    SettingsRow(
                        icon = Icons.Default.Lock,
                        title = "Change Password",
                        subtitle = "Update security credentials",
                        onClick = { showChangePasswordDialog = true }
                    )

                    HorizontalDivider(color = DarkCharcoal, modifier = Modifier.padding(vertical = 4.dp))

                    SettingsRow(
                        icon = Icons.Default.Build,
                        title = "Appearance",
                        subtitle = "Theme: $selectedTheme",
                        onClick = { showAppearanceDialog = true }
                    )

                    HorizontalDivider(color = DarkCharcoal, modifier = Modifier.padding(vertical = 4.dp))

                    SettingsRow(
                        icon = Icons.Default.Info,
                        title = "Privacy",
                        subtitle = "Data handling & permissions",
                        onClick = { showPrivacyDialog = true }
                    )

                    HorizontalDivider(color = DarkCharcoal, modifier = Modifier.padding(vertical = 4.dp))

                    SettingsRow(
                        icon = Icons.Default.Call,
                        title = "Help & Support",
                        subtitle = "Contact facility admins",
                        onClick = { showHelpDialog = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ℹ️ About Section
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ABOUT",
                        color = HighContrastCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    SettingsRow(
                        icon = Icons.Default.Info,
                        title = "About FixU",
                        subtitle = "Version 1.0.0 • Campus Reporting",
                        onClick = { showAboutDialog = true }
                    )

                    HorizontalDivider(color = DarkCharcoal, modifier = Modifier.padding(vertical = 4.dp))

                    SettingsRow(
                        icon = Icons.Default.List,
                        title = "How FixU Works",
                        subtitle = "Reporting & resolution process",
                        onClick = { showAboutDialog = true }
                    )

                    HorizontalDivider(color = DarkCharcoal, modifier = Modifier.padding(vertical = 4.dp))

                    SettingsRow(
                        icon = Icons.Default.CheckCircle,
                        title = "Terms & Privacy",
                        subtitle = "Usage guidelines & legal policies",
                        onClick = { showTermsDialog = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = StatusPending),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = TextWhite)
                Spacer(modifier = Modifier.width(8.dp))
                Text("LOG OUT", color = TextWhite, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // --- MODAL DIALOGS ---

    // 1. Edit Profile Modal
    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(currentUser.fullName) }
        var editId by remember { mutableStateOf(currentUser.studentId) }
        var editProgram by remember { mutableStateOf(currentUser.program) }
        var editYear by remember { mutableStateOf(currentUser.yearLevel) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            containerColor = DarkSurface,
            title = { Text("Edit Profile Details", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Full Name", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )
                    OutlinedTextField(
                        value = editId,
                        onValueChange = { editId = it },
                        label = { Text("ID Number", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )
                    OutlinedTextField(
                        value = editProgram,
                        onValueChange = { editProgram = it },
                        label = { Text("Program / Department", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )
                    OutlinedTextField(
                        value = editYear,
                        onValueChange = { editYear = it },
                        label = { Text("Year Level / Position", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    currentUser = currentUser.copy(
                        fullName = editName,
                        studentId = editId,
                        program = editProgram,
                        yearLevel = editYear
                    )
                    showEditProfileDialog = false
                }) {
                    Text("SAVE", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("CANCEL", color = TextMuted)
                }
            }
        )
    }

    // 2. Change Password Modal
    if (showChangePasswordDialog) {
        var currentPass by remember { mutableStateOf("") }
        var newPass by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            containerColor = DarkSurface,
            title = { Text("Change Password", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = currentPass,
                        onValueChange = { currentPass = it },
                        label = { Text("Current Password", color = TextMuted) },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )
                    OutlinedTextField(
                        value = newPass,
                        onValueChange = { newPass = it },
                        label = { Text("New Password", color = TextMuted) },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("UPDATE", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("CANCEL", color = TextMuted)
                }
            }
        )
    }

    // 3. Appearance Modal
    if (showAppearanceDialog) {
        AlertDialog(
            onDismissRequest = { showAppearanceDialog = false },
            containerColor = DarkSurface,
            title = { Text("Select Appearance", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    listOf("Dark", "Light", "System Default").forEach { mode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onThemeChanged(mode) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedTheme == mode,
                                onClick = { onThemeChanged(mode) },
                                colors = RadioButtonDefaults.colors(selectedColor = HighContrastCyan)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(mode, color = TextWhite)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAppearanceDialog = false }) {
                    Text("DONE", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // 4. Privacy Policy Modal
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            containerColor = DarkSurface,
            title = { Text("Privacy Policy", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "FixU collects user profile details and reported facility concerns exclusively for Adamson University maintenance resolution.",
                    color = TextMuted,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("CLOSE", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // 5. Help & Support Modal
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            containerColor = DarkSurface,
            title = { Text("Help & Support", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Need assistance?\n\nContact Adamson Facilities Office:\nEmail: facilities@adamson.edu.ph\nHotline: (02) 8524-2011 loc. 204",
                    color = TextMuted,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("OK", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // 6. About FixU Modal
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            containerColor = DarkSurface,
            title = { Text("About FixU", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "FixU is a real-time Campus Facility Issue Reporting application designed to connect students, faculty, and campus administrators.",
                    color = TextMuted,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("CLOSE", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // 7. Terms & Conditions Modal
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            containerColor = DarkSurface,
            title = { Text("Terms & Conditions", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "By using FixU, users agree to provide truthful facility issue reports.",
                    color = TextMuted,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("I AGREE", color = HighContrastCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = TextWhite)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, color = TextMuted, fontSize = 11.sp)
        }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = TextMuted)
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = TextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = if (value.isNotBlank()) value else "N/A",
            color = TextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}