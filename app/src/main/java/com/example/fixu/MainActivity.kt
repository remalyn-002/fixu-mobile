package com.example.fixu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.fixu.models.FixuViewModel
import com.example.fixu.models.TicketStatus
import com.example.fixu.models.UserData
import com.example.fixu.ui.screens.*
import com.example.fixu.ui.theme.DarkSurface
import com.example.fixu.ui.theme.FixUTheme

class MainActivity : ComponentActivity() {

    // Initialize the ViewModel using the activity-ktx delegate
    private val viewModel: FixuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // App-wide theme state ("Dark", "Light", "System Default")
            var appTheme by remember { mutableStateOf("Dark") }

            FixUTheme(selectedTheme = appTheme) {
                var isLoggedIn by remember { mutableStateOf(false) }
                var isAdmin by remember { mutableStateOf(false) }
                var selectedTab by remember { mutableIntStateOf(0) }

                var currentUser by remember {
                    mutableStateOf(
                        UserData(
                            fullName = "Student User",
                            email = "student@adamson.edu.ph",
                            studentId = "202210492",
                            program = "BS Information Technology",
                            yearLevel = "3rd Year",
                            role = "Student"
                        )
                    )
                }

                // Pull ticket state directly from the ViewModel
                val tickets = viewModel.tickets

                if (!isLoggedIn) {
                    AuthScreen(onLoginSuccess = { adminRole: Boolean, userData: UserData ->
                        isAdmin = adminRole
                        currentUser = userData
                        selectedTab = 0
                        isLoggedIn = true
                    })
                } else if (isAdmin) {
                    AdminScreen(
                        tickets = tickets,
                        onStatusChange = { ticketId, newStatus ->
                            viewModel.updateTicketStatus(ticketId, newStatus)
                        },
                        onLogout = { isLoggedIn = false }
                    )
                } else {
                    Scaffold(
                        bottomBar = {
                            NavigationBar(containerColor = DarkSurface) {
                                NavigationBarItem(
                                    selected = selectedTab == 0,
                                    onClick = { selectedTab = 0 },
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Home") }
                                )
                                NavigationBarItem(
                                    selected = selectedTab == 1,
                                    onClick = { selectedTab = 1 },
                                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Requests") },
                                    label = { Text("Requests") }
                                )
                                NavigationBarItem(
                                    selected = selectedTab == 2,
                                    onClick = { selectedTab = 2 },
                                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Alerts") },
                                    label = { Text("Alerts") }
                                )
                                NavigationBarItem(
                                    selected = selectedTab == 3,
                                    onClick = { selectedTab = 3 },
                                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                                    label = { Text("Profile") }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Surface(modifier = Modifier.padding(innerPadding)) {
                            when (selectedTab) {
                                0 -> HomeScreen(
                                    tickets = tickets,
                                    currentUserName = currentUser.fullName,
                                    onUpvote = { ticketId ->
                                        viewModel.upvoteTicket(ticketId)
                                    },
                                    onSubmitTicket = { cat, loc, desc, photo ->
                                        viewModel.addNewTicket(
                                            category = cat,
                                            location = loc,
                                            description = desc,
                                            reportedBy = currentUser.fullName,
                                            hasPhoto = photo
                                        )
                                    }
                                )
                                1 -> RequestsScreen(
                                    tickets = tickets,
                                    currentUserName = currentUser.fullName
                                )
                                2 -> AlertsScreen(
                                    tickets = tickets,
                                    currentUserName = currentUser.fullName
                                )
                                3 -> ProfileScreen(
                                    userData = currentUser,
                                    selectedTheme = appTheme,
                                    onThemeChanged = { appTheme = it },
                                    onLogout = { isLoggedIn = false }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}