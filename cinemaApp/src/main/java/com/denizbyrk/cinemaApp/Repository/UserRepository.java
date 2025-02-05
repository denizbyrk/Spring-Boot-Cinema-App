package com.denizbyrk.cinemaApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denizbyrk.cinemaApp.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
}