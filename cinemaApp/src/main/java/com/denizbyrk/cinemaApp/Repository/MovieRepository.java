package com.denizbyrk.cinemaApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denizbyrk.cinemaApp.Entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}