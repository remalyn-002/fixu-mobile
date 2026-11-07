package com.example.fixu.models

class FixuRepository {

    // Local in-memory list to store tickets temporarily
    private val ticketList = mutableListOf<Ticket>()

    fun getAllTickets(): List<Ticket> {
        return ticketList
    }

    fun addTicket(ticket: Ticket) {
        ticketList.add(ticket)
    }

    fun updateTicket(ticket: Ticket) {
        val index = ticketList.indexOfFirst { it.id == ticket.id }
        if (index != -1) {
            ticketList[index] = ticket
        }
    }

    fun deleteTicket(ticket: Ticket) {
        ticketList.remove(ticket)
    }
}