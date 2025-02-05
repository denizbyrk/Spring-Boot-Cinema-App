package com.denizbyrk.cinemaApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denizbyrk.cinemaApp.Entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

}