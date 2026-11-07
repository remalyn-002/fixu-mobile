package com.example.fixu.models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class FixuViewModel(
    private val repository: FixuRepository = FixuRepository()
) : ViewModel() {

    // Observable list for Jetpack Compose UI screens
    private val _tickets = mutableStateListOf<Ticket>()
    val tickets: List<Ticket> get() = _tickets

    init {
        loadTickets()
    }

    fun loadTickets() {
        _tickets.clear()
        // Pre-populating with initial sample tickets for local testing
        _tickets.addAll(
            listOf(
                Ticket("1", "Plumbing", "ST Building 3rd Flr Restroom", "Leaking faucet in corner sink.", "Remalyn Abad", "10 mins ago", TicketStatus.PENDING, 3),
                Ticket("2", "Electrical", "CS Lab 2", "Projector power outlet non-responsive.", "Student #2022019", "1 hour ago", TicketStatus.IN_PROGRESS, 7),
                Ticket("3", "HVAC", "Library 2nd Floor", "AC unit blowing warm air.", "Student #2023112", "Yesterday", TicketStatus.RESOLVED, 12)
            )
        )
    }

    fun addNewTicket(category: String, location: String, description: String, reportedBy: String, hasPhoto: Boolean) {
        val newTicket = Ticket(
            id = (_tickets.size + 1).toString(),
            category = category,
            location = location,
            description = description,
            reportedBy = reportedBy,
            timestamp = "Just now",
            status = TicketStatus.PENDING,
            upvotes = 1,
            hasPhoto = hasPhoto
        )
        _tickets.add(0, newTicket)
        repository.addTicket(newTicket)
    }

    fun upvoteTicket(ticketId: String) {
        val index = _tickets.indexOfFirst { it.id == ticketId }
        if (index != -1) {
            val current = _tickets[index]
            _tickets[index] = current.copy(upvotes = current.upvotes + 1)
        }
    }

    fun updateTicketStatus(ticketId: String, newStatus: TicketStatus) {
        val index = _tickets.indexOfFirst { it.id == ticketId }
        if (index != -1) {
            val current = _tickets[index]
            _tickets[index] = current.copy(status = newStatus)
        }
    }
}