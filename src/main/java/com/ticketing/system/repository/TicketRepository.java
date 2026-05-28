package com.ticketing.system.repository;

import com.ticketing.system.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Ticket entity.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Find a ticket by ticket number.
     */
    Optional<Ticket> findByTicketNumber(String ticketNumber);

    /**
     * Find all tickets for a user.
     */
    List<Ticket> findByUserId(Long userId);

    /**
     * Find all tickets assigned to a user.
     */
    List<Ticket> findByAssignedToUserId(Long assignedToUserId);

    /**
     * Find all tickets with a specific status.
     */
    List<Ticket> findByStatus(Ticket.TicketStatus status);

    /**
     * Find all tickets with a specific priority.
     */
    List<Ticket> findByPriority(Ticket.TicketPriority priority);

    /**
     * Custom query to find tickets by status and priority.
     */
    @Query("SELECT t FROM Ticket t WHERE t.status = :status AND t.priority = :priority")
    List<Ticket> findByStatusAndPriority(
            @Param("status") Ticket.TicketStatus status,
            @Param("priority") Ticket.TicketPriority priority
    );
}
