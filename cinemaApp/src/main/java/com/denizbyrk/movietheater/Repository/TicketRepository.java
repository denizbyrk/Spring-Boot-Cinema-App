package com.denizbyrk.movietheater.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denizbyrk.movietheater.Entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

}