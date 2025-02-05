package com.denizbyrk.movietheater.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denizbyrk.movietheater.Entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}