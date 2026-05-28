package com.ticketing.system.dto;

import com.ticketing.system.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Ticket entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;
    private String ticketNumber;
    private String title;
    private String description;
    private Ticket.TicketStatus status;
    private Ticket.TicketPriority priority;
    private Long userId;
    private Long assignedToUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
