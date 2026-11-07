package com.example.fixu.models

enum class TicketStatus { PENDING, IN_PROGRESS, RESOLVED }

data class Ticket(
    val id: String,
    val category: String,
    val location: String,
    val description: String,
    val reportedBy: String,
    val timestamp: String,
    var status: TicketStatus = TicketStatus.PENDING,
    var upvotes: Int = 0,
    var hasPhoto: Boolean = false
)