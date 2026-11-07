package com.example.fixu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixu.models.Ticket
import com.example.fixu.ui.theme.*

@Composable
fun RequestsScreen(
    tickets: List<Ticket>,
    currentUserName: String
) {
    // Show tickets reported by the current user
    val userTickets = tickets.filter { it.reportedBy.equals(currentUserName, ignoreCase = true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCharcoal)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "My Requests",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Showing tickets submitted by $currentUserName",
                color = TextMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (userTickets.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "You haven't submitted any requests yet.",
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(userTickets) { ticket ->
                        TicketCard(ticket = ticket, onUpvote = {})
                    }
                }
            }
        }
    }
}