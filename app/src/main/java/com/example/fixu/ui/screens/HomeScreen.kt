package com.example.fixu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixu.models.Ticket
import com.example.fixu.models.TicketStatus
import com.example.fixu.ui.theme.*

@Composable
fun HomeScreen(
    tickets: List<Ticket>,
    currentUserName: String,
    onUpvote: (String) -> Unit,
    onSubmitTicket: (category: String, location: String, description: String, hasPhoto: Boolean) -> Unit,
    onProfileClick: () -> Unit = {}
) {
    var showReportDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCharcoal)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header Section with Interactive Profile Avatar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, ${currentUserName.substringBefore(" ").lowercase()}! 👋",
                        color = TextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Campus Facility Reports",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                }

                // Clickable Avatar Circle
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(HighContrastCyan)
                        .clickable { onProfileClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentUserName.take(1).uppercase(),
                        color = DarkCharcoal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "RECENT COMMUNITY REPORTS",
                color = HighContrastCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Feed of facility reports
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(tickets) { ticket ->
                    TicketCard(ticket = ticket, onUpvote = { onUpvote(ticket.id) })
                }
            }
        }

        // Floating Action Button to report new facility issue
        FloatingActionButton(
            onClick = { showReportDialog = true },
            containerColor = HighContrastCyan,
            contentColor = DarkCharcoal,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Report Issue")
        }
    }

    // Modal for submitting a new ticket
    if (showReportDialog) {
        NewReportDialog(
            onDismiss = { showReportDialog = false },
            onSubmit = { category, location, description, hasPhoto ->
                onSubmitTicket(category, location, description, hasPhoto)
                showReportDialog = false
            }
        )
    }
}

@Composable
fun TicketCard(
    ticket: Ticket,
    onUpvote: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = HighContrastCyan.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = ticket.category.uppercase(),
                        color = HighContrastCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                StatusBadge(status = ticket.status)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = ticket.location,
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = ticket.description,
                color = TextMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reported by ${ticket.reportedBy} • ${ticket.timestamp}",
                    color = TextMuted,
                    fontSize = 11.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onUpvote() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Upvote",
                        tint = HighContrastCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${ticket.upvotes}",
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: TicketStatus) {
    val (backgroundColor, textColor, label) = when (status) {
        TicketStatus.PENDING -> Triple(StatusPending.copy(alpha = 0.2f), StatusPending, "PENDING")
        TicketStatus.IN_PROGRESS -> Triple(StatusInProgress.copy(alpha = 0.2f), StatusInProgress, "IN PROGRESS")
        TicketStatus.RESOLVED -> Triple(StatusResolved.copy(alpha = 0.2f), StatusResolved, "RESOLVED")
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun NewReportDialog(
    onDismiss: () -> Unit,
    onSubmit: (category: String, location: String, description: String, hasPhoto: Boolean) -> Unit
) {
    var category by remember { mutableStateOf("Plumbing") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = {
            Text("Report Facility Issue", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (e.g. Electrical, HVAC)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location (e.g. CS Lab 1)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Issue Description", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (location.isNotBlank() && description.isNotBlank()) {
                        onSubmit(category, location, description, false)
                    }
                }
            ) {
                Text("SUBMIT", color = HighContrastCyan, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = TextMuted)
            }
        }
    )
}