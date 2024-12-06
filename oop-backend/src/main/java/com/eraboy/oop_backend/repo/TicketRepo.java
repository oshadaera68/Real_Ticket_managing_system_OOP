package com.eraboy.oop_backend.repo;

import com.eraboy.oop_backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/
@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {
}
