package com.ticketing.system.controller;

import com.ticketing.system.dto.TicketDTO;
import com.ticketing.system.entity.Ticket;
import com.ticketing.system.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Ticket management.
 */
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * Create a new ticket.
     */
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        TicketDTO createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    /**
     * Get a ticket by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        TicketDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    /**
     * Get all tickets.
     */
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by user ID.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByUserId(@PathVariable Long userId) {
        List<TicketDTO> tickets = ticketService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets assigned to a user.
     */
    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsAssignedToUser(@PathVariable Long userId) {
        List<TicketDTO> tickets = ticketService.getTicketsAssignedToUser(userId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketDTO>> getTicketsByStatus(@PathVariable Ticket.TicketStatus status) {
        List<TicketDTO> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Update a ticket.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketDTO ticketDTO) {
        TicketDTO updatedTicket = ticketService.updateTicket(id, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    /**
     * Delete a ticket.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
