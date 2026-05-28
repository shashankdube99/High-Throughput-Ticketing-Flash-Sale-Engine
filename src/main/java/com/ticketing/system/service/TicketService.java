package com.ticketing.system.service;

import com.ticketing.system.dto.TicketDTO;
import com.ticketing.system.entity.Ticket;
import com.ticketing.system.exception.TicketNotFoundException;
import com.ticketing.system.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Ticket operations.
 */
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    /**
     * Create a new ticket.
     */
    @Transactional
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(generateTicketNumber());
        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(Ticket.TicketStatus.OPEN);
        ticket.setPriority(ticketDTO.getPriority());
        ticket.setUserId(ticketDTO.getUserId());
        ticket.setAssignedToUserId(ticketDTO.getAssignedToUserId());

        Ticket savedTicket = ticketRepository.save(ticket);
        return convertToDTO(savedTicket);
    }

    /**
     * Get a ticket by ID.
     */
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + id));
        return convertToDTO(ticket);
    }

    /**
     * Get all tickets.
     */
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tickets by user ID.
     */
    public List<TicketDTO> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tickets assigned to a user.
     */
    public List<TicketDTO> getTicketsAssignedToUser(Long userId) {
        return ticketRepository.findByAssignedToUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tickets by status.
     */
    public List<TicketDTO> getTicketsByStatus(Ticket.TicketStatus status) {
        return ticketRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update a ticket.
     */
    @Transactional
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + id));

        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setPriority(ticketDTO.getPriority());

        Ticket updatedTicket = ticketRepository.save(ticket);
        return convertToDTO(updatedTicket);
    }

    /**
     * Delete a ticket.
     */
    @Transactional
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException("Ticket not found with ID: " + id);
        }
        ticketRepository.deleteById(id);
    }

    /**
     * Generate a unique ticket number.
     */
    private String generateTicketNumber() {
        return "TKT-" + System.currentTimeMillis();
    }

    /**
     * Convert Ticket entity to TicketDTO.
     */
    private TicketDTO convertToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getTicketNumber(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getUserId(),
                ticket.getAssignedToUserId(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}
