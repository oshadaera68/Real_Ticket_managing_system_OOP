package com.eraboy.oop_backend.repo;

import com.eraboy.oop_backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@EnableJpaRepositories
public interface TicketRepo extends JpaRepository<Ticket, Long> {}
