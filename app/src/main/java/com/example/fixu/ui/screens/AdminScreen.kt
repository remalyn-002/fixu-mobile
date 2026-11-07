package com.example.fixu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixu.models.Ticket
import com.example.fixu.models.TicketStatus
import com.example.fixu.ui.theme.*

@Composable
fun AdminScreen(
    tickets: List<Ticket>,
    onStatusChange: (ticketId: String, newStatus: TicketStatus) -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        containerColor = DarkCharcoal,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Admin Dashboard", color = HighContrastCyan, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Facility Management Console", color = TextMuted, fontSize = 12.sp)
                }
                TextButton(onClick = onLogout) {
                    Text("LOGOUT", color = StatusPending, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tickets) { ticket ->
                AdminTicketCard(ticket = ticket, onStatusChange = onStatusChange)
            }
        }
    }
}

@Composable
fun AdminTicketCard(
    ticket: Ticket,
    onStatusChange: (String, TicketStatus) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(ticket.category.uppercase(), color = HighContrastCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(ticket.location, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(ticket.description, color = TextMuted, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Reported by: ${ticket.reportedBy} • ${ticket.upvotes} Upvotes", color = TextMuted, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(12.dp))
            Text("UPDATE STATUS:", color = TextWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TicketStatus.values().forEach { status ->
                    FilterChip(
                        selected = ticket.status == status,
                        onClick = { onStatusChange(ticket.id, status) },
                        label = { Text(status.name.replace("_", " "), fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = HighContrastCyan,
                            selectedLabelColor = DarkCharcoal,
                            containerColor = DarkCharcoal,
                            labelColor = TextWhite
                        )
                    )
                }
            }
        }
    }
}